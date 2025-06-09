package com.batch.foobarquix.controller;


import com.batch.foobarquix.service.FooBarQuixTransformerService;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.batch.core.*;

@RestController
@RequestMapping("/api")
public class FooBarQuixController {

    private final FooBarQuixTransformerService fooBarQuixTransformerService;
    private final JobLauncher jobLauncher;
    private final Job kataJob;


    public FooBarQuixController(FooBarQuixTransformerService fooBarQuixTransformerService,
                                JobLauncher jobLauncher,
                                Job  kataJob) {
        this.fooBarQuixTransformerService = fooBarQuixTransformerService;
        this.jobLauncher = jobLauncher;
        this.kataJob = kataJob;
    }

    @GetMapping("/transform/{number}")
    public ResponseEntity<String> transformSingleNumber(@PathVariable int number) {
        if (number < 0 || number > 100) {
            return ResponseEntity.badRequest().body("Le nombre doit être entre 0 et 100.");
        }
        return ResponseEntity.ok(fooBarQuixTransformerService.transform(number));
    }

    @PostMapping("/batch/run")
    public ResponseEntity<String> runBatchJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis()) // éviter duplicate job
                    .toJobParameters();

            jobLauncher.run(kataJob, params);
            return ResponseEntity.ok("Batch lancé avec succès !");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Échec du lancement du batch : " + e.getMessage());
        }
    }


}

