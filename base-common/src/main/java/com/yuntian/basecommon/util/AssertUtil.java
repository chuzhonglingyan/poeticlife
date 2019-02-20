package com.yuntian.basecommon.util;


import com.yuntian.basecommon.exception.BusinessException;

import org.apache.commons.lang.StringUtils;

/**
 * @Auther: yuntian
 * @Date: 2018/8/19 17:57
 * @Description: 断言工具类
 */
public class AssertUtil {


    public static void  isNotBlank(String text,String msg){
        if (StringUtils.isBlank(text)){
            throw new BusinessException(msg);
        }
    }


    public static void  isNotNull(Object object,String msg){
        if (object==null){
            throw new BusinessException(msg);
        }
    }

    public static void  isNotTrue(boolean flag,String msg){
        if (!flag){
            throw new BusinessException(msg);
        }
    }


}
