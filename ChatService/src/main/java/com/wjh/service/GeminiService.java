package com.wjh.service;

import com.wjh.dto.gemini.Content;
import com.wjh.dto.gemini.Part;
import com.wjh.dto.request.ChatBotRequest;
import com.wjh.dto.request.GeminiRequest;
import com.wjh.dto.response.ChatBotResponse;
import com.wjh.repository.GeminiConnector;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    private final GeminiConnector geminiConnector;

    public ChatBotResponse getGeminiResponse(ChatBotRequest chatBotRequest) {

        List<Part> parts = new ArrayList<>(chatBotRequest.getMessage().stream().map(
                request -> Part.builder()
                    .text(request)
                    .build()).toList());

        Part constraintPart = Part.builder().text(constraintPrompt).build();

        parts.add(0, constraintPart);

        Content content = Content.builder()
                .parts(parts)
                .build();
        List<Content> contents = new ArrayList<>();
        contents.add(content);

        GeminiRequest geminiRequest = GeminiRequest.builder()
                .contents(contents)
                .build();

        return ChatBotResponse.builder()
                .message(this.geminiConnector.getResponse(version, modelName, key, geminiRequest)
                        .getCandidates().get(0)
                        .getContent().getParts().get(0)
                        .getText())
                .build();
    }
}
