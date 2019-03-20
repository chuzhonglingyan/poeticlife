package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.RoleMenuDTO;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.vo.MenuVO;
import com.yuntian.poeticlife.service.RoleMenuService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2019/02/26.
 */
@RestController
@RequestMapping("/role/menu")
public class RoleMenuController extends BaseController {

    @Resource
    private RoleMenuService roleMenuService;


    @PostMapping("/saveMenuListByRoleId")
    public Result saveMenuIListByRoleId(@RequestBody RoleMenuDTO roleMenuDTO) {
        roleMenuDTO.setCreateBy(getUserId());
        roleMenuDTO.setUpdateBy(getUserId());
        roleMenuService.saveMenuListByRoleId(roleMenuDTO);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/getMenuListByRoleId")
    public  List<MenuVO>  getMenuListByRoleId(@RequestParam Long roleId) {
        List<MenuVO> menuList = roleMenuService.getMenuListByRoleId(roleId);
        return menuList;
    }


    @PostMapping("/deleteByRoleId")
    public Result deleteByRoleId(@RequestParam Long roleId) {
        roleMenuService.deleteByRoleId(roleId);
        return ResultGenerator.genSuccessResult();
    }
}
