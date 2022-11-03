package com.miniproject.payment.app.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableScheduling
public class PayBillsInvokerController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job processJob;

    @Scheduled(fixedRate = 3000)//0.5 sec
    @PutMapping("/paybills")
    public void payBills() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(processJob, jobParameters);
        System.out.println("Job Done");

    }
}

