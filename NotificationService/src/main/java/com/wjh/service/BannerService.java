package com.wjh.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.wjh.dto.request.BannerRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.BannerResponse;
import com.wjh.entity.Banner;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.mapper.BannerMapper;
import com.wjh.repository.BannerRepository;
import com.wjh.repository.UserConnectionClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BannerService {
    private final BannerRepository bannerRepository;
    private GridFsTemplate gridFsTemplate;
    private GridFsOperations gridFsOperations;
    private final BannerMapper bannerMapper;
    private final UserConnectionClient userConnectionClient;
    private final MailSenderService mailSenderService;


    public List<BannerResponse> findAll() {
        return this.bannerRepository.findAllByOrderByBannerCreatedAtDesc()
                .stream()
                .map(this.bannerMapper::toBannerResponse)
                .toList();
    }


    @PreAuthorize("hasAuthority('STAFF')")
    @Transactional
    public BannerResponse saveBanner(BannerRequest bannerRequest) throws IOException {

        System.out.println(SecurityContextHolder.getContext());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authorities: " + authentication.getAuthorities());

        try {
            ObjectId bannerImageId = gridFsTemplate.store(
                    bannerRequest.getBannerImage().getInputStream(),
                    bannerRequest.getBannerImage().getOriginalFilename(),
                    bannerRequest.getBannerImage().getContentType());
            Banner banner = this.bannerMapper.toBanner(bannerRequest);
            banner.setBannerImageId(bannerImageId);
            banner.setBannerCreatedAt(LocalDateTime.now());

            Banner savedBanner = bannerRepository.save(banner);

            boolean retrievalFailed = isRetrievalFailed(bannerRequest);
            if (retrievalFailed) {
                throw new AppException(ErrorCode.RETRIEVE_EMAILS_FAIL);
            }

            return bannerMapper.toBannerResponse(savedBanner);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.SAVE_BANNER_FAIL);
        }
    }

    private boolean isRetrievalFailed(BannerRequest bannerRequest) {
        ResponseEntity<ApiResponse<List<String>>> response =
                this.userConnectionClient.getAllNecessaryEmails();

        boolean retrievalFailed = false;

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            ApiResponse<List<String>> apiResponse = response.getBody();

            if (apiResponse.getCode() == 1000) {
                List<String> emails = apiResponse.getData();
                emails.forEach(email ->
                        CompletableFuture.runAsync(() -> {
                            try {
                                this.mailSenderService.sendAnnouncementEmail(email, bannerRequest);
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                                log.error("Failed to send email to {}", email);
                            }
                        })
                );
            } else {
                retrievalFailed = true;
            }
        } else {
            retrievalFailed = true;
        }
        return retrievalFailed;
    }


    public GridFsResource getBannerImage(String bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_EXISTED));
        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(banner.getBannerImageId())));
        if (file != null) {
            return gridFsOperations.getResource(file);
        }
        return null;
    }


    @Transactional
    @PreAuthorize("hasAuthority('STAFF')")
    public void deleteBanner(String bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_EXISTED));
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(banner.getBannerImageId())));
        try {
            bannerRepository.deleteById(bannerId);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.DELETING_ERROR);
        }
    }
}
