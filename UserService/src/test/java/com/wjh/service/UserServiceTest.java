package com.wjh.service;

import com.wjh.dto.request.ProfileCreationRequest;
import com.wjh.dto.request.identity.UserCreationParam;
import com.wjh.dto.response.ProfileResponse;
import com.wjh.entity.Profile;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.repository.IdentityClient;
import com.wjh.repository.ProfileRepository;
import lombok.experimental.NonFinal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    private ProfileCreationRequest profileCreationRequest;
    private ProfileResponse profileResponse;
    private Profile profile;

    @MockBean
    ProfileRepository profileRepository;

    @MockBean
    IdentityClient identityClient;

    @Value("${test.token}")
    @NonFinal
    private String token;

    private UserCreationParam userCreationParam;

    @BeforeEach
    public void setUp() {
        LocalDate dateOfBirth;

        dateOfBirth = LocalDate.of(1990, 1, 1);
        profileCreationRequest = ProfileCreationRequest.builder()
                .password("test123456")
                .email("test@test.uit.edu")
                .username("unit_test_service")
                .firstName("Test")
                .lastName("Test")
                .dateOfBirth(dateOfBirth)
                .build();

        profileResponse = ProfileResponse.builder()
                .profileID("1e7534321183")
                .email("test@test.uit.edu")
                .username("unit_test_service")
                .firstName("Test")
                .lastName("Test")
                .dateOfBirth(dateOfBirth)
                .build();

        profile = Profile.builder()
                .profileID("1e7534321183")
                .email("test@test.uit.edu")
                .username("unit_test_service")
                .firstName("Test")
                .lastName("Test")
                .dateOfBirth(dateOfBirth)
                .build();
        userCreationParam = UserCreationParam.builder()
                .email("test@test.uit.edu")
                .username("unit_test_service")
                .firstName("Test")
                .lastName("Test")
                .build();
    }

    @Test
    void createProfile_validRequest_success () {
        Mockito.when(profileRepository.save(any())).thenReturn(profile);

        var response = userService.createProfile(profileCreationRequest);

        Assertions.assertThat(response.getProfileID()).isEqualTo(profileResponse.getProfileID());
    }

    @Test
    void createProfile_existedUsername_fail () {
        profileCreationRequest.setUsername("unit_test");

        AppException appException = new AppException(ErrorCode.USER_EXISTED);
        Mockito.when(identityClient.createUserKeyCloak(any(), any())).thenThrow(appException);

        Assertions.assertThatThrownBy(() -> identityClient.createUserKeyCloak(token, userCreationParam))
                .isInstanceOf(AppException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_EXISTED);
    }

    @Test
    void createProfile_existedEmail_fail () {
        profileCreationRequest.setEmail("test@test.uit.com");

        AppException appException = new AppException(ErrorCode.EMAIL_EXISTED);
        Mockito.when(identityClient.createUserKeyCloak(any(), any())).thenThrow(appException);

        Assertions.assertThatThrownBy(() -> identityClient.createUserKeyCloak(token, userCreationParam))
                .isInstanceOf(AppException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.EMAIL_EXISTED);
    }
}
