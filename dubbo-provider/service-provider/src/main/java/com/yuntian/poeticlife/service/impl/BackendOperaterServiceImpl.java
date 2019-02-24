package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.BackendOperaterMapper;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.service.BackendOperaterService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/02/23.
 */
@Service("backendOperaterService")
public class BackendOperaterServiceImpl extends AbstractService<BackendOperater> implements BackendOperaterService {
    @Resource
    private BackendOperaterMapper backendOperaterMapper;

    @Override
    public String getRole(Long userId) {

        return "";
    }
}
