package com.yuntian.poeticlife.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuntian.poeticlife.AssertUtil;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.ScheduleJobMapper;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.dto.ScheduleJobDTO;
import com.yuntian.poeticlife.model.entity.ScheduleJob;
import com.yuntian.poeticlife.model.entity.ScheduleJobLog;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.quartz.ScheduleCallTask;
import com.yuntian.poeticlife.quartz.SchedulerUtil;
import com.yuntian.poeticlife.service.ScheduleJobLogService;
import com.yuntian.poeticlife.service.ScheduleJobService;
import com.yuntian.poeticlife.util.SpringContextUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * Created by CodeGenerator on 2019/03/17.
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends AbstractService<ScheduleJob> implements ScheduleJobService {
    //第一种方式
    private ExecutorService executor = Executors.newCachedThreadPool();


    @Resource
    private ScheduleJobMapper scheduleJobMapper;

    @Resource
    private SchedulerUtil schedulerUtil;

    @Resource
    private ScheduleJobLogService scheduleJobLogService;

    @PostConstruct
    public void initConstruct() throws SchedulerException {
        List<ScheduleJob> scheduleJobList = findAll();
        if (CollectionUtils.isNotEmpty(scheduleJobList)) {
            for (ScheduleJob scheduleJob : scheduleJobList) {
                schedulerUtil.start(scheduleJob);
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ScheduleJob model) {
        AssertUtil.isNotBlank(model.getGroupName(), "组名不能为空");
        AssertUtil.isNotNull(model.getBeanName(), "实体类不能为空");
        AssertUtil.isNotBlank(model.getMethodName(), "方法不能为空");
        AssertUtil.isNotBlank(model.getCronExpression(), "时间表达式不能为空");
        AssertUtil.isNotNull(model.getCreateBy(), "创建人不能为空");
        AssertUtil.isNotNull(model.getUpdateBy(), "更新人不能为空");
        if (!CronExpression.isValidExpression(model.getCronExpression())) {
            BusinessException.throwMessage("cron表达式不正确");
        }
        try {
            insertJob(model);
            model.setStatus((byte) 0);
            schedulerUtil.start(model);
        } catch (DuplicateKeyException e) {
            BusinessException.throwMessage("实体类和方法唯一约束，不能重复");
        } catch (SchedulerException e) {
            e.printStackTrace();
            BusinessException.throwMessage("定时器初始化失败");
        }
    }


    @Override
    public List<ScheduleJob> findAll() {
        Condition condition = new Condition(ScheduleJob.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        return findByCondition(condition);
    }

    @Override
    public ScheduleJob findById(Long id) {
        Condition condition = new Condition(ScheduleJob.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        List<ScheduleJob> scheduleJobList = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(scheduleJobList)) {
            return scheduleJobList.get(0);
        }
        return null;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(ScheduleJob model) {
        AssertUtil.isNotNull(model.getId(), "更新id不能为空");
        AssertUtil.isNotNull(model.getGroupName(), "组名不能为空");
        AssertUtil.isNotNull(model.getBeanName(), "实体类不能为空");
        AssertUtil.isNotBlank(model.getMethodName(), "方法不能为空");
        AssertUtil.isNotBlank(model.getCronExpression(), "时间表达式不能为空");
        AssertUtil.isNotNull(model.getUpdateBy(), "更新人不能为空");

        check(model.getBeanName(),model.getMethodName(),model.getParams());
        super.update(model);
        try {
            schedulerUtil.updateTrigger(model);
        } catch (SchedulerException | ParseException e) {
            e.printStackTrace();
            BusinessException.throwMessage("更新定时任务信息发生异常");
        }
    }


    @Override
    public void deleteById(Long id) {
        ScheduleJob scheduleJob = findById(id);
        if (scheduleJob == null) {
            BusinessException.throwMessage("该定时器不存在，请刷新页面");
        }
        scheduleJob.setIsDelete((byte) 1);
        super.update(scheduleJob);
    }


    @Override
    public int insertJob(ScheduleJob scheduleJob) {
        return scheduleJobMapper.insertJob(scheduleJob);
    }

    @Override
    public PageInfoVo<ScheduleJob> queryListByPage(ScheduleJobDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Condition condition = new Condition(ScheduleJob.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<ScheduleJob> roleList = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(roleList));
    }

    @Override
    public void run(Long id) {
        ScheduleJob scheduleJob = findById(id);
        if (scheduleJob == null) {
            BusinessException.throwMessage("该定时器不存在，请刷新页面");
        }
        check(scheduleJob.getBeanName(),scheduleJob.getMethodName(),scheduleJob.getParams());

        String ip = "localhost";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        long startTime = System.currentTimeMillis();

        ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
        scheduleJobLog.setIp(ip);
        scheduleJobLog.setJobId(scheduleJob.getId());
        scheduleJobLog.setBeanName(scheduleJob.getBeanName());
        scheduleJobLog.setMethodName(scheduleJob.getMethodName());
        scheduleJobLog.setCreateBy(1L);
        scheduleJobLog.setUpdateBy(1L);

        boolean isException=false;
        String msg="";
        try {

            ScheduleCallTask callTask = new ScheduleCallTask(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
            FutureTask<Integer> futureTask = new FutureTask<>(callTask);
            executor.submit(futureTask);
            futureTask.get();
            scheduleJobLog.setStatus((byte) 1);
            schedulerUtil.updateTrigger(scheduleJob);
        } catch (Exception e) {
            e.printStackTrace();
            scheduleJobLog.setError(e.getMessage());
            scheduleJobLog.setStatus((byte) 0);
            isException=true;
            msg=e.getMessage();
        } finally {
            scheduleJobLog.setTimes(System.currentTimeMillis() - startTime);
            scheduleJobLogService.save(scheduleJobLog);
        }
        if (isException){
            BusinessException.throwMessage(msg);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resume(Long id) {
        ScheduleJob scheduleJob = findById(id);
        if (scheduleJob == null) {
            BusinessException.throwMessage("该定时器不存在，请刷新页面");
        }
        check(scheduleJob.getBeanName(),scheduleJob.getMethodName(),scheduleJob.getParams());
        scheduleJob.setStatus((byte) 1);
        try {
            schedulerUtil.resume(scheduleJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
            BusinessException.throwMessage("定时任务恢复异常");
        }
        update(scheduleJob);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pause(Long id) {
        ScheduleJob scheduleJob = findById(id);
        if (scheduleJob == null) {
            BusinessException.throwMessage("该定时器不存在，请刷新页面");
        }
        check(scheduleJob.getBeanName(),scheduleJob.getMethodName(),scheduleJob.getParams());
        scheduleJob.setStatus((byte) 0);
        try {
            schedulerUtil.pause(scheduleJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
            BusinessException.throwMessage("定时任务暂停异常");
        }
        super.update(scheduleJob);
    }


    private void  check(String beanName, String methodName, String params){
        Object target=null;
        try {
            target = SpringContextUtils.getBean(beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(target==null){
            BusinessException.throwMessage("没有该任务类");
        }
        if(StringUtils.isNotBlank(params)){
            try {
                 target.getClass().getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                BusinessException.throwMessage("没有该方法");
            }
        }else{
            try {
                 target.getClass().getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                BusinessException.throwMessage("没有该方法");
            }
        }
    }

}
