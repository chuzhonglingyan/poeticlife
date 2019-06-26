package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.BackendOperaterDTO;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.BackendOperaterService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2019/02/23.
 */
@RestController
@RequestMapping("/operater")
public class BackendOperaterController extends BaseController {

    @Resource
    private BackendOperaterService backendOperaterService;

    @PostMapping("/add")
    public Result add(BackendOperater dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        backendOperaterService.save(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        backendOperaterService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequiresPermissions(value = {"operater:update"})
    @PostMapping("/update")
    public Result update(BackendOperater dto) {
        dto.setUpdateId(getUserId());
        backendOperaterService.update(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/isEnable")
    public Result isEnable(BackendOperater dto) {
        dto.setUpdateId(getUserId());
        backendOperaterService.isEnable(dto);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/isStop")
    public Result isStop(BackendOperater dto) {
        dto.setUpdateId(getUserId());
        backendOperaterService.isStop(dto);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        BackendOperater backendOperater = backendOperaterService.findById(id);
        return ResultGenerator.genSuccessResult(backendOperater);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public PageInfoVo<BackendOperater> list(BackendOperaterDTO dto) {
        return backendOperaterService.queryListByPage(dto);
    }




    @GetMapping("/getMenuListByOperater")
    public Result getMenuListByOperater() {
        List<MenuTreeVO> list = backendOperaterService.getMenuTreeVOListByOperater(getUserId());
        return ResultGenerator.genSuccessResult(list);
    }

}
