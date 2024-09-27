package com.wjh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponse {
    private String vehicleName;
    private String vehiclePrice;
    private String vehicleImageUrl;
    private int numberOfRemaining;
}
