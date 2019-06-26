package com.yuntian.poeticlife.dao;

import com.yuntian.poeticlife.core.Mapper;
import com.yuntian.poeticlife.model.entity.ScheduleJob;

public interface ScheduleJobMapper extends Mapper<ScheduleJob> {


    int insertJob(ScheduleJob dto);

}