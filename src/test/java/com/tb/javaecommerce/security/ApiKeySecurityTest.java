package com.tb.javaecommerce.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiKeySecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldRejectAdminRequestWithoutApiKey() throws Exception {
        mockMvc.perform(post("/api/admin/test"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowAdminRequestWithValidApiKey() throws Exception {
        mockMvc.perform(
                post("/api/admin/test")
                        .header("X-COSMO-API-KEY", "COSMO-SECRET-KEY-123")
        ).andExpect(status().isNotFound());
    }
}
