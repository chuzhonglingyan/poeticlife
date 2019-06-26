package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.ScheduleJobDTO;
import com.yuntian.poeticlife.model.entity.ScheduleJob;
import com.yuntian.poeticlife.service.ScheduleJobService;
import com.yuntian.poeticlife.model.vo.PageInfoVo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* Created by CodeGenerator on 2019/03/17.
*/
@RestController
@RequestMapping("/scheduleJob")
public class ScheduleJobController extends BaseController{

    @Resource
    private ScheduleJobService scheduleJobService;

    @PostMapping("/add")
    public Result add(ScheduleJob dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        scheduleJobService.saveByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(ScheduleJob dto) {
        scheduleJobService.deleteByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(ScheduleJob dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        scheduleJobService.updateByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        ScheduleJob scheduleJob = scheduleJobService.findById(id);
        return ResultGenerator.genSuccessResult(scheduleJob);
    }


    @PostMapping("/resume")
    public Result resume(ScheduleJob dto) {
        scheduleJobService.resume(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/pause")
    public Result pause(ScheduleJob dto) {
        scheduleJobService.pause(dto);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/run")
    public Result run(ScheduleJob dto) {
        scheduleJobService.run(dto);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/list")
    public PageInfoVo<ScheduleJob> list(ScheduleJobDTO dto) {
        return scheduleJobService.queryListByPage(dto);
    }

}
