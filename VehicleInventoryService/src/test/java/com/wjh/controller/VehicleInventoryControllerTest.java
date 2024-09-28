package com.wjh.controller;

import com.wjh.dto.response.VehicleBrandResponse;
import com.wjh.service.VehicleBrandService;
import com.wjh.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class VehicleInventoryControllerTest {

    @MockBean
    VehicleService vehicleService;
    @MockBean
    VehicleBrandService vehicleBrandService;
    @Autowired
    private MockMvc mockMvc;
    VehicleBrandResponse vehicleBrandResponse;
    List<VehicleBrandResponse> vehicleBrandResponseList;

    @BeforeEach
    public void setUp() {
        vehicleBrandResponse = VehicleBrandResponse.builder().brandName("BMW").build();
        vehicleBrandResponseList = new ArrayList<>();
        vehicleBrandResponseList.add(vehicleBrandResponse);
    }

    @Test
    void getAllBrands_success() throws Exception {
        Mockito.when(vehicleBrandService.getAllVehicleBrands()).thenReturn(vehicleBrandResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get("/vehicle-inventory/all-brand")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
                );
    }
}
