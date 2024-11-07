package com.wjh.controller;

import com.wjh.dto.request.ChatBotRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.ChatBotResponse;
import com.wjh.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/chat")
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/gemini")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<ChatBotResponse>> getGeminiResponse(
            @RequestBody ChatBotRequest chatBotRequest) {
        return ResponseEntity.ok(ApiResponse.<ChatBotResponse>builder()
                .data(this.geminiService.getGeminiResponse(chatBotRequest))
                .build());
    }
}
