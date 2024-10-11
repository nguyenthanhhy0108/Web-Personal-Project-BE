package com.wjh.service;

import com.wjh.dto.request.VehicleBrandRequest;
import com.wjh.dto.response.VehicleBrandResponse;
import com.wjh.entity.VehicleBrand;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.mapper.VehicleBrandMapper;
import com.wjh.repository.VehicleBrandRepository;
import com.wjh.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleBrandService {

    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleBrandMapper vehicleBrandMapper;

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public VehicleBrandResponse saveVehicleBrand(VehicleBrandRequest vehicleBrandRequest) {
        VehicleBrand vehicleBrand = vehicleBrandRepository.findByBrandName(vehicleBrandRequest.getBrandName());
        if (vehicleBrand != null) {
            throw new AppException(ErrorCode.BRAND_EXISTED);
        }
        vehicleBrand = vehicleBrandMapper.toVehicleBrand(vehicleBrandRequest);
        VehicleBrand saved = vehicleBrandRepository.save(vehicleBrand);
        return vehicleBrandMapper.toVehicleBrandResponse(saved);
    }


    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteVehicleBrand(String brandName) {
        VehicleBrand vehicleBrand = vehicleBrandRepository.findByBrandName(brandName);
        if (vehicleBrand == null) {
            throw new AppException(ErrorCode.BRAND_NOT_EXIST);
        } else {
            if (this.vehicleRepository.existsByVehicleBrand(vehicleBrand)) {
                throw new AppException(ErrorCode.FOREIGN_KEY_ERROR);
            }
            vehicleBrandRepository.delete(vehicleBrand);
        }
    }


    public List<VehicleBrandResponse> getAllVehicleBrands() {
        List<VehicleBrand> vehicleBrands = vehicleBrandRepository.findAll();
        return vehicleBrands.stream().map(this.vehicleBrandMapper::toVehicleBrandResponse).toList();
    }

    public List<String> getAllBrandNames() {
        List<VehicleBrand> vehicleBrands = vehicleBrandRepository.findAll();
        return vehicleBrands.stream().map(VehicleBrand::getBrandName).toList();
    }
}
