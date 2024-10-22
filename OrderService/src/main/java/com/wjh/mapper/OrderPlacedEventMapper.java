package com.wjh.mapper;

import com.wjh.dto.request.VehicleDepositeContractRequest;
import com.wjh.event.OrderPlacedEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderPlacedEventMapper {
    OrderPlacedEvent toOrderPlacedEvent(VehicleDepositeContractRequest vehicleDepositeContractRequest);
}
