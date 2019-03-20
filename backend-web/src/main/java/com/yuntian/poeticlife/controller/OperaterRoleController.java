package com.yuntian.poeticlife.controller;
import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.OperaterRoleDTO;
import com.yuntian.poeticlife.model.dto.RoleMenuDTO;
import com.yuntian.poeticlife.model.entity.OperaterRole;
import com.yuntian.poeticlife.model.vo.MenuVO;
import com.yuntian.poeticlife.model.vo.RoleVO;
import com.yuntian.poeticlife.service.OperaterRoleService;
import com.github.pagehelper.PageHelper;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


    @PostMapping("/saveRoleListByOperaterId")
    public Result saveRoleListByOperaterId(@RequestBody OperaterRoleDTO dto) {
        dto.setCreateBy(getUserId());
        dto.setUpdateBy(getUserId());
        operaterRoleService.saveRoleListByOperaterId(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        operaterRoleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }



    @PostMapping("/getRoleListByOperaterId")
    public  List<RoleVO>  getRoleListByOperaterId(@RequestParam Long operaterId) {
        List<RoleVO> roleVOList = operaterRoleService.getRoleListByOperaterId(operaterId);
        return roleVOList;
    }


    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<OperaterRole> list = operaterRoleService.findAll();
        PageInfoVo<OperaterRole> pageInfo = new PageInfoVo<>(new PageInfo<>(list));
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
