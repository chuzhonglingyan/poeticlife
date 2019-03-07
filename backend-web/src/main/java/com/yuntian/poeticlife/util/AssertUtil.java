package com.yuntian.poeticlife.util;


import com.yuntian.poeticlife.exception.BusinessException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;

/**
 * @Auther: yuntian
 * @Date: 2018/8/19 17:57
 * @Description: 断言工具类
 */
public class AssertUtil {


    public static void  isNotBlank(String text,String message){
        if (StringUtils.isBlank(text)){
            BusinessException.throwMessage(message);
        }
    }

    public static void  isNotEmpty(Collection collection, String message){
        if (CollectionUtils.isEmpty(collection)){
            BusinessException.throwMessage(message);
        }
    }

    public static void  isEmpty(Collection collection, String message){
        if (CollectionUtils.isNotEmpty(collection)){
            BusinessException.throwMessage(message);
        }
    }


    public static void  isNotNull(Object object,String message){
        if (object==null){
            BusinessException.throwMessage(message);
        }
    }

    public static void  isNotTrue(boolean flag,String message){
        if (!flag){
            BusinessException.throwMessage(message);
        }
    }


}
