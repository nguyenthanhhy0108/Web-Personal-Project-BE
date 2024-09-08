package com.wjh.dto.request;

import com.wjh.validator.constraint.DateOfBirthConstraint;
import com.wjh.validator.constraint.PasswordConstraint;
import com.wjh.validator.constraint.UsernameConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCreationRequest {
    @PasswordConstraint(min = 8, max = 32, message = "INVALID_PASSWORD")
    @NotNull(message = "PASSWORD_IS_MISSING")
    private String password;

    private String email;
    @UsernameConstraint(min = 8, max = 16, message = "INVALID_USERNAME")
    @NotNull(message = "USERNAME_IS_MISSING")
    private String username;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @DateOfBirthConstraint(min = 2, message = "INVALID_DOB")
    private LocalDate dateOfBirth;
}
