package com.khpark0203.batch.service;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.khpark0203.batch.config.JobConfig;
import com.khpark0203.batch.job.PrintJob;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {

    private final Scheduler scheduler; // 쿼츠 스케줄 객체
    private final JobConfig jobConfig; // 쿼츠 스케줄 객체

    @Getter
    public class Jobs {

        private Class<? extends Job> clazz;
        private Map<? extends String, ? extends Object> map;
        private String exp;
        private String key;
        private Boolean run;

        public Jobs(Class<? extends Job> clazz, Map<? extends String, ? extends Object> map, String exp, String key, Boolean run) {
            this.clazz = clazz;
            this.map = map;
            this.exp = exp;
            this.key = key;
            this.run = run;
        }
        
    }

    private Jobs[] jobs = {
        new Jobs(PrintJob.class, new HashMap<>(), "* * * * * ?", "PrintJob", true)
    };
    
    @PostConstruct
    public void run() {
        for (Jobs j: jobs) {
            try {
                if (j.run) {
                    scheduler.scheduleJob(
                        jobConfig.runJobDetail(j.getClazz(), j.getMap(), j.getKey()),
                        jobConfig.runJobTrigger(j.getExp())
                    );
                } else {
                    scheduler.deleteJob(new JobKey(j.key, "DEFAULT"));
                }
            } catch (ObjectAlreadyExistsException e) {
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }   
}