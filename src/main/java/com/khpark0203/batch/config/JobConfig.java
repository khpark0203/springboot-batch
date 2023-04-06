package com.khpark0203.batch.config;

import static org.quartz.JobBuilder.newJob;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.khpark0203.batch.job.PrintJob;

import jakarta.annotation.PostConstruct;

@Configuration
public class JobConfig {
    
    @Autowired
    private Scheduler scheduler; // 쿼츠 스케줄 객체
    
    @PostConstruct
    public void run() {
        JobDetail detail = runJobDetail(PrintJob.class, new HashMap<>());
        
        try {
            // 크론형식 지정 후 스케줄 시작
            scheduler.scheduleJob(detail, runJobTrigger("* * * * * ?"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    public Trigger runJobTrigger(String scheduleExp) {
        // 크론 스케줄 사용
        return TriggerBuilder.newTrigger()
                            .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
                            .build();
    }
    
    public JobDetail runJobDetail(Class<? extends Job> job, Map<? extends String, ? extends Object> params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        // 스케줄 생성
        return newJob(job).usingJobData(jobDataMap).build();
    }
}