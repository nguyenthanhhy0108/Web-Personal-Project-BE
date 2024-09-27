package com.wjh.repository;

import com.wjh.entity.Vehicle;
import com.wjh.entity.VehicleBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle findByVehicleName(String vehicleName);
    List<Vehicle> findByVehicleBrand(VehicleBrand vehicleBrand);
}