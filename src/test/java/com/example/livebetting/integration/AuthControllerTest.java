package com.example.livebetting.integration;


import com.example.livebetting.data.model.request.SignupRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {

    private static Faker faker;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @SneakyThrows
    private ResultActions sendMockRequest(SignupRequestModel requestModel) {
        return mockMvc.perform(post("/api/auth/signup")
                .content(objectMapper.writeValueAsString(requestModel))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
    }

    @Test
    void should_sign_up() throws UnsupportedEncodingException {
        var requestModel = SignupRequestModel.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .password(faker.lorem().word())
                .userName(faker.lorem().word())
                .build();

        var response = sendMockRequest(requestModel).andReturn().getResponse();

        assertNotNull(response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
