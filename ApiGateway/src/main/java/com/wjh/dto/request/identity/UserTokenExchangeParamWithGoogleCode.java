package com.wjh.dto.request.identity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTokenExchangeParamWithGoogleCode {
    String grant_type;
    String client_id;
    String client_secret;
    String code;
    String redirect_uri;
}