package com.yuntian.poeticlife.quartz;

import com.yuntian.poeticlife.model.entity.ScheduleJob;
import com.yuntian.poeticlife.model.entity.ScheduleJobLog;
import com.yuntian.poeticlife.service.ScheduleJobLogService;
import com.yuntian.poeticlife.service.ScheduleJobService;
import com.yuntian.poeticlife.util.SpringContextUtils;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import lombok.extern.slf4j.Slf4j;

import static com.yuntian.poeticlife.quartz.QuartJobTask.JOBID;

/**
 * @Auther: yuntian
 * @Date: 2019/3/17 0017 14:28
 * @Description:
 */
@Slf4j
public class TaskQuartzJobBean extends QuartzJobBean {

    //第一种方式
    private ExecutorService executor = Executors.newCachedThreadPool();


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        log.error("正在运行的定时器:group=" + jobDetail.getKey().getGroup() + ",name=" + jobDetail.getKey().getName() + ",id:" + jobDataMap.get(JOBID));

        SchedulerUtil schedulerUtil = (SchedulerUtil) SpringContextUtils.getBean("schedulerUtil");
        ScheduleJobService scheduleJobService = (ScheduleJobService) SpringContextUtils.getBean("scheduleJobService");
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        ScheduleJob scheduleJob = scheduleJobService.findById((Long) jobDataMap.get(JOBID));

        String ip = "localhost";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        log.error("执行ip:" + ip);

        long startTime = System.currentTimeMillis();

        ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
        scheduleJobLog.setIp(ip);
        scheduleJobLog.setJobId(scheduleJob.getId());
        scheduleJobLog.setBeanName(scheduleJob.getBeanName());
        scheduleJobLog.setMethodName(scheduleJob.getMethodName());
        scheduleJobLog.setcreateId(1L);
        scheduleJobLog.setupdateId(1L);
        try {

            ScheduleCallTask callTask = new ScheduleCallTask(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
            FutureTask<Integer> futureTask = new FutureTask<>(callTask);
            executor.submit(futureTask);
            futureTask.get();
            log.error("id:" + scheduleJob.getId() + ",beanName:" + scheduleJob.getBeanName() + ",methodName:" + scheduleJob.getMethodName() + "执行成功");
            scheduleJobLog.setStatus((byte) 1);

            schedulerUtil.updateTrigger(scheduleJob);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("id:" + scheduleJob.getId() + ",beanName:" + scheduleJob.getBeanName() + ",methodName:" + scheduleJob.getMethodName() + "执行失败");
            scheduleJobLog.setError(e.getMessage());
            scheduleJobLog.setStatus((byte) 0);
        } finally {
            scheduleJobLog.setTimes(System.currentTimeMillis() - startTime);
            scheduleJobLogService.save(scheduleJobLog);
        }
    }



}
