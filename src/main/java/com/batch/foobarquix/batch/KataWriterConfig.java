package com.batch.foobarquix.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
public class KataWriterConfig {

    private static final Logger log = LoggerFactory.getLogger(KataWriterConfig.class);

    @Value("${batch.output-file}")
    private String outputFile;

    @Bean
    public FlatFileItemWriter<String> writer() {
        log.info("Initialisation du FlatFileItemWriter avec le fichier : {}", outputFile);

        FlatFileItemWriter<String> writer = new FlatFileItemWriter<>();
        writer.setName("kataWriter");
        writer.setResource(new FileSystemResource(outputFile));
        writer.setLineAggregator(new PassThroughLineAggregator<>());

        log.info("Fin du traitement FlatFileItemWriter avec le fichier : {}", outputFile);
        return writer;
    }
}
