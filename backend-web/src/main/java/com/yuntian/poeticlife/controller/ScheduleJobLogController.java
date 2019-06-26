package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.ScheduleJobLogDTO;
import com.yuntian.poeticlife.model.entity.ScheduleJobLog;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.ScheduleJobLogService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2019/03/17.
 */
@RestController
@RequestMapping("/scheduleJobLog")
public class ScheduleJobLogController extends BaseController {

    @Resource
    private ScheduleJobLogService scheduleJobLogService;

    @PostMapping("/delete")
    public Result delete(ScheduleJobLog dto) {
        scheduleJobLogService.deleteByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/list")
    public PageInfoVo<ScheduleJobLog> list(ScheduleJobLogDTO dto) {
        return scheduleJobLogService.queryListByPage(dto);
    }

}
