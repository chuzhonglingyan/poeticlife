package com.yuntian.poeticlife.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;

/**
 * @Auther: yuntian
 * @Date: 2019/2/20 0020 22:55
 * @Description:
 */
public abstract class BaseController {


    public Long getUserId(){
       return (Long) SecurityUtils.getSubject().getPrincipal();
    }

}
