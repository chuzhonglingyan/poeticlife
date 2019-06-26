package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.MenuDTO;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.MenuService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2019/02/26.
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Resource
    private MenuService menuService;

    @PostMapping("/add")
    public Result add(Menu dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        menuService.save(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(Menu dto) {
        menuService.deleteByDTO(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Menu dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        menuService.update(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        Menu menu = menuService.findById(id);
        return ResultGenerator.genSuccessResult(menu);
    }


    @PostMapping("/list")
    @ResponseBody
    public PageInfoVo<Menu> list(MenuDTO dTO) {
        return menuService.queryListByPage(dTO);
    }


    @PostMapping("/getAll")
    public List<Menu> getAll() {
        return menuService.findAll();
    }


    @PostMapping("/getEnableMenuList")
    public List<Menu> getEnableMenuList() {
        return menuService.findEnableMenus();
    }


}
