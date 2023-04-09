package com.khpark0203.batch.config;

import static org.quartz.JobBuilder.newJob;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
    public Trigger runJobTrigger(String scheduleExp) {
        // 크론 스케줄 사용
        return TriggerBuilder.newTrigger()
                            .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
                            .build();
    }
    
    public JobDetail runJobDetail(
        Class<? extends Job> job,
        Map<? extends String, ? extends Object> params,
        String key
    ) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        // 스케줄 생성
        // return newJob(job).usingJobData(jobDataMap).build();
        return newJob(job).usingJobData(jobDataMap).withIdentity(key).build();
    }
}