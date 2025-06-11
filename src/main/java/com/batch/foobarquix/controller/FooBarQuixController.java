package com.batch.foobarquix.controller;


import com.batch.foobarquix.dto.ApiResponse;
import com.batch.foobarquix.service.FooBarQuixTransformerService;
import com.batch.foobarquix.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class FooBarQuixController {

    private final FooBarQuixTransformerService fooBarQuixTransformerService;
    private final JobLauncher jobLauncher;
    private final Job kataJob;

    private static final Logger log = LoggerFactory.getLogger(FooBarQuixController.class);

    public FooBarQuixController(FooBarQuixTransformerService fooBarQuixTransformerService,
                                JobLauncher jobLauncher,
                                Job kataJob) {
        this.fooBarQuixTransformerService = fooBarQuixTransformerService;
        this.jobLauncher = jobLauncher;
        this.kataJob = kataJob;
    }

    @GetMapping(Constants.ENDPOINT_PATH_TRANSFORM + "/{number}")
    public ResponseEntity<ApiResponse<String>>transformSingleNumber(@PathVariable int number) {
        log.info("Requête reçue pour transformer le nombre : {}", number);

        String result = fooBarQuixTransformerService.transform(number);
        log.info("Résultat de la transformation de {} : {}", number, result);
        return ResponseEntity.ok(new ApiResponse<>(true, Constants.MSG_TRANSFORMATION_SUCCESS, result));

    }

    @PostMapping(Constants.ENDPOINT_PATH_RUN)
    public ResponseEntity<ApiResponse<String>> runBatchJob() throws JobExecutionException {

        long timestamp = System.currentTimeMillis();
        log.info("Requête POST reçue pour lancer le batch. Timestamp : {}", timestamp);

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", timestamp) // permet d'éviter un job déjà exécuté
                .toJobParameters();

        log.debug("Préparation du lancement du job avec les paramètres : {}", params);
        jobLauncher.run(kataJob, params);
        log.info("Le batch a été lancé avec succès.");
        return ResponseEntity.ok(new ApiResponse<>(true,Constants.MSG_BATCH_LAUNCHED, null));

    }


}

