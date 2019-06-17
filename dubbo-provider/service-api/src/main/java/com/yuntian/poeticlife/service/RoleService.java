package com.yuntian.poeticlife.service;

import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.dto.RoleDTO;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.vo.PageInfoVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
public interface RoleService extends Service<RoleDTO,Role> {

    List<Role> findEnableRoleList();

    Role findRoleByName(String roleName);


    PageInfoVo<Role> queryRoleListByPage(RoleDTO roleDTO);

    void isEnable(Role role);

    void isStop(Role role);


}
