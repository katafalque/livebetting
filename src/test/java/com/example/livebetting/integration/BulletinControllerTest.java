package com.example.livebetting.integration;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BulletinControllerTest {
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
    private ResultActions sendMockRequest() {
        return mockMvc.perform(get("/api/bulletin")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
    }

    @Test
    void should_save_event() throws UnsupportedEncodingException {

        var response = sendMockRequest().andReturn().getResponse();

        assertNotNull(response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
