package com.wjh.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String vehicleId;

    private String vehicleName;
    private String vehicleDescription;
    private String vehiclePrice;
    private String vehicleImageUrl;
    private int numberOfRemaining;

    @ManyToOne
    private VehicleBrand vehicleBrand;
}
