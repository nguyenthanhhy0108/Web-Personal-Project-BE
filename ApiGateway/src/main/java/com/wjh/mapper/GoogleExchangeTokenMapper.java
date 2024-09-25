package com.wjh.mapper;

import com.wjh.dto.request.identity.GoogleCodeParam;
import com.wjh.dto.request.identity.UserCredentials;
import com.wjh.dto.request.identity.UserTokenExchangeParam;
import com.wjh.dto.request.identity.UserTokenExchangeParamWithGoogleCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GoogleExchangeTokenMapper {

    @Mapping(source = "redirectUri", target = "redirect_uri")
    UserTokenExchangeParamWithGoogleCode toUserTokenExchangeParam(GoogleCodeParam googleCodeParam);
}
