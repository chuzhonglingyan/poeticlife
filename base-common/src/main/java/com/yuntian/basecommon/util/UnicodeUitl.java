package com.yuntian.basecommon.util;

import java.io.UnsupportedEncodingException;

/**
 * @Auther: yuntian
 * @Date: 2018/8/30 21:34
 * @Description: 字符串在java中统一用unicode表示(即utf - 16 LE) ,
 * 乱码如何产生？ 本质上都是由于 字符串原本的编码格式 与 读取时解析用的编码格式不一致导致的。
 */
public class UnicodeUitl {

    public static String unicodeToUtf8(String s) throws UnsupportedEncodingException {
        return new String(s.getBytes("utf-8"), "utf-8");
    }

    public static String unicodeToGBK(String s) throws UnsupportedEncodingException {
        return new String(s.getBytes("GBK"), "GBK");
    }


}
