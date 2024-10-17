package com.wjh.entity;

import jakarta.persistence.*;
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
@Entity
public class VehicleDepositeContract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String contractId;
    private String brandName;
    private String vehicleName;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private int age;
    private String gender;
    private LocalDate dateOfBirth;
    private long price;
    private LocalDateTime createdAt;
    private String contractPdfId;
    private boolean enabled;

    @OneToOne(mappedBy = "vehicleDepositeContract", cascade = CascadeType.ALL)
    private ContractPayment contractPayment;
}
