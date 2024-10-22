package com.wjh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wjh.dto.request.ProfileCreationRequest;
import com.wjh.dto.response.ProfileResponse;
import com.wjh.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;
    private ProfileCreationRequest profileCreationRequest;
    private ProfileResponse profileResponse;
    private LocalDate dateOfBirth;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        dateOfBirth = LocalDate.of(1990, 1, 1);
        profileCreationRequest = ProfileCreationRequest.builder()
                .password("test123456")
                .email("test@test.uit")
                .username("unit_test")
                .firstName("Test")
                .lastName("Test")
                .dateOfBirth(dateOfBirth)
                .build();

        profileResponse = ProfileResponse.builder()
                .profileID("1e7534321183")
                .email("test@test.uit")
                .username("unit_test")
                .firstName("Test")
                .lastName("Test")
                .dateOfBirth(dateOfBirth)
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(profileCreationRequest);

        Mockito.when(userService.createProfile(any())).thenReturn(profileResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/create-user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
        );
    }

    @Test
    void createUser_invalidUsername_fail() throws Exception {
        profileCreationRequest.setUsername("test");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(profileCreationRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/create-user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(9006)
                );
    }


    @Test
    void createUser_invalidPassword_fail() throws Exception {
        profileCreationRequest.setPassword("test");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(profileCreationRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/create-user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(9007)
                );
    }


    @Test
    void createUser_invalidDateOfBirth_fail() throws Exception {
        dateOfBirth = LocalDate.now();
        profileCreationRequest.setDateOfBirth(dateOfBirth);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(profileCreationRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/create-user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(9005)
                );
    }
}
