package com.wjh.dto.request.identity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleCodeParam {
    private String code;
    public String redirectUri;
}
