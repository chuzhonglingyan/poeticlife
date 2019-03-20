package com.yuntian.poeticlife.quartz;

import com.yuntian.poeticlife.enums.ScheduleStatus;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.entity.ScheduleJob;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import static com.yuntian.poeticlife.quartz.QuartJobTask.JOBID;

/**
 * @Auther: yuntian
 * @Date: 2019/3/17 0017 15:32
 * @Description:
 */
@Component
@Slf4j
public class SchedulerUtil {

    @Resource(name = "customScheduler")
    private Scheduler scheduler;

    public void start(ScheduleJob scheduleJob) throws SchedulerException {
        JobDetail jobDetail = QuartJobTask.createTaskDetail(scheduleJob);
        Trigger trigger = QuartJobTask.createTrigger(scheduleJob, jobDetail);
        start(scheduleJob,jobDetail, trigger);
    }

    private void start(ScheduleJob scheduleJob,JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        log.error("启动定时任务:group=" + jobDetail.getKey().getGroup() + ",name=" + jobDetail.getKey().getName() + ",id:" + jobDataMap.get(JOBID));
        scheduler.scheduleJob(jobDetail, trigger);
        // 启动
        if (!scheduler.isStarted()) {
            scheduler.start();
        }
        if (scheduleJob.getStatus()!= ScheduleStatus.NORMAL.getValue()){
            pause(scheduleJob);
        }
    }


    public void resume(ScheduleJob scheduleJob) throws SchedulerException {
        log.error("恢复定时任务:group=" + scheduleJob.getGroupName() + ",id=" + scheduleJob.getId());
        JobKey jobKey = new JobKey(QuartJobTask.JOB_TAG + scheduleJob.getId(), scheduleJob.getGroupName());
        List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
        if (triggersOfJob.size() > 0) {
            for (Trigger t : triggersOfJob) {
                if (t instanceof SimpleTrigger || t instanceof CronTrigger) {
                    scheduler.resumeJob(jobKey);
                }
            }
        }
    }


    public void updateTrigger(ScheduleJob scheduleJob) throws SchedulerException, ParseException {
        TriggerKey triggerKey = new TriggerKey(QuartJobTask.JOB_TAG + scheduleJob.getId(), scheduleJob.getGroupName());
        CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            BusinessException.throwMessage("cron表达式为空");
        }
        String oldTime = trigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(scheduleJob.getCronExpression())) {
            // 修改时间
            trigger.setCronExpression(scheduleJob.getCronExpression());
            // 方式一 ：修改一个任务的触发时间
            scheduler.rescheduleJob(triggerKey, trigger);
            log.error("更新定时任务的cron:group=" + scheduleJob.getGroupName() + ",id=" + scheduleJob.getId() + ",cronExpression:" + scheduleJob.getCronExpression());
            if (scheduleJob.getStatus()!=1){
                pause(scheduleJob);
            }
        }
    }


    public void removeJob(ScheduleJob scheduleJob) {
        log.error("移除定时任务:group=" + scheduleJob.getGroupName() + ",id=" + scheduleJob.getId());
        JobKey jobKey = new JobKey(QuartJobTask.JOB_TAG + scheduleJob.getId(), scheduleJob.getGroupName());
        TriggerKey triggerKey = new TriggerKey(QuartJobTask.JOB_TAG + scheduleJob.getId(), scheduleJob.getGroupName());
        try {
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void pause(ScheduleJob scheduleJob) throws SchedulerException {
        log.error("暂停定时任务:group=" + scheduleJob.getGroupName() + ",id=" + scheduleJob.getId());
        JobKey jobKey = new JobKey(QuartJobTask.JOB_TAG + scheduleJob.getId(), scheduleJob.getGroupName());
        scheduler.pauseJob(jobKey);
    }




    public void shutdown() throws SchedulerException {
        log.error("结束所有定时任务");
        scheduler.shutdown();
    }
}
