package com.batch.foobarquix.controller;

import com.batch.foobarquix.service.FooBarQuixTransformerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FooBarQuixController.class)
class FooBarQuixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FooBarQuixTransformerService transformerService;

    @Test
    @DisplayName("GET /api/transform/3 should return 200 with FOOFOO")
    void testValidInput() throws Exception {
        when(transformerService.transform(3)).thenReturn("FOOFOO");

        mockMvc.perform(get("/api/transform/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("FOOFOO"));
    }

    @Test
    @DisplayName("GET /api/transform/1 should return 200 with 1")
    void testNoRuleMatch() throws Exception {
        when(transformerService.transform(1)).thenReturn("1");

        mockMvc.perform(get("/api/transform/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("GET /api/transform/105 should return 400 for input above max")
    void testUpperBoundViolation() throws Exception {
        mockMvc.perform(get("/api/transform/105"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Le nombre doit être entre 0 et 100."));
    }

    @Test
    @DisplayName("GET /api/transform/-1 should return 400 for input below 0")
    void testLowerBoundViolation() throws Exception {
        mockMvc.perform(get("/api/transform/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Le nombre doit être entre 0 et 100."));
    }

    @Test
    @DisplayName("GET /api/transform/abc should return 400 for invalid format")
    void testInvalidInput() throws Exception {
        mockMvc.perform(get("/api/transform/abc"))
                .andExpect(status().isBadRequest());
    }
}
