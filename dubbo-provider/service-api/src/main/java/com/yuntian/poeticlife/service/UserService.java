package com.yuntian.poeticlife.service;
import com.yuntian.poeticlife.model.entity.User;
import com.yuntian.poeticlife.core.Service;


/**
 * Created by CodeGenerator on 2019/02/23.
 */
public interface UserService extends Service<User> {

    String  getRole(String userName);
}
