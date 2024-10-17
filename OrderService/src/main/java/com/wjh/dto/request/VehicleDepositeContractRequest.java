package com.wjh.dto.request;

import com.wjh.validator.constraint.DateOfBirthConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleDepositeContractRequest {
    @NotNull
    @NotBlank(message = "BLANK_REQUEST")
    private String brandName;
    @NotNull
    @NotBlank(message = "BLANK_REQUEST")
    private String vehicleName;
    @NotNull
    @NotBlank(message = "BLANK_REQUEST")
    private String name;
    @NotNull
    @NotBlank(message = "BLANK_REQUEST")
    private String address;
    @NotNull
    @NotBlank(message = "BLANK_REQUEST")
    private String phoneNumber;
    @NotNull
    @NotBlank(message = "BLANK_REQUEST")
    private String email;
    @NotNull
    @NotBlank(message = "BLANK_REQUEST")
    private String gender;
    @NotNull
    @DateOfBirthConstraint(min = 2, message = "INVALID_DOB")
    private LocalDate dateOfBirth;
    @NotNull
    @Min(100)
    private long price;
    @NotNull
    private MultipartFile contractPdf;
}
