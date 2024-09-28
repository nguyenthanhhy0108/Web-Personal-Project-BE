package com.wjh.service;

import com.wjh.dto.request.ChangeVehicleAmountRequest;
import com.wjh.dto.request.VehicleCreationRequest;
import com.wjh.dto.response.VehicleWithBrandResponse;
import com.wjh.entity.Vehicle;
import com.wjh.entity.VehicleBrand;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.mapper.VehicleMapper;
import com.wjh.repository.VehicleBrandRepository;
import com.wjh.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public VehicleWithBrandResponse saveVehicle(VehicleCreationRequest vehicleRequest) {
        VehicleBrand vehicleBrand = null;

        Vehicle vehicle = vehicleRepository.findByVehicleName(vehicleRequest.getVehicleName());
        if (vehicle != null) {
            int newAmount = vehicleRequest.getNumberOfRemaining() + vehicle.getNumberOfRemaining();
            vehicle.setNumberOfRemaining(newAmount);
        } else {
            vehicle = vehicleMapper.toVehicle(vehicleRequest);
            vehicleBrand = this.vehicleBrandRepository.findByBrandName(vehicleRequest.getVehicleBrandName());
            if (vehicleBrand == null) {
                throw new AppException(ErrorCode.BRAND_NOT_EXIST);
            } else {
                vehicle.setVehicleBrand(vehicleBrand);
            }
        }
        Vehicle saved = vehicleRepository.save(vehicle);
        VehicleWithBrandResponse vehicleWithBrandResponse = vehicleMapper.toVehicleResponse(saved);
        assert vehicleBrand != null;
        vehicleWithBrandResponse.setBrandName(vehicleBrand.getBrandName());

        return vehicleWithBrandResponse;
    }


    @Transactional
    public VehicleWithBrandResponse changeAmountOfVehicle(ChangeVehicleAmountRequest changeVehicleAmountRequest) {
        String vehicleBrand;
        Vehicle vehicle = vehicleRepository.findByVehicleName(changeVehicleAmountRequest.getVehicleName());
        if (vehicle == null) {
            throw new AppException(ErrorCode.VEHICLE_NOT_EXIST);
        }
        else {
            if (-changeVehicleAmountRequest.getAmount() >= vehicle.getNumberOfRemaining()) {
                throw new AppException(ErrorCode.VEHICLE_LESS_THAN_DESIRE);
            }
            vehicle.setNumberOfRemaining(vehicle.getNumberOfRemaining() + changeVehicleAmountRequest.getAmount());
            vehicleBrand = vehicle.getVehicleBrand().getBrandName();
        }
        VehicleWithBrandResponse vehicleWithBrandResponse = vehicleMapper.toVehicleResponse(vehicleRepository.save(vehicle));
        assert vehicleBrand != null;
        vehicleWithBrandResponse.setBrandName(vehicleBrand);
        return vehicleWithBrandResponse;
    }


    @Transactional
    public void deleteVehicle(String vehicleName, String vehicleBrandName) {
        Vehicle vehicle = vehicleRepository.findByVehicleName(vehicleName);
        if (vehicle == null) {
            throw new AppException(ErrorCode.VEHICLE_NOT_EXIST);
        } else {
            if (vehicle.getVehicleBrand().getBrandName().equals(vehicleBrandName)) {
                vehicleRepository.delete(vehicle);
            } else {
                throw new AppException(ErrorCode.VEHICLE_NOT_EXIST);
            }
        }
    }

    public List<VehicleWithBrandResponse> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream().map(this.vehicleMapper::toVehicleResponse).toList();
    }

    public List<VehicleWithBrandResponse> getAllVehiclesByBrandName(String brandName) {
        if (brandName == null) {
            throw new AppException(ErrorCode.BRAND_NOT_EXIST);
        }
        VehicleBrand vehicleBrand = vehicleBrandRepository.findByBrandName(brandName);
        if (vehicleBrand == null) {
            throw new AppException(ErrorCode.BRAND_NOT_EXIST);
        } else {
            return vehicleRepository.findByVehicleBrand(vehicleBrand)
                    .stream().map(this.vehicleMapper::toVehicleResponse).toList();
        }
    }
}
