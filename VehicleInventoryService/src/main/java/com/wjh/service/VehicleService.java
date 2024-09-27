package com.wjh.service;

import com.wjh.dto.request.ChangeVehicleAmountRequest;
import com.wjh.dto.request.VehicleRequest;
import com.wjh.dto.response.VehicleResponse;
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
    public VehicleResponse saveVehicle(VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepository.findByVehicleName(vehicleRequest.getVehicleName());
        if (vehicle != null) {
            int newAmount = vehicleRequest.getNumberOfRemaining() + vehicle.getNumberOfRemaining();
            vehicle.setNumberOfRemaining(newAmount);
        } else {
            vehicle = vehicleMapper.toVehicle(vehicleRequest);
        }
        Vehicle saved = vehicleRepository.save(vehicle);
        return vehicleMapper.toVehicleResponse(saved);
    }


    @Transactional
    public VehicleResponse changeAmountOfVehicle(ChangeVehicleAmountRequest changeVehicleAmountRequest) {
        Vehicle vehicle = vehicleRepository.findByVehicleName(changeVehicleAmountRequest.getVehicleName());
        if (vehicle == null) {
            throw new AppException(ErrorCode.VEHICLE_NOT_EXIST);
        }
        else {
            vehicle.setNumberOfRemaining(vehicle.getNumberOfRemaining() + changeVehicleAmountRequest.getAmount());
            if (changeVehicleAmountRequest.getAmount() < 0) {
                throw new AppException(ErrorCode.VEHICLE_LESS_THAN_DESIRE);
            }
        }
        return vehicleMapper.toVehicleResponse(vehicleRepository.save(vehicle));
    }


    @Transactional
    public void deleteVehicle(VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepository.findByVehicleName(vehicleRequest.getVehicleName());
        if (vehicle == null) {
            throw new AppException(ErrorCode.VEHICLE_NOT_EXIST);
        } else {
            vehicleRepository.delete(vehicle);
        }
    }

    public List<VehicleResponse> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream().map(this.vehicleMapper::toVehicleResponse).toList();
    }


    public List<VehicleResponse> getAllVehiclesByBrandName(String brandName) {
        VehicleBrand vehicleBrand = vehicleBrandRepository.findByBrandName(brandName);
        if (vehicleBrand == null) {
            throw new AppException(ErrorCode.BRAND_NOT_EXIST);
        } else {
            return vehicleRepository.findByVehicleBrand(vehicleBrand)
                    .stream().map(this.vehicleMapper::toVehicleResponse).toList();
        }
    }
}
