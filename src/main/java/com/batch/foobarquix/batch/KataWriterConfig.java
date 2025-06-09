package com.batch.foobarquix.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
public class KataWriterConfig {
    @Value("${batch.output-file}")
    private String outputFile;

    @Bean
    public FlatFileItemWriter<String> writer() {
        FlatFileItemWriter<String> writer = new FlatFileItemWriter<>();
        writer.setName("kataWriter");
        writer.setResource(new FileSystemResource(outputFile));
        writer.setLineAggregator(new PassThroughLineAggregator<>());
        return writer;
    }
}
