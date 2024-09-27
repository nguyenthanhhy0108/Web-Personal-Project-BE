package com.wjh.repository;

import com.wjh.entity.VehicleBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, String> {
    VehicleBrand findByBrandName(String brandName);
}
