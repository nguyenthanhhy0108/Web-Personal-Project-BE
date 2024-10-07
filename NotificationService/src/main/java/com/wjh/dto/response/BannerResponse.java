package com.wjh.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BannerResponse {
    private String bannerId;
    private String bannerTitle;
    private String bannerDescription;
    private String bannerUrl;
    private ObjectId bannerImageId;
    private LocalDateTime bannerCreatedAt;
}
