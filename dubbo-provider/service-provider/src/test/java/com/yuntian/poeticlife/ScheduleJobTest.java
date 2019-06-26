package com.yuntian.poeticlife;

import com.yuntian.poeticlife.model.entity.ScheduleJob;
import com.yuntian.poeticlife.quartz.SchedulerUtil;
import com.yuntian.poeticlife.service.ScheduleJobService;

import org.junit.Test;
import org.quartz.SchedulerException;

import java.text.ParseException;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: yuntian
 * @Date: 2019/3/17 0017 14:47
 * @Description:
 */
@Slf4j
public class ScheduleJobTest extends BaseTest {

    @Resource
    ScheduleJobService scheduleJobService;

    @Test
    public void saveScheduleJob() {
        ScheduleJob model = new ScheduleJob();
        model.setBeanName("testTask2");
        model.setMethodName("test");
        model.setGroupName("test");
        model.setCronExpression("0 0/1 * * * ?");
        model.setCreateId(1L);
        model.setUpdateId(1L);
        scheduleJobService.save(model);

    }


    @Resource
    private SchedulerUtil schedulerUtil;


    @Test
    public void findById() throws SchedulerException, InterruptedException, ParseException {
        ScheduleJob scheduleJob = scheduleJobService.findById(1L);
        schedulerUtil.start(scheduleJob);
        Thread.sleep(10000);


        schedulerUtil.pause(scheduleJob);

        Thread.sleep(3000);

        schedulerUtil.resume(scheduleJob);

        Thread.sleep(10000);

        scheduleJob.setCronExpression("*/10 * * * * ?");
        scheduleJobService.update(scheduleJob);

        ScheduleJob scheduleJob2 = scheduleJobService.findById(1L);
        schedulerUtil.updateTrigger(scheduleJob2);

        Thread.sleep(30000);

        schedulerUtil.shutdown();
    }
}
