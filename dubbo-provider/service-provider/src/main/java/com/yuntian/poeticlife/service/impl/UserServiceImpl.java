package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.dao.UserMapper;
import com.yuntian.poeticlife.model.entity.User;
import com.yuntian.poeticlife.service.UserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/02/23.
 */
@Service("userService")
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;


    @Override
    public String getRole(String userName) {


        //查询用户

        return  "";
    }
}
