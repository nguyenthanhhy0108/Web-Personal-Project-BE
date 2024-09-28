package com.wjh.mapper;

import com.wjh.dto.request.VehicleCreationRequest;
import com.wjh.dto.response.VehicleWithBrandResponse;
import com.wjh.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toVehicle(VehicleCreationRequest vehicleBrandRequest);
    @Mapping(target = "brandName", source = "vehicleBrand.brandName")
    VehicleWithBrandResponse toVehicleResponse(Vehicle vehicle);
}
