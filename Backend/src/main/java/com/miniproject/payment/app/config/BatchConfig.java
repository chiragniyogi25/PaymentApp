package com.miniproject.payment.app.config;

import com.miniproject.payment.app.entity.RecurringPayments;
import com.miniproject.payment.app.repository.RecurringPaymentsRepository;
import com.miniproject.payment.app.repository.TransactionRepository;
import com.miniproject.payment.app.service.UserService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.miniproject.payment.app.listener.JobCompletionListener;
import com.miniproject.payment.app.step.Reader;
import com.miniproject.payment.app.step.Process;
import com.miniproject.payment.app.step.Writer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
//@EnableScheduling
public class BatchConfig {
    @Autowired
    RecurringPaymentsRepository recurringPaymentsRepository;
    @Autowired
    UserService userService;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//    @Scheduled(fixedRate = 3000)
    @Bean
    public Job processJob() {
        System.out.println("Job Process");
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer()).listener(listener())
                .flow(orderStep1()).end().build();
    }

    @Bean
    public Step orderStep1() {
        System.out.println("Stepss");
        return stepBuilderFactory.get("orderStep1").<RecurringPayments, RecurringPayments> chunk(1)  //returns 1 step
                .reader(new Reader(recurringPaymentsRepository))//reader
//                .reader(new Reader())//reader
                .processor(new Process(userService,recurringPaymentsRepository,transactionRepository))//process
                .writer(new Writer(recurringPaymentsRepository)).build();//writer
    }

    @Bean
    public JobExecutionListener listener() {
        System.out.println("listener");

        return new JobCompletionListener();
    }

}
