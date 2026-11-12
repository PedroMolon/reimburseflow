package com.pedromolon.reimburseflow.controller;

import com.pedromolon.reimburseflow.configuration.AbstractIntegrationTest;
import com.pedromolon.reimburseflow.domain.enums.ReimbursementCategory;
import com.pedromolon.reimburseflow.infrastructure.web.dto.LoginRequestDTO;
import com.pedromolon.reimburseflow.infrastructure.web.dto.ReimbursementRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReimbursementIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateReimbursementSuccessfullyWhenAuthenticated() throws Exception {

        LoginRequestDTO loginRequest = new LoginRequestDTO("employee@reimburseflow.com", "password123");

        MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();

        String token = objectMapper.readTree(responseBody).get("token").asString();

        ReimbursementRequestDTO request = new ReimbursementRequestDTO(
                "Client dinner",
                new BigDecimal("250.00"),
                ReimbursementCategory.FOOD
        );

        mockMvc.perform(post("/api/v1/reimbursement")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("Client dinner"))
                .andExpect(jsonPath("$.amount").value(250.00));
    }

}
