package com.wjh.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.wjh.dto.request.BannerRequest;
import com.wjh.dto.response.BannerResponse;
import com.wjh.entity.Banner;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.mapper.BannerMapper;
import com.wjh.repository.BannerRepository;
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
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BannerService {
    private final BannerRepository bannerRepository;
    private GridFsTemplate gridFsTemplate;
    private GridFsOperations gridFsOperations;
    private final BannerMapper bannerMapper;


    public List<BannerResponse> findAll() {
        return this.bannerRepository.findAll()
                .stream()
                .map(this.bannerMapper::toBannerResponse)
                .toList();
    }


    @Transactional
    public BannerResponse saveBanner(BannerRequest bannerRequest) throws IOException {
        try {
            ObjectId bannerImageId = gridFsTemplate.store(
                    bannerRequest.getBannerImage().getInputStream(),
                    bannerRequest.getBannerImage().getOriginalFilename(),
                    bannerRequest.getBannerImage().getContentType());
            Banner banner = this.bannerMapper.toBanner(bannerRequest);
            banner.setBannerImageId(bannerImageId);
            return this.bannerMapper.toBannerResponse(bannerRepository.save(banner));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.SAVE_BANNER_FAIL);
        }
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
