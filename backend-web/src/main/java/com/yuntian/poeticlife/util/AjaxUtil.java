package com.yuntian.poeticlife.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: yuntian
 * @Date: 2019/2/24 0024 20:28
 * @Description:
 */
public class AjaxUtil {


    /**
     * 判断请求是否为ajax请求
     */
    public static boolean isAjax(HttpServletRequest request){
        return(request.getHeader("X-Requested-With")!=null
                &&"XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

}
