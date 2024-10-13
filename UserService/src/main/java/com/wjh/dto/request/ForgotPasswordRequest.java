package com.wjh.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPasswordRequest {
    @NotNull
    @NotBlank(message = "REQUEST_MUST_NOT_A_BLANK")
    private String email;
    @NotNull
    @NotBlank(message = "REQUEST_MUST_NOT_A_BLANK")
    private String username;
}
