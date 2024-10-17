package com.wjh.controller;

import com.wjh.dto.request.VehicleDepositeContractRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.VehicleDepositeContractResponse;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.service.VehicleDepositeContractService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/deposite-contract")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleDepositeContractController {

    private final VehicleDepositeContractService vehicleDepositeContractService;


    @PostMapping("/contracts")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<VehicleDepositeContractResponse>> depositeContract(
            @ModelAttribute @Valid VehicleDepositeContractRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<VehicleDepositeContractResponse>builder()
                .data(this.vehicleDepositeContractService.saveVehicleDepositeContract(request))
                .build());
    }


    @DeleteMapping("/contracts/{contractId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContractById(@PathVariable String contractId) {
        this.vehicleDepositeContractService.deleteVehicleDepositeContract(contractId);
    }


    @GetMapping("/contracts/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<VehicleDepositeContractResponse>> getContractById(@PathVariable String contractId) {
        return ResponseEntity.ok(
                ApiResponse.<VehicleDepositeContractResponse>builder()
                .data(this.vehicleDepositeContractService.getVehicleDepositeContractById(contractId))
                .build());
    }


    @GetMapping("/contracts/pdf/{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getContractPdfById(@PathVariable String contractId) {
        GridFsResource resource = this.vehicleDepositeContractService.getContractPdfByContractId(contractId);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] pdfData;
        try (InputStream inputStream = resource.getInputStream()) {
            pdfData = StreamUtils.copyToByteArray(inputStream);
        } catch (IOException e) {
            throw new AppException(ErrorCode.GET_CONTRACT_FAIL);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok().headers(headers).body(pdfData);
    }


    @GetMapping("/contracts/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<VehicleDepositeContractResponse>>> getAllContractByEmail(@PathVariable String email) {
        return ResponseEntity.ok(
                ApiResponse.<List<VehicleDepositeContractResponse>>builder()
                .data(this.vehicleDepositeContractService.findAllVehicleDepositeContractsByEmail(email))
                .build());
    }
}
