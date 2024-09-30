package com.wjh.dto.request;

import com.wjh.validator.constraint.NumberOfVehicleConstraint;
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
public class VehicleCreationRequest {
    @NotNull
    @NotBlank(message = "BLANK_VEHICLE_NAME")
    private String vehicleName;
    @NotNull
    @NotBlank(message = "BLANK_VEHICLE_DESCRIPTION")
    private String vehicleDescription;
    @NotNull
    @NotBlank(message = "BLANK_VEHICLE_PRICE")
    private String vehiclePrice;
    @NotNull
    @NotBlank(message = "BLANK_VEHICLE_IMAGE")
    private String vehicleImageUrl;

    @NotNull
    @NumberOfVehicleConstraint(min = 1)
    private int numberOfRemaining;

    @NotNull
    @NotBlank(message = "BLANK_BRAND_NAME")
    private String vehicleBrandName;
}
