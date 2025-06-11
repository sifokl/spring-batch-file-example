package com.batch.foobarquix.controller;


import com.batch.foobarquix.service.FooBarQuixTransformerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
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
                                Job  kataJob) {
        this.fooBarQuixTransformerService = fooBarQuixTransformerService;
        this.jobLauncher = jobLauncher;
        this.kataJob = kataJob;
    }

    @GetMapping("/transform/{number}")
    public ResponseEntity<String> transformSingleNumber(@PathVariable int number) {
        log.info("Requête reçue pour transformer le nombre : {}", number);

        if (number < 0 || number > 100) {
            log.warn("Nombre invalide : {} (hors intervalle autorisé 0-100)", number);
            return ResponseEntity.badRequest().body("Le nombre doit être entre 0 et 100.");
        }
        String result = fooBarQuixTransformerService.transform(number);
        log.info("Résultat de la transformation de {} : {}", number, result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/batch/run")
    public ResponseEntity<String> runBatchJob() {

        long timestamp = System.currentTimeMillis();
        log.info("Requête POST reçue pour lancer le batch. Timestamp : {}", timestamp);

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", timestamp) // permet d'éviter un job déjà exécuté
                .toJobParameters();
        try {
            log.debug("Préparation du lancement du job avec les paramètres : {}", params);
            jobLauncher.run(kataJob, params);
            log.info("Le batch a été lancé avec succès.");
            return ResponseEntity.ok("Batch lancé avec succès !");
        } catch (Exception e) {
            log.error("Échec lors du lancement du batch : {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Échec du lancement du batch : " + e.getMessage());
        }
    }


}

