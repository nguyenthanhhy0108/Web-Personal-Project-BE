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


    @PostMapping("/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VehicleWithBrandResponse> createVehicle(@RequestBody @Valid VehicleCreationRequest vehicleCreationRequest) {
        return ApiResponse.<VehicleWithBrandResponse>builder()
                .data(this.vehicleService.saveVehicle(vehicleCreationRequest))
                .build();
    }


    @PutMapping("/vehicles/amount")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<VehicleWithBrandResponse> changeVehicleAmount(@RequestBody @Valid ChangeVehicleAmountRequest changeVehicleAmountRequest) {
        return ApiResponse.<VehicleWithBrandResponse>builder()
                .data(this.vehicleService.changeAmountOfVehicle(changeVehicleAmountRequest))
                .build();
    }


    @DeleteMapping("/vehicles/{vehicleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable @NotBlank String vehicleId) {
        this.vehicleService.deleteVehicle(vehicleId);
    }


    @GetMapping("/vehicles")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<VehicleWithBrandResponse>> getAllVehicles() {
        return ApiResponse.<List<VehicleWithBrandResponse>>builder()
                .data(this.vehicleService.getAllVehicles())
                .build();
    }


    @GetMapping("/vehicles/{brandName}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<VehicleWithBrandResponse>> getAllVehicleOfBrand(@PathVariable @NotBlank String brandName) {
        return ApiResponse.<List<VehicleWithBrandResponse>>builder()
                .data(this.vehicleService.getAllVehiclesByBrandName(brandName))
                .build();
    }


    @DeleteMapping("/vehicles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllVehicleOfBrand(@RequestParam("brand") @NotBlank String brandName) {
        this.vehicleService.deleteAllVehiclesByBrandName(brandName);
    }
}