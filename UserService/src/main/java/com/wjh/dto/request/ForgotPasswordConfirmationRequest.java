package com.wjh.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ForgotPasswordConfirmationRequest {
    @NotBlank(message = "REQUEST_MUST_NOT_A_BLANK")
    @NotNull
    private String code;
    @NotBlank(message = "REQUEST_MUST_NOT_A_BLANK")
    @NotNull
    private String username;
    @NotBlank(message = "REQUEST_MUST_NOT_A_BLANK")
    @NotNull
    private String email;
    @NotBlank(message = "REQUEST_MUST_NOT_A_BLANK")
    @NotNull
    private String newPassword;
}
