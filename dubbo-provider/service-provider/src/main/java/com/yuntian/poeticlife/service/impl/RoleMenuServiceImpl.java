package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.RoleMenuMapper;
import com.yuntian.poeticlife.model.entity.RoleMenu;
import com.yuntian.poeticlife.service.RoleMenuService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends AbstractService<RoleMenu> implements RoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

}
