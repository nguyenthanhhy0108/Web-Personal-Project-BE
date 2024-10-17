package com.wjh.repository;

import com.wjh.entity.VehicleDepositeContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleDepositeContractRepository extends JpaRepository<VehicleDepositeContract, String> {
    List<VehicleDepositeContract> findAllByEmail(String email);
    VehicleDepositeContract findByContractId(String contractId);
    void deleteByContractId(String contractId);
}
