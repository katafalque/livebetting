package com.example.livebetting.integration;

import com.example.livebetting.data.model.request.AddEventRequestModel;
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
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventControllerTest {
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
    private ResultActions sendMockRequest(AddEventRequestModel requestModel) {
        return mockMvc.perform(post("/api/event/add")
                .content(objectMapper.writeValueAsString(requestModel))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON));
    }

    @Test
    void should_save_event() throws UnsupportedEncodingException {
        var requestModel = AddEventRequestModel.builder()
                .league(faker.esports().league())
                .homeTeam(faker.esports().team())
                .awayTeam(faker.esports().team())
                .startTime(OffsetDateTime.now())
                .homeTeamOdd(faker.random().nextDouble())
                .awayTeamOdd(faker.random().nextDouble())
                .drawOdd(faker.random().nextDouble())
                .build();

        var response = sendMockRequest(requestModel).andReturn().getResponse();

        assertNotNull(response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
