package com.wjh.service;

import com.wjh.dto.request.ProfileCreationRequest;
import com.wjh.dto.request.identity.Credential;
import com.wjh.dto.request.identity.UserCreationParam;
import com.wjh.dto.response.ProfileCreationResponse;
import com.wjh.entity.Profile;
import com.wjh.entity.UserSetting;
import com.wjh.exception.ErrorNormalizer;
import com.wjh.mapper.ProfileMapper;
import com.wjh.repository.IdentityClient;
import com.wjh.repository.ProfileRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class UserService {
    private final IdentityClient identityClient;
    private final IdentityService identityService;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final ErrorNormalizer errorNormalizer;
    private final UserSettingService userSettingService;

    public ProfileCreationResponse createProfile(ProfileCreationRequest request) {
        ResponseEntity<?> userCreationKeyCloakResponse;
        try {
            log.info("Bearer {}", identityService.exchangeClientToken());
            //Create user in KeyCloak
            userCreationKeyCloakResponse = identityClient.createUserKeyCloak(
                    "Bearer " + identityService.exchangeClientToken(),
                    UserCreationParam.builder()
                            .username(request.getUsername())
                            .enabled(true)
                            .email(request.getEmail())
                            .emailVerified(false)
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .credentials(List.of(Credential.builder()
                                    .type("password")
                                    .value(request.getPassword())
                                    .temporary(false)
                                    .build()))
                            .build());
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw errorNormalizer.handleKeyCloakException(feignException);
        }

        //Create user in DB
        Profile createdProfile = profileMapper.toProfile(request);

        UserSetting userSetting = new UserSetting();
        userSetting.setProfile(createdProfile);
        createdProfile.setUserSetting(userSetting);

        createdProfile.setUserID(extractKeyCloakUserID(userCreationKeyCloakResponse));
        Profile resultProfile = profileRepository.save(createdProfile);

        log.info("Created user profile: {}", resultProfile.getProfileID());

        return profileMapper.toProfileCreationResponse(resultProfile);
    }

    private String extractKeyCloakUserID(ResponseEntity<?> createKeyCloakUserResponse){
        String location = Objects.requireNonNull(createKeyCloakUserResponse.getHeaders().get("Location"))
                .get(0);
        String[] splittedString = location.split("/");
        return splittedString[splittedString.length - 1];
    }


    @PreAuthorize("hasAuthority('STAFF')")
    public List<String> findAllNecessaryEmail() {

        System.out.println(SecurityContextHolder.getContext());

        List<String> profileIdList = this.userSettingService.findAllNotifyTrueAndActiveTrueProfileId();
        List<Profile> profiles = this.profileRepository.findByProfileIDIn(profileIdList);
        return profiles.stream().map(Profile::getEmail).toList();
    }
}
