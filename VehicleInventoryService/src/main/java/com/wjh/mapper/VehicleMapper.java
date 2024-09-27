package com.wjh.mapper;

import com.wjh.dto.request.VehicleRequest;
import com.wjh.dto.response.VehicleResponse;
import com.wjh.entity.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toVehicle(VehicleRequest vehicleBrandRequest);
    VehicleResponse toVehicleResponse(Vehicle vehicle);
}
