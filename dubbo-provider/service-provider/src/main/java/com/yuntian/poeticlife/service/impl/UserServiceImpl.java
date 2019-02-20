package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.UserMapper;
import com.yuntian.poeticlife.model.entity.User;
import com.yuntian.poeticlife.service.UserService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/02/19.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

}
