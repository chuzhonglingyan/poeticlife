package com.yuntian.poeticlife.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuntian.poeticlife.util.AssertUtil;
import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.ScheduleJobLogMapper;
import com.yuntian.poeticlife.model.dto.ScheduleJobDTO;
import com.yuntian.poeticlife.model.entity.ScheduleJobLog;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.ScheduleJobLogService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;


/**
 * Created by CodeGenerator on 2019/03/17.
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends AbstractService<ScheduleJobLog> implements ScheduleJobLogService {

    @Resource
    private ScheduleJobLogMapper scheduleJobLogMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(ScheduleJobLog model) {
        AssertUtil.isNotNull(model.getIp(), "ip不能为空");
        AssertUtil.isNotNull(model.getJobId(), "任务id不能为空");
        AssertUtil.isNotNull(model.getBeanName(), "实体类不能为空");
        AssertUtil.isNotBlank(model.getMethodName(), "方法不能为空");
        super.save(model);
    }


    @Override
    public PageInfoVo<ScheduleJobLog> queryListByPage(ScheduleJobDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getJobId(), "JobId参数不能为空");
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Condition condition = new Condition(ScheduleJobLog.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("jobId", dto.getJobId());
        List<ScheduleJobLog> roleList = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(roleList));
    }


}
