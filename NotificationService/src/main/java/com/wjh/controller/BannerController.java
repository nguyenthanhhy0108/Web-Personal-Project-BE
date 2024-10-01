package com.wjh.controller;

import com.wjh.dto.response.BannerResponse;
import com.wjh.entity.Banner;
import com.wjh.service.BannerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/banners")
    public List<BannerResponse> getAllBanners() {
        return this.bannerService.findAll();
    }


    @PostMapping(value = "/banners", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Banner> uploadBanner(
            @RequestParam("bannerTitle") String bannerTitle,
            @RequestParam("bannerDescription") String bannerDescription,
            @RequestParam("bannerUrl") String bannerUrl,
            @RequestParam("bannerImage") MultipartFile bannerImage) throws IOException {

        Banner banner = Banner.builder()
                .bannerTitle(bannerTitle)
                .bannerDescription(bannerDescription)
                .bannerUrl(bannerUrl)
                .build();

        Banner savedBanner = bannerService.saveBanner(banner, bannerImage);

        return ResponseEntity.ok(savedBanner);
    }


    @GetMapping("/banners/{id}")
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
}
