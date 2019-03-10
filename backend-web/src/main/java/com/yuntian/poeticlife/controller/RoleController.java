package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.RoleDTO;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.service.RoleService;
import com.yuntian.poeticlife.model.vo.PageInfoVo;

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
public class RoleController extends BaseController{

    @Resource
    private RoleService roleService;

    @PostMapping("/add")
    public Result add(Role role) {
        role.setCreateBy(getUserId());
        role.setUpdateBy(getUserId());
        roleService.save(role);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        roleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Role role) {
        role.setCreateBy(getUserId());
        role.setUpdateBy(getUserId());
        roleService.update(role);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/isEnable")
    public Result isEnable(Role role) {
        role.setCreateBy(getUserId());
        role.setUpdateBy(getUserId());
        roleService.isEnable(role);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/isStop")
    public Result isStop(Role role) {
        role.setCreateBy(getUserId());
        role.setUpdateBy(getUserId());
        roleService.isStop(role);
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
