package com.yuntian.poeticlife.constants;

/**
 * @Auther: yuntian
 * @Date: 2018/8/21 23:57
 * @Description:
 */
public class RedisKey {


    private static final String BACKEND_LOGIN_PREFIX = "backend_login_%s";

    public static final long BACKEND_LOGIN_EXPIRE = 3600 * 24; //1天



    public static String getBackendLoginkey(String id) {
        return String.format(BACKEND_LOGIN_PREFIX, id);
    }



}
