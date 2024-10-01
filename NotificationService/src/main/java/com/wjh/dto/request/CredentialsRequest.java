package com.wjh.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CredentialsRequest {
    @NotNull
    @NotBlank(message = "BLANK_USERNAME")
    private String username;
    @NotNull
    @NotBlank(message = "BLANK_PASSWORD")
    private String password;
}
