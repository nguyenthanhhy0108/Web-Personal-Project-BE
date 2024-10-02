package com.wjh.mapper;

import com.wjh.dto.response.UserSettingResponse;
import com.wjh.entity.UserSetting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSettingMapper {
    UserSettingResponse toResponse(UserSetting userSetting);
}
