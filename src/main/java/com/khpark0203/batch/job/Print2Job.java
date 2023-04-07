package com.khpark0203.batch.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Component
public class Print2Job extends QuartzJobBean {
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Print2Job");
    }
}
