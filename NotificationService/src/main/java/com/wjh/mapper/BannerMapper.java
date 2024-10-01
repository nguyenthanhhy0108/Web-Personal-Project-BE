package com.wjh.mapper;

import com.wjh.dto.response.BannerResponse;
import com.wjh.entity.Banner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BannerMapper {
    BannerResponse toBannerResponse(Banner banner);
}
