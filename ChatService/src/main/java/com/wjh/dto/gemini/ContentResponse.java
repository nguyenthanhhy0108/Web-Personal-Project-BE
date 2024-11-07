package com.wjh.dto.gemini;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentResponse extends Content{
    private String role;
}
