package com.batch.foobarquix.batch;


import com.batch.foobarquix.util.Constants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class BatchConfig {

    @Bean
    public Job kataJob(JobRepository jobRepository, Step kataStep) {
        return new JobBuilder(Constants.NAME_JOB, jobRepository)
                .start(kataStep)
                .build();
    }

    @Bean
    public Step kataStep(JobRepository jobRepository,
                         PlatformTransactionManager transactionManager,
                         ItemReader<Integer> reader,
                         ItemProcessor<Integer, String> processor,
                         ItemWriter<String> writer) {

        return new StepBuilder(Constants.NAME_STEP, jobRepository)
                .<Integer, String>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}
