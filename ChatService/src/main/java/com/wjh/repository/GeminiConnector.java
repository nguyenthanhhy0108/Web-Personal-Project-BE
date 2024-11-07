package com.wjh.repository;

import com.wjh.dto.request.GeminiRequest;
import com.wjh.dto.response.GeminiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gemini-connector", url = "${gemini.url}")
public interface GeminiConnector {

    @PostMapping(value = "/{version}/models/{modelName}:generateContent",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    GeminiResponse getResponse(@PathVariable String version,
                               @PathVariable String modelName,
                               @RequestParam String key,
                               @RequestBody GeminiRequest request);
}
