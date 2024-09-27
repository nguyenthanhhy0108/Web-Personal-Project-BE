package com.wjh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VehicleInventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehicleInventoryServiceApplication.class, args);
    }
}
