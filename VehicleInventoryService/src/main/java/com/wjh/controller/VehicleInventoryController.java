package com.wjh.controller;

import com.wjh.dto.request.ChangeVehicleAmountRequest;
import com.wjh.dto.request.VehicleBrandRequest;
import com.wjh.dto.request.VehicleCreationRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.VehicleBrandResponse;
import com.wjh.dto.response.VehicleWithBrandResponse;
import com.wjh.service.VehicleBrandService;
import com.wjh.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle-inventory")
public class VehicleInventoryController {

    private final VehicleService vehicleService;
    private final VehicleBrandService vehicleBrandService;

    @Autowired
    public VehicleInventoryController(VehicleService vehicleService, VehicleBrandService vehicleBrandService) {
        this.vehicleService = vehicleService;
        this.vehicleBrandService = vehicleBrandService;
    }

    @GetMapping("/all-brand")
    public ApiResponse<List<VehicleBrandResponse>> getAllBrands() {
        return ApiResponse.<List<VehicleBrandResponse>>builder()
                .data(this.vehicleBrandService.getAllVehicleBrands())
                .build();
    }

    @PostMapping("/save-brand")
    public ApiResponse<VehicleBrandResponse> saveBrand(@RequestBody @Valid VehicleBrandRequest vehicleBrandRequest) {
        return ApiResponse.<VehicleBrandResponse>builder()
                .data(this.vehicleBrandService.saveVehicleBrand(vehicleBrandRequest))
                .build();
    }

    @DeleteMapping("/delete-brand")
    public void deleteBrand(@RequestBody @Valid VehicleBrandRequest vehicleBrandRequest) {
        this.vehicleBrandService.deleteVehicleBrand(vehicleBrandRequest);
    }


    @PostMapping("/save-vehicle")
    public ApiResponse<VehicleWithBrandResponse> saveVehicle(@RequestBody @Valid VehicleCreationRequest vehicleCreationRequest) {
        return ApiResponse.<VehicleWithBrandResponse>builder()
                .data(this.vehicleService.saveVehicle(vehicleCreationRequest))
                .build();
    }

    @PostMapping("/change-vehicle-amount")
    public ApiResponse<VehicleWithBrandResponse> changeVehicleAmount(@RequestBody @Valid ChangeVehicleAmountRequest changeVehicleAmountRequest) {
        return ApiResponse.<VehicleWithBrandResponse>builder()
                .data(this.vehicleService.changeAmountOfVehicle(changeVehicleAmountRequest))
                .build();
    }

    @DeleteMapping("/delete-vehicle")
    public void deleteVehicle(@RequestParam("vehicle-name") String vehicleName,
                              @RequestParam("brand-name") String vehicleBrandName) {
        this.vehicleService.deleteVehicle(vehicleName, vehicleBrandName);
    }

    @GetMapping("/all-vehicle")
    public ApiResponse<List<VehicleWithBrandResponse>> getAllVehicles() {
        return ApiResponse.<List<VehicleWithBrandResponse>>builder()
                .data(this.vehicleService.getAllVehicles())
                .build();
    }

    @GetMapping("/all-vehicle-of-brand")
    public ApiResponse<List<VehicleWithBrandResponse>> getAllVehicleOfBrand(@RequestParam("brand-name") String brandName) {
        return ApiResponse.<List<VehicleWithBrandResponse>>builder()
                .data(this.vehicleService.getAllVehiclesByBrandName(brandName))
                .build();
    }
}
