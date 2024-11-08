package com.wjh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjh.dto.gemini.Content;
import com.wjh.dto.gemini.Part;
import com.wjh.dto.request.ChatBotRequest;
import com.wjh.dto.request.GeminiRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.ChatBotResponse;
import com.wjh.dto.response.ErrorResponse;
import com.wjh.dto.response.VehicleWithBrandResponse;
import com.wjh.repository.ApplicationFeignClient;
import com.wjh.repository.GeminiConnector;
import com.wjh.utils.CustomStringUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GeminiService {

    @Value("${gemini.version}")
    @NonFinal
    private String version;

    @Value("${gemini.modelName}")
    @NonFinal
    private String modelName;

    @Value("${gemini.key}")
    @NonFinal
    private String key;

    @Value("${gemini.constraint-prompt}")
    @NonFinal
    private String constraintPrompt;

    @Value("${gemini.fail-message}")
    @NonFinal
    private String failMessage;

    @Value("${gemini.checked-word}")
    @NonFinal
    private String CHECKED_WORD;

    @Value("${gemini.check.text}")
    @NonFinal
    private String geminiCheckText;

    @Value("${gemini.response.text}")
    @NonFinal
    private String geminiResponseText;

    private final GeminiConnector geminiConnector;
    private final ApplicationFeignClient applicationFeignClient;
    private final CustomStringUtils stringUtils;

    ObjectMapper mapper = new ObjectMapper();

    private GeminiRequest buildGeminiRequest(List<Part> parts) {
        Content content = Content.builder()
                .parts(parts)
                .build();
        List<Content> contents = new ArrayList<>();
        contents.add(content);

        return GeminiRequest.builder()
                .contents(contents)
                .build();
    }

    private List<Part> convertConstraintParts(List<String> constraintParts, ChatBotRequest chatBotRequest) {
        List<Part> parts = new ArrayList<>(constraintParts.stream().map(
                constraint -> Part.builder()
                        .text(constraint)
                        .build()).toList());

        Part actualPart = Part.builder().text(chatBotRequest.getMessage())
                .build();

        parts.add(actualPart);

        return parts;
    }

    public ChatBotResponse getGeminiResponse(ChatBotRequest chatBotRequest) {

        ChatBotResponse geminiCheck = this.getGeminiCheck(chatBotRequest);
        String actualCheckedResponse = geminiCheck.getMessage().strip();

        if (actualCheckedResponse.equals(CHECKED_WORD)) {
            String api = this.getGeminiApiResponse(chatBotRequest).getMessage().strip();
            log.info("API: {}", api);
            try {
                ApiResponse<?> response = this.applicationFeignClient.getVehicleServiceResponse(api);
                log.info("Response code: {}", response.getCode());
                log.info("Response message: {}", response.getMessage());
                if (response.getCode() != 1000) {
                    return ChatBotResponse.builder()
                            .message(failMessage)
                            .build();
                } else {
                    Object data = response.getData();
                    StringBuilder actualMarkDownResponse = new StringBuilder();
                    if (data instanceof List<?> dataList) {
                        for (Object object : dataList) {
                            log.warn("Parsing may incomplete");
                            if (object instanceof Map<?, ?> map) {
                                try {
                                    VehicleWithBrandResponse vehicleResponse =
                                            mapper.convertValue(map, VehicleWithBrandResponse.class);
                                    actualMarkDownResponse.append(this.getMarkdownFromVehicle(vehicleResponse));
                                } catch (Exception e) {
                                    log.error(e.getMessage());
                                }
                            }
                        }
                        return ChatBotResponse.builder()
                                .message(actualMarkDownResponse.toString())
                                .build();
                    }
                }
            } catch (FeignException feignException) {
                log.error(feignException.getMessage());
                log.error(feignException.contentUTF8());
                log.warn("Parsing error may incomplete");
                try {
                    ErrorResponse error = this.mapper.readValue(
                            feignException.contentUTF8(),
                            ErrorResponse.class);
                    return ChatBotResponse.builder()
                            .message(error.getMessage())
                            .build();
                } catch (JsonProcessingException jsonProcessingException) {
                    log.error(jsonProcessingException.getMessage());
                    return ChatBotResponse.builder()
                            .message(failMessage)
                            .build();
                }
            }
            return ChatBotResponse.builder()
                    .message(failMessage)
                    .build();
        } else {
            Part part = Part.builder()
                    .text(chatBotRequest.getMessage())
                    .build();
            Part constraintPart = Part.builder().text(constraintPrompt).build();
            List<Part> parts = List.of(part, constraintPart);
            GeminiRequest geminiRequest = this.buildGeminiRequest(parts);
            return ChatBotResponse.builder()
                    .message(this.geminiConnector.getResponse(version, modelName, key, geminiRequest)
                            .getCandidates().get(0)
                            .getContent().getParts().get(0)
                            .getText())
                    .build();
        }
    }

    String getMarkdownFromVehicle(VehicleWithBrandResponse vehicleResponse) {
        String specificMarkDownForVehicle = "";
        specificMarkDownForVehicle += "# **`" + vehicleResponse.getVehicleName()
                .toUpperCase() + "`** \n";
        specificMarkDownForVehicle += "- **Price:** "
                + this.stringUtils.convertPrice(vehicleResponse.getVehiclePrice())
                + " VND " + " \n";
        specificMarkDownForVehicle += "- **Description:** "
                + vehicleResponse.getVehicleDescription() + ". \n";
        specificMarkDownForVehicle += "![" + vehicleResponse.getVehicleName() + "]"
                + "(" + vehicleResponse.getVehicleImageUrl() + ")";
        specificMarkDownForVehicle += "\n --- \n";
        return specificMarkDownForVehicle;
    }

    public ChatBotResponse getGeminiApiResponse(ChatBotRequest chatBotRequest) {

        List<String> constraintParts = Arrays.stream(this.geminiResponseText.split("\\*")).toList();

        List<Part> parts = this.convertConstraintParts(constraintParts, chatBotRequest);

        GeminiRequest geminiRequest = this.buildGeminiRequest(parts);

        return ChatBotResponse.builder()
                .message(this.geminiConnector.getResponse(version, modelName, key, geminiRequest)
                        .getCandidates().get(0)
                        .getContent().getParts().get(0)
                        .getText())
                .build();
    }

    public ChatBotResponse getGeminiCheck(ChatBotRequest chatBotRequest) {

        List<String> constraintParts = Arrays.stream(this.geminiCheckText.split("\\*")).toList();

        List<Part> parts = this.convertConstraintParts(constraintParts, chatBotRequest);

        GeminiRequest geminiRequest = this.buildGeminiRequest(parts);

        return ChatBotResponse.builder()
                .message(this.geminiConnector.getResponse(version, modelName, key, geminiRequest)
                        .getCandidates().get(0)
                        .getContent().getParts().get(0)
                        .getText())
                .build();
    }
}
