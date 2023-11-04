package com.example.demo;

import com.example.demo.model.Profession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.Person;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final JdbcTemplate jdbcTemplate;

    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            jdbcTemplate
                    .query("SELECT first_name, last_name FROM people", new DataClassRowMapper<>(Person.class))
                    .forEach(person -> log.info("Found <{{}}> in the database. ",person));

            jdbcTemplate
                    .query("SELECT first_name, last_name, profession FROM profession", new DataClassRowMapper<>(Profession.class))
                    .forEach(profession -> log.info("Found <{{}}> in the database. ",profession));

        }
    }
}