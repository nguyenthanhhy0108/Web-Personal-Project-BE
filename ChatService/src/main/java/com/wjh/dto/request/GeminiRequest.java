package com.wjh.dto.request;

import com.wjh.dto.gemini.Content;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeminiRequest {
    private List<Content> contents;
}
