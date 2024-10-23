package com.wjh.repository;

import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.VehicleWithBrandResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "vehicle-inventory-service")
public interface VehicleInventoryConnectionClient {
    @GetMapping(value = "/vehicle-inventory/vehicles/{brandName}/{vehicleName}")
    ResponseEntity<ApiResponse<VehicleWithBrandResponse>> findVehicleByBrandNameAndVehicleName(
            @PathVariable String brandName, @PathVariable String vehicleName);

    @PutMapping(value = "/vehicle-inventory/vehicles/{brandName}/{vehicleName}")
    ResponseEntity<ApiResponse<VehicleWithBrandResponse>> changeVehicleAmountByDepositing(
            @PathVariable String brandName, @PathVariable String vehicleName);
}
