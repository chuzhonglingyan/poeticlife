package com.yuntian.poeticlife.model.vo;


import com.yuntian.poeticlife.model.entity.UserAccount;

/**
 * @Auther: yuntian
 * @Date: 2018/8/22 21:40
 * @Description:  前端用户信息
 */
public class UserAccountVo extends UserAccount {


    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
