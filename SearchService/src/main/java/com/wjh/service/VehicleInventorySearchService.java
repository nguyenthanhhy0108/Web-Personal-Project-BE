package com.wjh.service;

import com.wjh.dto.response.ApiResponse;
import com.wjh.repository.VehicleInventoryClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleInventorySearchService {

    private final VehicleInventoryClient vehicleInventoryClient;


    public List<String> getAllBrandNames() {
        ResponseEntity<ApiResponse<List<String>>> response =
                this.vehicleInventoryClient.getAllBrandNames();
        return Objects.requireNonNull(response.getBody()).getData();
    }


    public List<String> getAllVehicleNames() {
        ResponseEntity<ApiResponse<List<String>>> response =
                this.vehicleInventoryClient.getAllVehicleNames();
        return Objects.requireNonNull(response.getBody()).getData();
    }


    public List<String> recommendBrandNames(String typedString) {
        List<String> brandNames = getAllBrandNames();
        if (typedString == null || typedString.isEmpty()) {
            return brandNames;
        }
        return brandNames.stream().filter(brandName ->
                brandName.toLowerCase().contains(typedString.toLowerCase())).toList();
    }

    public List<String> recommendVehicleNames(String typedString) {
        List<String> vehicleNames = getAllVehicleNames();
        if (typedString == null || typedString.isEmpty()) {
            return vehicleNames;
        }
        return vehicleNames.stream().filter(vehicleName ->
                vehicleName.toLowerCase().contains(typedString.toLowerCase())).toList();
    }
}
