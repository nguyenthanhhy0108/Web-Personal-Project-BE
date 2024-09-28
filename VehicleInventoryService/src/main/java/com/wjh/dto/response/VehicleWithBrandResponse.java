package com.wjh.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleWithBrandResponse {
    private String vehicleName;
    private String vehiclePrice;
    private String vehicleImageUrl;
    private int numberOfRemaining;
    private String brandName;
}
