package com.yuntian.poeticlife.service;

import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.dto.ScheduleJobDTO;
import com.yuntian.poeticlife.model.entity.ScheduleJob;
import com.yuntian.poeticlife.model.vo.PageInfoVo;


/**
 * Created by CodeGenerator on 2019/03/17.
 */
public interface ScheduleJobService extends Service<ScheduleJobDTO,ScheduleJob> {

    int insertJob(ScheduleJob dto);


    public void run(ScheduleJob dto) ;



    public void resume(ScheduleJob dto) ;


    public void pause(ScheduleJob dto) ;


}
