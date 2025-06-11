package com.batch.foobarquix.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
    public class KataReaderConfig {

    private static final Logger log = LoggerFactory.getLogger(KataReaderConfig.class);

    @Value("${batch.input-file}")
    private String inputFile;

        @Bean
        public FlatFileItemReader<Integer> reader() {
            log.info("Initialisation du FlatFileItemReader avec le fichier : {}", inputFile);
            FlatFileItemReader<Integer> reader = new FlatFileItemReader<>();
            reader.setName("kataReader");
            reader.setResource(new FileSystemResource(inputFile));
            reader.setLinesToSkip(0);
            reader.setLineMapper((line, lineNumber) -> Integer.parseInt(line.trim()));
            log.info("Fin du traitement FlatFileItemReader avec le fichier : {}", inputFile);
            return reader;
        }
}
