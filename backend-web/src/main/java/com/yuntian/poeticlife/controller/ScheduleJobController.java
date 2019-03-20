package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.RoleDTO;
import com.yuntian.poeticlife.model.dto.ScheduleJobDTO;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.entity.ScheduleJob;
import com.yuntian.poeticlife.service.ScheduleJobService;
import com.github.pagehelper.PageHelper;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/03/17.
*/
@RestController
@RequestMapping("/scheduleJob")
public class ScheduleJobController extends BaseController{

    @Resource
    private ScheduleJobService scheduleJobService;

    @PostMapping("/add")
    public Result add(ScheduleJob scheduleJob) {
        scheduleJob.setCreateBy(getUserId());
        scheduleJob.setUpdateBy(getUserId());
        scheduleJobService.save(scheduleJob);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        scheduleJobService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(ScheduleJob scheduleJob) {
        scheduleJob.setCreateBy(getUserId());
        scheduleJob.setUpdateBy(getUserId());
        scheduleJobService.update(scheduleJob);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        ScheduleJob scheduleJob = scheduleJobService.findById(id);
        return ResultGenerator.genSuccessResult(scheduleJob);
    }


    @PostMapping("/resume")
    public Result resume(@RequestParam Long id) {
        scheduleJobService.resume(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/pause")
    public Result pause(@RequestParam Long id) {
        scheduleJobService.pause(id);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/run")
    public Result run(@RequestParam Long id) {
        scheduleJobService.run(id);
        return ResultGenerator.genSuccessResult();
    }



    @PostMapping("/list")
    public PageInfoVo<ScheduleJob> list(ScheduleJobDTO dto) {
        return scheduleJobService.queryListByPage(dto);
    }

}
