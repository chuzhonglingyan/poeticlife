package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.RoleDTO;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.RoleService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2019/02/26.
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @PostMapping("/add")
    public Result add(Role dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        roleService.save(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        roleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Role dto) {
        dto.setUpdateId(getUserId());
        roleService.update(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/isEnable")
    public Result isEnable(Role dto) {
        dto.setUpdateId(getUserId());
        roleService.isEnable(dto);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/isStop")
    public Result isStop(Role dto) {
        dto.setUpdateId(getUserId());
        roleService.isStop(dto);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        Role role = roleService.findById(id);
        return ResultGenerator.genSuccessResult(role);
    }

    @PostMapping("/list")
    public PageInfoVo<Role> list(RoleDTO roleDTO) {
        return roleService.queryRoleListByPage(roleDTO);
    }


}
