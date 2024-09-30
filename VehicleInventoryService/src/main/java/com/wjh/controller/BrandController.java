package com.wjh.controller;

import com.wjh.dto.request.VehicleBrandRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.VehicleBrandResponse;
import com.wjh.service.VehicleBrandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle-inventory")
public class BrandController {

    private final VehicleBrandService vehicleBrandService;

    @Autowired
    public BrandController(VehicleBrandService vehicleBrandService) {
        this.vehicleBrandService = vehicleBrandService;
    }

    @GetMapping("/brands")
    public ApiResponse<List<VehicleBrandResponse>> getAllBrands() {
        return ApiResponse.<List<VehicleBrandResponse>>builder()
                .data(this.vehicleBrandService.getAllVehicleBrands())
                .build();
    }

    @PostMapping("/brands")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VehicleBrandResponse> createBrand(@RequestBody @Valid VehicleBrandRequest vehicleBrandRequest) {
        return ApiResponse.<VehicleBrandResponse>builder()
                .data(this.vehicleBrandService.saveVehicleBrand(vehicleBrandRequest))
                .build();
    }

    @DeleteMapping("/brands/{brandName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(@PathVariable @NotBlank String brandName) {
        this.vehicleBrandService.deleteVehicleBrand(brandName);
    }
}
