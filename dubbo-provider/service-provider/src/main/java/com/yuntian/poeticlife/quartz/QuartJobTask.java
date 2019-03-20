package com.yuntian.poeticlife.quartz;

import com.yuntian.poeticlife.model.entity.ScheduleJob;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

/**
 * @Auther: yuntian
 * @Date: 2019/3/17 0017 14:36
 * @Description:
 */
public class QuartJobTask {

    public static final String JOB_TAG = "job_";
    public static final String JOBID = "jobId";



    public static JobDetail createTaskDetail(ScheduleJob scheduleJob) {
        JobDetail jobDetail = JobBuilder.newJob(TaskQuartzJobBean.class)
                .withIdentity(JOB_TAG + scheduleJob.getId(),scheduleJob.getGroupName())
                .storeDurably()
                .build();
        jobDetail.getJobDataMap().put(JOBID, scheduleJob.getId());
        return jobDetail;
    }

    public static Trigger createTrigger(ScheduleJob scheduleJob, JobDetail jobDetail) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(JOB_TAG + scheduleJob.getId(), scheduleJob.getGroupName())
                .withSchedule(scheduleBuilder)
                .build();
    }


}
