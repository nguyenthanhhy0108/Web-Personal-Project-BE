package com.wjh.mapper;

import com.wjh.dto.request.ProfileCreationRequest;
import com.wjh.dto.response.ProfileResponse;
import com.wjh.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile toProfile(ProfileCreationRequest profileCreationRequest);
    ProfileResponse toProfileResponse(Profile profile);
}
