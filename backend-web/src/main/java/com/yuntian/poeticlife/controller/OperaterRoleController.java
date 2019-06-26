package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.core.Result;
import com.yuntian.poeticlife.core.ResultGenerator;
import com.yuntian.poeticlife.model.dto.OperaterRoleDTO;
import com.yuntian.poeticlife.model.entity.OperaterRole;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.model.vo.RoleVO;
import com.yuntian.poeticlife.service.OperaterRoleService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/operater/role")
public class OperaterRoleController extends BaseController {

    @Resource
    private OperaterRoleService operaterRoleService;


    @PostMapping("/saveRoleListByOperaterId")
    public Result saveRoleListByOperaterId(@RequestBody OperaterRoleDTO dto) {
        dto.setCreateId(getUserId());
        dto.setUpdateId(getUserId());
        operaterRoleService.saveRoleListByOperaterId(dto);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        operaterRoleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/getRoleListByOperaterId")
    public List<RoleVO> getRoleListByOperaterId(@RequestParam Long operaterId) {
        return operaterRoleService.getRoleListByOperaterId(operaterId);
    }


    @PostMapping("/list")
    @ResponseBody
    public PageInfoVo<OperaterRole> list(OperaterRoleDTO dTO) {
        return operaterRoleService.queryListByPage(dTO);
    }


}
