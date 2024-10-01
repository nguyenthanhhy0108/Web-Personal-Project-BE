package com.wjh.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerRequest {
    @NotBlank(message = "BLANK_BANNER_TITLE")
    @NotNull
    private String bannerTitle;
    @NotBlank(message = "BLANK_BANNER_DESCRIPTION")
    @NotNull
    private String bannerDescription;
    //Usually HTTP page
    @NotBlank(message = "BLANK_BANNER_URL")
    @NotNull
    private String bannerUrl;
    @NotNull
    private MultipartFile bannerImage;
}
