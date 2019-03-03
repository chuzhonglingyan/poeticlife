package com.yuntian.poeticlife.controller;

import com.yuntian.poeticlife.util.ShiroUtil;

/**
 * @Auther: yuntian
 * @Date: 2019/2/20 0020 22:55
 * @Description:
 */
public abstract class BaseController {


    protected Long getUserId(){
       return ShiroUtil.getUserId();
    }

}
