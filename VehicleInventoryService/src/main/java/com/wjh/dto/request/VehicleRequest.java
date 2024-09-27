package com.wjh.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleRequest {
    @NotNull
    private String vehicleName;
    @NotNull
    private String vehiclePrice;
    @NotNull
    private String vehicleImageUrl;
    @NotNull
    private int numberOfRemaining;
}
