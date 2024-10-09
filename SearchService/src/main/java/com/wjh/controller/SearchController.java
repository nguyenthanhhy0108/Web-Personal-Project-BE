package com.wjh.controller;

import com.wjh.dto.request.CarSearchParams;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.CarsSearchResponse;
import com.wjh.service.VehicleInventorySearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/search")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {

    private final VehicleInventorySearchService vehicleInventorySearchService;


    @GetMapping("/brands/{typedString}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<String>>> recommendBrandNames(
            @PathVariable @NotBlank @NotNull String typedString) {
        return ResponseEntity.ok(ApiResponse.<List<String>>builder()
                        .data(this.vehicleInventorySearchService.recommendBrandNames(typedString))
                .build());
    }


    @GetMapping("/vehicles/{typedString}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<String>>> recommendVehicleNames(
            @PathVariable @NotBlank @NotNull String typedString) {
        return ResponseEntity.ok(ApiResponse.<List<String>>builder()
                .data(this.vehicleInventorySearchService.recommendVehicleNames(typedString))
                .build());
    }


    @GetMapping("/vehicles/{brandName}/{typedString}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<String>>> recommendVehicleNamesOfBrand(
            @PathVariable @NotBlank @NotNull String brandName,
            @PathVariable @NotBlank @NotNull String typedString) {
        return ResponseEntity.ok(ApiResponse.<List<String>>builder()
                .data(this.vehicleInventorySearchService
                        .recommendVehicleNamesOfBrand(brandName.toLowerCase(), typedString))
                .build());
    }


    @PostMapping("/vehicles")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<CarsSearchResponse>> relevantVehicles(
            @RequestBody @Valid CarSearchParams carSearchParams) {
        return ResponseEntity.ok(ApiResponse.<CarsSearchResponse>builder()
                        .data(this.vehicleInventorySearchService.getRelevantVehicles(carSearchParams))
                .build());
    }
}
