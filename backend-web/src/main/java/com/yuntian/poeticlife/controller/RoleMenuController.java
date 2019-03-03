package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.entity.RoleMenu;
import com.yuntian.poeticlife.service.RoleMenuService;
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
@RequestMapping("/role/menu")
public class RoleMenuController extends BaseController{

    @Resource
    private RoleMenuService roleMenuService;

    @PostMapping("/add")
    public Result add(RoleMenu roleMenu) {
        roleMenuService.save(roleMenu);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        roleMenuService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(RoleMenu roleMenu) {
        roleMenuService.update(roleMenu);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        RoleMenu roleMenu = roleMenuService.findById(id);
        return ResultGenerator.genSuccessResult(roleMenu);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<RoleMenu> list = roleMenuService.findAll();
        PageInfoVo<RoleMenu> pageInfo = new PageInfoVo<>(new PageInfo<>(list));
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
