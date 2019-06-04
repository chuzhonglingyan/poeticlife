package com.yuntian.poeticlife.config.shrio.exception;

/**
 * @Auther: yuntian
 * @Date: 2019/6/3 0003 21:07
 * @Description:
 */
public class PrincipalIdNullException extends RuntimeException {
    private static final String MESSAGE = "Principal Id shouldn't be null!";

    public PrincipalIdNullException(Class clazz, String idMethodName) {
        super(clazz + " id field: " + idMethodName + ", value is null\n" + MESSAGE);
    }
}
