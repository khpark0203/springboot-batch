package com.khpark0203.batch.service;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khpark0203.batch.config.JobConfig;
import com.khpark0203.batch.job.PrintJob;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Service
public class JobService {

    @Autowired
    private Scheduler scheduler; // 쿼츠 스케줄 객체

    @Autowired
    private JobConfig jobConfig; // 쿼츠 스케줄 객체

    @Getter
    public class Jobs {

        private Class<? extends Job> clazz;
        private Map<? extends String, ? extends Object> map;
        private String exp;

        public Jobs(Class<? extends Job> clazz, Map<? extends String, ? extends Object> map, String exp) {
            this.clazz = clazz;
            this.map = map;
            this.exp = exp;
        }
        
    }

    private Jobs[] jobs = {
        new Jobs(PrintJob.class, new HashMap<>(), "* * * * * ?")
    };
    
    @PostConstruct
    public void run() {
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        for (Jobs j: jobs) {
            try {
                scheduler.scheduleJob(
                    jobConfig.runJobDetail(j.getClazz(), j.getMap()),
                    jobConfig.runJobTrigger(j.getExp())
                );
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }   
}