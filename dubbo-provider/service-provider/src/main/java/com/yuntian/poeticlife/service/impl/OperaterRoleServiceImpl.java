package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.OperaterRoleMapper;
import com.yuntian.poeticlife.model.entity.OperaterRole;
import com.yuntian.poeticlife.service.OperaterRoleService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/02/23.
 */
@Service("operaterRoleService")
public class OperaterRoleServiceImpl extends AbstractService<OperaterRole> implements OperaterRoleService {
    @Resource
    private OperaterRoleMapper operaterRoleMapper;

}
