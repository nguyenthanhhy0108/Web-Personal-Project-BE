package com.wjh.repository;

import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.VehicleWithBrandResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "vehicle-inventory-service")
public interface VehicleInventoryClient {
    @GetMapping(value = "/vehicle-inventory/vehicles/names")
    ResponseEntity<ApiResponse<List<String>>> getAllVehicleNames();

    @GetMapping(value = "/vehicle-inventory/brands/names")
    ResponseEntity<ApiResponse<List<String>>> getAllBrandNames();

    @GetMapping(value = "/vehicle-inventory/vehicles/{brandName}/names")
    ResponseEntity<ApiResponse<List<String>>> getAllVehicleNamesOfBrand(@PathVariable("brandName") String brandName);

    @GetMapping(value = "/vehicle-inventory/vehicles")
    ResponseEntity<ApiResponse<List<VehicleWithBrandResponse>>> getAllVehicles();

    @GetMapping(value = "/vehicle-inventory/vehicles/{brandName}")
    ResponseEntity<ApiResponse<List<VehicleWithBrandResponse>>> getAllVehiclesOfBrand(
            @PathVariable("brandName") String brandName);
}
