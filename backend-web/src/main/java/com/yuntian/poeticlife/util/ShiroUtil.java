package com.yuntian.poeticlife.util;

import org.apache.shiro.SecurityUtils;

/**
 * @Auther: yuntian
 * @Date: 2019/2/25 0025 22:05
 * @Description:
 */
public class ShiroUtil {

    public static Long getUserId(){
        return (Long) SecurityUtils.getSubject().getPrincipal();
    }


}
