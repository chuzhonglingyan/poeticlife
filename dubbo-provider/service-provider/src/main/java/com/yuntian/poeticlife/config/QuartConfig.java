package com.yuntian.poeticlife.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: yuntian
 * @Date: 2019/3/17 0017 15:33
 * @Description:
 */
@Configuration
public class QuartConfig {

    @Bean("customScheduler")
    Scheduler createScheduler() throws SchedulerException {
        SchedulerFactory factory = new StdSchedulerFactory();
        return factory.getScheduler();
    }


}
