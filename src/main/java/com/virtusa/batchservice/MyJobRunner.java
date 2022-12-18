package com.virtusa.batchservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyJobRunner implements CommandLineRunner {

//    @Autowired
    private JobLauncher launcher;

    @Autowired
    public void setLauncher(JobLauncher launcher) {
        this.launcher = launcher;
    }

    @Autowired
    private Job jobA;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {
        JobParameters jobParameter = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        launcher.run(jobA, jobParameter);
        logger.info("JOB_EXECUTION_DONE!");
    }
}
