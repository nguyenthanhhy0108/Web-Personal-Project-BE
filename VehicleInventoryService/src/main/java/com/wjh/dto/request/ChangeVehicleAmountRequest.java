package com.wjh.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeVehicleAmountRequest {
    @NotNull
    @NotBlank(message = "BLANK_VEHICLE_NAME")
    String vehicleName;
    @NotNull
    int amount;
}
