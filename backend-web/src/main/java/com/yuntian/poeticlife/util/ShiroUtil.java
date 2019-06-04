package com.yuntian.poeticlife.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @Auther: yuntian
 * @Date: 2019/2/25 0025 22:05
 * @Description:
 */
public class ShiroUtil {

    public static Long getUserId(){
        return (Long) SecurityUtils.getSubject().getPrincipal();
    }

    public static Subject getSubject(){
        return  SecurityUtils.getSubject();
    }
}
