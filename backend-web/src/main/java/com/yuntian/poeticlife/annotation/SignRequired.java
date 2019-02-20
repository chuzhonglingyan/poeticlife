package com.yuntian.poeticlife.annotation;

/**
 * @Auther: yuntian
 * @Date: 2018/8/22 00:14
 * @Description:
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要验证签名的Controller的方法上使用此注解
 */
@Target({ElementType.METHOD})// 可用在方法名上
@Retention(RetentionPolicy.RUNTIME)// 运行时有效
public @interface SignRequired {

    boolean value() default false;
}