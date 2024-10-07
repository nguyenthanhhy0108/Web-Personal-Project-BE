package com.wjh.repository;

import com.wjh.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "vehicle-inventory-service")
public interface VehicleInventoryClient {
    @GetMapping(value = "/vehicle-inventory/vehicles/names")
    ResponseEntity<ApiResponse<List<String>>> getAllVehicleNames();

    @GetMapping(value = "/vehicle-inventory/brands/names")
    ResponseEntity<ApiResponse<List<String>>> getAllBrandNames();
}
