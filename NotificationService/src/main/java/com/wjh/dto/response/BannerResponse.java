package com.wjh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BannerResponse {
    private String bannerId;
    private String bannerTitle;
    private String bannerDescription;
    private String bannerUrl;
    private ObjectId bannerImageId;
}
