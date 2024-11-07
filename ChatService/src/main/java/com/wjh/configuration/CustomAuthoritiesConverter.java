package com.wjh.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        String REALM_ACCESS = "realm_access";

        Map<String, Object> realmAccessMap = source.getClaimAsMap(REALM_ACCESS);
        Object roles = realmAccessMap.get("roles");

        if (roles instanceof List<?> stringRoles) {
            return  ((List<String>) stringRoles)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        return List.of();
    }


}
