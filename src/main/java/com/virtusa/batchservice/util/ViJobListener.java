package com.virtusa.batchservice.util;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.util.Date;

public class ViJobListener implements JobExecutionListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void beforeJob(JobExecution je) {
        logger.info("START_DATE_AND_TIME-->" + new Date());
        logger.info("BATCH_STATUS-->" + je.getStatus());
    }

    @Override
    public void afterJob(JobExecution je) {
        logger.info("END_DATE_AND_TIME-->" + new Date());
        logger.info("BATCH_STATUS-->" + je.getStatus());
    }
}
