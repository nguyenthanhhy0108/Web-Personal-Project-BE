package com.wjh.controller;

import com.wjh.dto.request.ChangeVehicleAmountRequest;
import com.wjh.dto.request.VehicleCreationRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.VehicleWithBrandResponse;
import com.wjh.service.VehicleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle-inventory")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @GetMapping("/vehicles/{brandName}/{vehicleName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<VehicleWithBrandResponse>> findByBrandNameAndVehicleName(
            @PathVariable String brandName, @PathVariable String vehicleName) {
        return ResponseEntity.ok(ApiResponse.<VehicleWithBrandResponse>builder()
                        .data(this.vehicleService.findByBrandNameAndVehicleName(brandName, vehicleName))
                .build());
    }


    @PutMapping("/vehicles/{brandName}/{vehicleName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<VehicleWithBrandResponse>> updateVehicleByDepositing(
            @PathVariable String brandName, @PathVariable String vehicleName) {
        return ResponseEntity.ok(ApiResponse.<VehicleWithBrandResponse>builder()
                        .data(this.vehicleService.changeVehicleByDepositing(brandName, vehicleName))
                .build());
    }


    @PostMapping("/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<VehicleWithBrandResponse>> createVehicle(
            @RequestBody @Valid VehicleCreationRequest vehicleCreationRequest) {
        return ResponseEntity.ok(ApiResponse.<VehicleWithBrandResponse>builder()
                .data(this.vehicleService.saveVehicle(vehicleCreationRequest))
                .build());
    }


    @PutMapping("/vehicles/amount")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<VehicleWithBrandResponse>> changeVehicleAmount(
            @RequestBody @Valid ChangeVehicleAmountRequest changeVehicleAmountRequest) {
        return ResponseEntity.ok(ApiResponse.<VehicleWithBrandResponse>builder()
                .data(this.vehicleService.changeAmountOfVehicle(changeVehicleAmountRequest))
                .build());
    }


    @DeleteMapping("/vehicles/{vehicleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable @NotBlank String vehicleId) {
        this.vehicleService.deleteVehicle(vehicleId);
    }


    @GetMapping("/vehicles")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<VehicleWithBrandResponse>>> getAllVehicles() {
        return ResponseEntity.ok(ApiResponse.<List<VehicleWithBrandResponse>>builder()
                .data(this.vehicleService.getAllVehicles())
                .build());
    }


    @GetMapping("/vehicles/{brandName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<VehicleWithBrandResponse>>> getAllVehicleOfBrand(
            @PathVariable @NotBlank String brandName) {
        return ResponseEntity.ok(ApiResponse.<List<VehicleWithBrandResponse>>builder()
                .data(this.vehicleService.getAllVehiclesByBrandName(brandName))
                .build());
    }


    @DeleteMapping("/vehicles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllVehicleOfBrand(@RequestParam("brand") @NotBlank String brandName) {
        this.vehicleService.deleteAllVehiclesByBrandName(brandName);
    }


    @GetMapping("/vehicles/names")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<String>>> getAllVehicleNames() {
        return ResponseEntity.ok(
                ApiResponse.<List<String>>builder().data(this.vehicleService.getAllVehicleNames()).build());
    }


    @GetMapping("/vehicles/{brandName}/names")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<String>>> getAllVehicleNamesOfBrand(
            @PathVariable @NotBlank String brandName) {
        return ResponseEntity.ok(
                ApiResponse.<List<String>>builder()
                        .data(this.vehicleService.getAllVehicleNamesOfBrandName(brandName))
                        .build());
    }


    @GetMapping("/vehicles/{vehicleName}/brand")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<String>> getBrandNameByVehicleName(
            @PathVariable @NotBlank String vehicleName) {
        return ResponseEntity.ok(ApiResponse.<String>builder()
                        .data(this.vehicleService.getVehicleBrandNameByVehicleName(vehicleName))
                .build());
    }
}