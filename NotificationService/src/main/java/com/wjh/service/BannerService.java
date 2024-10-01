package com.wjh.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.wjh.dto.response.BannerResponse;
import com.wjh.entity.Banner;
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
import org.springframework.web.multipart.MultipartFile;

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
    public Banner saveBanner(Banner banner, MultipartFile imageFile) throws IOException {
        ObjectId bannerImageId = gridFsTemplate.store(
                imageFile.getInputStream(), imageFile.getOriginalFilename(), imageFile.getContentType());
        banner.setBannerImageId(bannerImageId);
        return bannerRepository.save(banner);
    }


    public GridFsResource getBannerImage(String bannerId) throws IOException {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
        GridFSFile file = gridFsTemplate.findOne(
                Query.query(Criteria.where("_id").is(banner.getBannerImageId())));
        if (file != null) {
            return gridFsOperations.getResource(file);
        }
        return null;
    }


    @Transactional
    public void deleteBannerImage(String bannerId) {
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(banner.getBannerImageId())));
        bannerRepository.deleteById(bannerId);
    }
}
