package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.OperaterRole;
import com.yuntian.poeticlife.service.OperaterRoleService;
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
* Created by CodeGenerator on 2019/02/26.
*/
@RestController
@RequestMapping("/operater/role")
public class OperaterRoleController extends BaseController{

    @Resource
    private OperaterRoleService operaterRoleService;

    @PostMapping("/add")
    public Result add(OperaterRole operaterRole) {
        operaterRoleService.save(operaterRole);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        operaterRoleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(OperaterRole operaterRole) {
        operaterRoleService.update(operaterRole);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        OperaterRole operaterRole = operaterRoleService.findById(id);
        return ResultGenerator.genSuccessResult(operaterRole);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<OperaterRole> list = operaterRoleService.findAll();
        PageInfoVo<OperaterRole> pageInfo = new PageInfoVo<>(new PageInfo<>(list));
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
