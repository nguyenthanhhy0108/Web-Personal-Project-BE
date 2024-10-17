package com.wjh.mapper;

import com.wjh.dto.request.VehicleDepositeContractRequest;
import com.wjh.dto.response.VehicleDepositeContractResponse;
import com.wjh.entity.VehicleDepositeContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleDepositeContractMapper {
    @Mapping(target = "status", source = "contractPayment.status")
    VehicleDepositeContractResponse toVehicleDepositeContractResponse(VehicleDepositeContract vehicleDepositeContract);
    VehicleDepositeContract toVehicleDepositeContract(VehicleDepositeContractRequest vehicleDepositeContractRequest);
}
