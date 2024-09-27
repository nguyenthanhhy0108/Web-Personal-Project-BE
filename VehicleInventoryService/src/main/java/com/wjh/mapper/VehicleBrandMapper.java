package com.wjh.mapper;

import com.wjh.dto.request.VehicleBrandRequest;
import com.wjh.dto.response.VehicleBrandResponse;
import com.wjh.entity.VehicleBrand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleBrandMapper {
    VehicleBrand toVehicleBrand(VehicleBrandRequest vehicleBrandRequest);
    VehicleBrandResponse toVehicleBrandResponse(VehicleBrand vehicleBrand);
}
