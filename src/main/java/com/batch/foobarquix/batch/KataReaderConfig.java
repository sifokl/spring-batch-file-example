package com.batch.foobarquix.batch;


import com.batch.foobarquix.config.BatchProperties;
import com.batch.foobarquix.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
    public class KataReaderConfig {

    private static final Logger log = LoggerFactory.getLogger(KataReaderConfig.class);

    private final BatchProperties properties;

    public KataReaderConfig(BatchProperties properties) {
        this.properties = properties;
    }

        @Bean
        public FlatFileItemReader<Integer> reader() {

            log.info("Initialisation du FlatFileItemReader avec le fichier : {}", properties.getInputFile());
            FlatFileItemReader<Integer> reader = new FlatFileItemReader<>();
            reader.setName(Constants.NAME_READER);
            reader.setResource(new FileSystemResource(properties.getInputFile()));
            reader.setLinesToSkip(0);
            reader.setLineMapper((line, lineNumber) -> Integer.parseInt(line.trim()));
            log.info("Fin du traitement FlatFileItemReader avec le fichier : {}", properties.getInputFile());
            return reader;
        }
}
