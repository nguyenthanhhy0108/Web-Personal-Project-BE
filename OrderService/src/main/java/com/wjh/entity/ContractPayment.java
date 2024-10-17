package com.wjh.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractPayment {

    @Id
    private String contractId;
    //deposited, full-paid, unpaid
    private String status = "unpaid";

    @OneToOne
    @MapsId
    private VehicleDepositeContract vehicleDepositeContract;
}
