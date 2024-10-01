package com.wjh.controller;

import com.wjh.dto.request.BannerRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.BannerResponse;
import com.wjh.service.BannerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping("/notification")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BannerController {

    private final BannerService bannerService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/banners")
    public ResponseEntity<ApiResponse<List<BannerResponse>>>  getAllBanners() {
        return ResponseEntity.ok(ApiResponse.<List<BannerResponse>>builder()
                .data(this.bannerService.findAll())
                .build());
    }


    @PostMapping(value = "/banners", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<BannerResponse>> uploadBanner(@ModelAttribute @Valid BannerRequest bannerRequest)
            throws IOException {

        BannerResponse savedBanner = bannerService.saveBanner(bannerRequest);

        return ResponseEntity.ok(ApiResponse.<BannerResponse>builder().data(savedBanner).build());
    }


    @GetMapping("/banners/image/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws IOException {
        GridFsResource resource = this.bannerService.getBannerImage(id);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] imageData;
        try (InputStream inputStream = resource.getInputStream()) {
            imageData = StreamUtils.copyToByteArray(inputStream);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ResponseEntity.ok().headers(headers).body(imageData);
    }

    @DeleteMapping("/banners/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBanner(@PathVariable String id) {
        bannerService.deleteBanner(id);
    }
}
