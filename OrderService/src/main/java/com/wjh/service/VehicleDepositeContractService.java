package com.wjh.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.wjh.dto.request.VehicleDepositeContractRequest;
import com.wjh.dto.response.VehicleDepositeContractResponse;
import com.wjh.entity.ContractPayment;
import com.wjh.entity.VehicleDepositeContract;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.mapper.VehicleDepositeContractMapper;
import com.wjh.repository.VehicleDepositeContractRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleDepositeContractService {

    private final VehicleDepositeContractRepository vehicleDepositeContractRepository;
    private GridFsTemplate gridFsTemplate;
    private GridFsOperations gridFsOperations;
    private final VehicleDepositeContractMapper vehicleDepositeContractMapper;


    @Transactional
    public VehicleDepositeContractResponse saveVehicleDepositeContract(VehicleDepositeContractRequest request) {
        //Save pdf
        //Save instance
        try {
            ObjectId contractPdfId = gridFsTemplate.store(
                    request.getContractPdf().getInputStream(),
                    request.getContractPdf().getOriginalFilename(),
                    request.getContractPdf().getContentType());

            VehicleDepositeContract vehicleDepositeContract = this.vehicleDepositeContractMapper
                    .toVehicleDepositeContract(request);
            vehicleDepositeContract.setContractPdfId(contractPdfId.toString());
            vehicleDepositeContract.setCreatedAt(LocalDateTime.now());
            vehicleDepositeContract.setAge(LocalDate.now().getYear() - request.getDateOfBirth().getYear());
            vehicleDepositeContract.setEnabled(true);

            ContractPayment contractPayment = new ContractPayment();
            contractPayment.setVehicleDepositeContract(vehicleDepositeContract);

            vehicleDepositeContract.setContractPayment(contractPayment);

            VehicleDepositeContract savedBanner = this.vehicleDepositeContractRepository
                    .save(vehicleDepositeContract);

            return this.vehicleDepositeContractMapper.toVehicleDepositeContractResponse(savedBanner);
        } catch (RuntimeException | IOException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.SAVE_CONTRACT_FAIL);
        }
    }


    @Transactional
    public void deleteVehicleDepositeContract(String contractId) {
        VehicleDepositeContract vehicleDepositeContract =
                this.vehicleDepositeContractRepository.findByContractId(contractId);
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(vehicleDepositeContract.getContractPdfId())));
        try {
            vehicleDepositeContractRepository.deleteByContractId(contractId);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.DELETING_ERROR);
        }
    }


    public List<VehicleDepositeContractResponse> findAllVehicleDepositeContractsByEmail(String email) {
        return this.vehicleDepositeContractRepository.findAllByEmail(email).stream().map(
                this.vehicleDepositeContractMapper::toVehicleDepositeContractResponse
        ).toList();
    }


    public VehicleDepositeContractResponse getVehicleDepositeContractById(String id) {
        return this.vehicleDepositeContractMapper.toVehicleDepositeContractResponse(
                this.vehicleDepositeContractRepository.findByContractId(id)
        );
    }


    public GridFsResource getContractPdfByContractId(String contractPdfId) {
        ObjectId pdfId;
        if (contractPdfId != null) {
            pdfId = new ObjectId(contractPdfId);
        } else {
            throw new AppException(ErrorCode.INVALID_CONTRACT_ID);
        }
        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(pdfId)));
        if (file != null) {
            return gridFsOperations.getResource(file);
        }
        return null;
    }
}
