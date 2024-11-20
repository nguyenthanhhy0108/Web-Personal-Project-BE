package com.wjh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjh.dto.request.CarSearchParams;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.CarsSearchResponse;
import com.wjh.dto.response.VehicleWithBrandResponse;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.repository.VehicleInventoryClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class VehicleInventorySearchService {

    private final VehicleInventoryClient vehicleInventoryClient;

    private final RedisService redisService;

    private final ObjectMapper objectMapper = new ObjectMapper();

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


    public List<String> getAllVehicleNamesOfBrand(String brandName) {
        ResponseEntity<ApiResponse<List<String>>> vehicleNamesOfBrand =
                this.vehicleInventoryClient.getAllVehicleNamesOfBrand(brandName);
        return Objects.requireNonNull(vehicleNamesOfBrand.getBody()).getData();
    }


    public List<String> recommendVehicleNamesOfBrand(String brandName, String typedString) {
        if (brandName == null) {
            throw new AppException(ErrorCode.BRAND_NAME_IS_MISSING);
        } else {
            if (typedString == null) {
                return null;
            }
            else {
                List<String> vehicleNames = getAllVehicleNamesOfBrand(brandName);
                return vehicleNames.stream().filter(name ->
                        name.toLowerCase().contains(typedString.toLowerCase())).toList();
            }
        }
    }


    public List<VehicleWithBrandResponse> getAllVehicles() {
        ResponseEntity<ApiResponse<List<VehicleWithBrandResponse>>> vehicleNamesOfBrand =
                this.vehicleInventoryClient.getAllVehicles();
        return Objects.requireNonNull(vehicleNamesOfBrand.getBody()).getData();
    }


    public List<VehicleWithBrandResponse> getAllVehiclesOfBrand(String brandName) {
        ResponseEntity<ApiResponse<List<VehicleWithBrandResponse>>> vehicleNamesOfBrand =
                this.vehicleInventoryClient.getAllVehiclesOfBrand(brandName);
        return Objects.requireNonNull(vehicleNamesOfBrand.getBody()).getData();
    }


    public CarsSearchResponse getRelevantVehicles(
            CarSearchParams carSearchParams) {

        String jsonValue = (String) this.redisService.get(carSearchParams.toString());

        log.info("Cache: {}", jsonValue);

        if (jsonValue != null) {
            try {
                return this.objectMapper.readValue(jsonValue, CarsSearchResponse.class);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                log.error("Reading Cache failed!");
            }
        }

        List<VehicleWithBrandResponse> allVehicles;
        List<VehicleWithBrandResponse> relevantVehicles;
        CarsSearchResponse response = new CarsSearchResponse();
        if (Objects.equals(carSearchParams.getCarName(), "")) {
            if (Objects.equals(carSearchParams.getBrandName(), "")) {
                relevantVehicles = this.getAllVehicles();
            } else {
                relevantVehicles = this.getAllVehiclesOfBrand(carSearchParams.getBrandName());
            }
        } else {
            if (Objects.equals(carSearchParams.getBrandName(), "")) {
                allVehicles = this.getAllVehicles();
            } else {
                allVehicles = this.getAllVehiclesOfBrand(carSearchParams.getBrandName());
            }
            relevantVehicles = allVehicles.stream().filter(vehicle ->
                    vehicle.getVehicleName().toLowerCase().toLowerCase().contains(
                            carSearchParams.getCarName().toLowerCase())).toList();
        }
        if (carSearchParams.getPageNumber() <= 0 || carSearchParams.getPageSize() <= 0) {
            throw new AppException(ErrorCode.PAGE_ERROR);
        }
        if ((carSearchParams.getPageNumber() - 1) * carSearchParams.getPageSize() > relevantVehicles.size() - 1) {
            throw new AppException(ErrorCode.PAGE_ERROR);
        }

        response.setCars(
                relevantVehicles.subList((carSearchParams.getPageNumber() - 1) * carSearchParams.getPageSize(),
                        Math.min(carSearchParams.getPageNumber() * carSearchParams.getPageSize(),
                                relevantVehicles.size())));
        response.setTotalPages(
                (int)(Math.ceil((double) relevantVehicles.size() / (double) carSearchParams.getPageSize())));

        try {
            this.redisService.save(
                    carSearchParams.toString(), this.objectMapper.writeValueAsString(response), 3600);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            log.error("Caching fail");
        }

        return response;
    }
}
