package com.batch.foobarquix.batch;


import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
    public class KataReaderConfig {

    @Value("${batch.input-file}")
    private String inputFile;

        @Bean
        public FlatFileItemReader<Integer> reader() {
            FlatFileItemReader<Integer> reader = new FlatFileItemReader<>();
            reader.setName("kataReader");
            reader.setResource(new FileSystemResource(inputFile));
            reader.setLinesToSkip(0);
            reader.setLineMapper((line, lineNumber) -> Integer.parseInt(line.trim()));

            return reader;
        }
}
