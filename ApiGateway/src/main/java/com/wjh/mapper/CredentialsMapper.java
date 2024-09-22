package com.wjh.mapper;

import com.wjh.dto.request.identity.UserCredentials;
import com.wjh.dto.request.identity.UserTokenExchangeParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    UserTokenExchangeParam toUserTokenExchangeParam(UserCredentials userCredentials);
}
