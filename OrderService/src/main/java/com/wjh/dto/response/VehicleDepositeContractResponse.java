package com.wjh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleDepositeContractResponse {
    private String contractId;
    private String brandName;
    private String vehicleName;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String idCardNumber;
    private int age;
    private String gender;
    private LocalDate dateOfBirth;
    private String price;
    private LocalDateTime createdAt;
    private String contractPdfId;
    private boolean enabled;
    private String status;
}
