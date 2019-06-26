package com.yuntian.poeticlife.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.ScheduleJobMapper;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.model.dto.ScheduleJobDTO;
import com.yuntian.poeticlife.model.entity.Article;
import com.yuntian.poeticlife.model.entity.ScheduleJob;
import com.yuntian.poeticlife.model.entity.ScheduleJobLog;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.quartz.ScheduleCallTask;
import com.yuntian.poeticlife.quartz.SchedulerUtil;
import com.yuntian.poeticlife.service.ScheduleJobLogService;
import com.yuntian.poeticlife.service.ScheduleJobService;
import com.yuntian.poeticlife.util.AssertUtil;
import com.yuntian.poeticlife.util.SpringContextUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
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
public class ScheduleJobServiceImpl extends AbstractService<ScheduleJobDTO, ScheduleJob> implements ScheduleJobService {
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


    @Override
    public void saveByDTO(ScheduleJob dto) {
        AssertUtil.isNotBlank(dto.getGroupName(), "组名不能为空");
        AssertUtil.isNotNull(dto.getBeanName(), "实体类不能为空");
        AssertUtil.isNotBlank(dto.getMethodName(), "方法不能为空");
        AssertUtil.isNotBlank(dto.getCronExpression(), "时间表达式不能为空");
        AssertUtil.isNotNull(dto.getcreateId(), "创建人不能为空");
        AssertUtil.isNotNull(dto.getupdateId(), "更新人不能为空");
        if (!CronExpression.isValidExpression(dto.getCronExpression())) {
            BusinessException.throwMessage("cron表达式不正确");
        }
        try {
            int id = insertJob(dto);
            dto.setStatus((byte) 0);
            schedulerUtil.start(dto);
        } catch (DuplicateKeyException e) {
            BusinessException.throwMessage("实体类和方法唯一约束，不能重复");
        } catch (SchedulerException e) {
            e.printStackTrace();
            BusinessException.throwMessage("定时器初始化失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByDTO(ScheduleJob dto) {
        ScheduleJob scheduleJob = findById(dto.getId());
        if (scheduleJob == null) {
            BusinessException.throwMessage("该定时器不存在，请刷新页面");
        }
        scheduleJob.setIsDelete((byte) 1);
        super.update(scheduleJob);
    }

    @Override
    public void updateByDTO(ScheduleJob dto) {
        AssertUtil.isNotNull(dto.getId(), "更新id不能为空");
        AssertUtil.isNotNull(dto.getGroupName(), "组名不能为空");
        AssertUtil.isNotNull(dto.getBeanName(), "实体类不能为空");
        AssertUtil.isNotBlank(dto.getMethodName(), "方法不能为空");
        AssertUtil.isNotBlank(dto.getCronExpression(), "时间表达式不能为空");
        AssertUtil.isNotNull(dto.getupdateId(), "更新人不能为空");

        check(dto.getBeanName(), dto.getMethodName(), dto.getParams());
        try {
            schedulerUtil.updateTrigger(dto);
        } catch (SchedulerException | ParseException e) {
            e.printStackTrace();
            BusinessException.throwMessage("更新定时任务信息发生异常");
        }
        super.update(dto);
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

    @Override
    public int insertJob(ScheduleJob dto) {
        return scheduleJobMapper.insertJob(dto);
    }


    @Override
    public PageInfoVo<ScheduleJob> queryListByPage(ScheduleJobDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Condition condition = new Condition(Article.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<ScheduleJob> list = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(list));
    }


    @Override
    public void run(ScheduleJob dto) {
        ScheduleJob scheduleJob = findById(dto.getId());
        if (scheduleJob == null) {
            BusinessException.throwMessage("该定时器不存在，请刷新页面");
        }
        check(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());

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
        scheduleJobLog.setCreateId(1L);
        scheduleJobLog.setUpdateId(1L);

        boolean isException = false;
        String msg = "";
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
            isException = true;
            msg = e.getMessage();
        } finally {
            scheduleJobLog.setTimes(System.currentTimeMillis() - startTime);
            scheduleJobLogService.save(scheduleJobLog);
        }
        if (isException) {
            BusinessException.throwMessage(msg);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resume(ScheduleJob dto) {
        ScheduleJob vo = findById(dto.getId());
        if (vo == null) {
            BusinessException.throwMessage("该定时器不存在，请刷新页面");
        }
        check(vo.getBeanName(), vo.getMethodName(), vo.getParams());
        vo.setStatus((byte) 1);
        dto.setStatus((byte) 1);
        try {
            schedulerUtil.resume(vo);
        } catch (SchedulerException e) {
            e.printStackTrace();
            BusinessException.throwMessage("定时任务恢复异常");
        }
        update(dto);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pause(ScheduleJob dto) {
        ScheduleJob vo = findById(dto.getId());
        if (vo == null) {
            BusinessException.throwMessage("该定时器不存在，请刷新页面");
        }
        check(vo.getBeanName(), vo.getMethodName(), vo.getParams());
        vo.setStatus((byte) 0);
        dto.setStatus((byte) 0);
        try {
            schedulerUtil.pause(vo);
        } catch (SchedulerException e) {
            e.printStackTrace();
            BusinessException.throwMessage("定时任务暂停异常");
        }
        super.update(dto);
    }


    private void check(String beanName, String methodName, String params) {
        Object target = null;
        try {
            target = SpringContextUtils.getBean(beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (target == null) {
            BusinessException.throwMessage("没有该任务类");
        }
        if (StringUtils.isNotBlank(params)) {
            try {
                target.getClass().getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                BusinessException.throwMessage("没有该方法");
            }
        } else {
            try {
                target.getClass().getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                BusinessException.throwMessage("没有该方法");
            }
        }
    }

}
