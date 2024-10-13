package com.wjh.service;

import com.wjh.dto.request.ForgotPasswordConfirmationRequest;
import com.wjh.dto.request.ForgotPasswordRequest;
import com.wjh.dto.request.ProfileCreationRequest;
import com.wjh.dto.request.identity.Credential;
import com.wjh.dto.request.identity.UserCreationParam;
import com.wjh.dto.request.identity.UserResetPasswordParam;
import com.wjh.dto.response.ProfileCreationResponse;
import com.wjh.entity.Profile;
import com.wjh.entity.UserSetting;
import com.wjh.entity.UserVerifyCode;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.exception.ErrorNormalizer;
import com.wjh.mapper.ProfileMapper;
import com.wjh.repository.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class UserService {
    private final IdentityClient identityClient;
    private final IdentityService identityService;
    private final ProfileRepository profileRepository;
    private final UserVerifyCodeRepository userVerifyCodeRepository;
    private final NotificationClient notificationClient;
    private final ProfileMapper profileMapper;
    private final ErrorNormalizer errorNormalizer;
    private final UserSettingService userSettingService;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

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


    private static String generateVerificationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }


    @Transactional
    public void sendVerificationEmail(ForgotPasswordRequest request) {
        String verificationCode = generateVerificationCode();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);

        Profile profile = this.profileRepository.findByUsername(request.getUsername());
        if (Objects.isNull(profile) || !profile.getEmail().equalsIgnoreCase(request.getEmail())) {
            throw new AppException(ErrorCode.USERNAME_EMAIL_NOT_MATCH);
        }

        try {
            notificationClient.sendVerificationEmail(request.getEmail(), verificationCode);
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
        }

        try {
            this.userVerifyCodeRepository.save(UserVerifyCode.builder()
                    .code(verificationCode)
                    .expiryDate(expiryDate)
                    .email(request.getEmail())
                    .build());
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

    }


    public void confirmResetPassword(ForgotPasswordConfirmationRequest request) {

        String trueCode = this.userVerifyCodeRepository.findByEmail(request.getEmail()).getCode();
        if (!trueCode.equals(request.getCode())) {
            throw new AppException(ErrorCode.WRONG_VERIFICATION_CODE);
        }
        if (this.userVerifyCodeRepository.findByEmail(request.getEmail()).getExpiryDate()
                .isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.EXPIRED_VERIFICATION_CODE);
        }
        try {
            identityClient.userResetPassword(
                    "Bearer " + identityService.exchangeClientToken(),
                    this.profileRepository.findByUsername(request.getUsername()).getUserID(),
                    UserResetPasswordParam.builder().value(request.getNewPassword()).build());
        } catch (FeignException feignException) {
            log.error(feignException.getMessage());
            throw errorNormalizer.handleKeyCloakException(feignException);
        }
    }
}
