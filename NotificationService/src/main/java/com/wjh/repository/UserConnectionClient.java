package com.wjh.repository;

import com.wjh.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserConnectionClient {
    @GetMapping(value = "/user/emails")
    ResponseEntity<ApiResponse<List<String>>> getAllNecessaryEmails();
}