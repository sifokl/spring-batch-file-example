package com.batch.foobarquix.controller;

import com.batch.foobarquix.exception.GlobalExceptionHandler;
import com.batch.foobarquix.exception.InvalidNumberRangeException;
import com.batch.foobarquix.service.FooBarQuixTransformerService;
import com.batch.foobarquix.util.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FooBarQuixController.class)
@Import(GlobalExceptionHandler.class)
class FooBarQuixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobLauncher jobLauncher;

    @MockBean
    private Job kataJob;

    @MockBean
    private FooBarQuixTransformerService transformerService;

    @Test
    @DisplayName("GET /api/transform/3 should return 200 with FOOFOO")
    void testValidInput() throws Exception {
        when(transformerService.transform(3)).thenReturn("FOOFOO");

        mockMvc.perform(get("/api/transform/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(Constants.MSG_TRANSFORMATION_SUCCESS))
                .andExpect(jsonPath("$.data").value("FOOFOO"));
    }

    @Test
    @DisplayName("GET /api/transform/1 should return 200 with 1")
    void testNoRuleMatch() throws Exception {
        when(transformerService.transform(1)).thenReturn("1");

        mockMvc.perform(get("/api/transform/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(Constants.MSG_TRANSFORMATION_SUCCESS))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    @DisplayName("GET /api/transform/105 should return 400 for input above max")
    void testUpperBoundViolation() throws Exception {
        when(transformerService.transform(105))
                .thenThrow(new InvalidNumberRangeException(105));

        mockMvc.perform(get("/api/transform/105"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Le nombre 105 n'est pas valide. Il doit être entre 0 et 100."))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("GET /api/transform/-1 should return 400 for input below 0")
    void testLowerBoundViolation() throws Exception {

        when(transformerService.transform(-1))
                .thenThrow(new InvalidNumberRangeException(-1));

        mockMvc.perform(get("/api/transform/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Le nombre -1 n'est pas valide. Il doit être entre 0 et 100."))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("GET /api/transform/abc should return 400 for invalid format")
    void testInvalidInput() throws Exception {
        mockMvc.perform(get("/api/transform/abc"))
                .andExpect(status().isBadRequest());
    }


    /**
     * Tests pour l'endpoint # 2
     */

    @Test
    @DisplayName("POST /api/batch/run should return 200 on success")
    void testRunBatchJobSuccess() throws Exception {
        JobExecution mockExecution = new JobExecution(1L);
        when(jobLauncher.run(eq(kataJob), any(JobParameters.class))).thenReturn(mockExecution);

        mockMvc.perform(post("/api/batch/run"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(Constants.MSG_BATCH_LAUNCHED))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("POST /api/batch/run should return 500 on failure")
    void testRunBatchJobFailure() throws Exception {
        when(jobLauncher.run(eq(kataJob), any(JobParameters.class)))
                .thenThrow(new RuntimeException("Test failure"));


        mockMvc.perform(post("/api/batch/run"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Erreur interne : Test failure"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
