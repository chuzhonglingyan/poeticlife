package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.RoleMapper;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.service.RoleService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
@Service("roleService")
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;

}
