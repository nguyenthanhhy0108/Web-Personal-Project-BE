package com.wjh.repository;

import com.wjh.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "application-feign-client", url = "${application.domain}")
public interface ApplicationFeignClient {

    @GetMapping(value = "{url}")
    ApiResponse<?> getVehicleServiceResponse(@PathVariable String url);
}
