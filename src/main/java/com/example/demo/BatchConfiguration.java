package com.example.demo;


import javax.sql.DataSource;

import com.example.demo.chunk.PersonItemProcessor;
import com.example.demo.chunk.PersonItemReader;
import com.example.demo.chunk.PersonItemWriter;
import com.example.demo.model.Person;
import com.example.demo.model.PersonInfo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfiguration {

    @Bean
    public FlatFileItemReader<Person> personItemReader() {
        PersonItemReader personReader = new PersonItemReader();
        return personReader.reader();
    }

    @Bean
    public PersonItemProcessor personItemProcessor() {
        return new PersonItemProcessor();
    }

    @Bean
    public PersonItemWriter personItemWriter(DataSource dataSource) {
        return new PersonItemWriter();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository,Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      FlatFileItemReader<Person> reader, PersonItemProcessor processor, PersonItemWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<Person, PersonInfo> chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
