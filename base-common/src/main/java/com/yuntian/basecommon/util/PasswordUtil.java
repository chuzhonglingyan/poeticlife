package com.yuntian.basecommon.util;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;

/**
 * @Auther: yuntian
 * @Date: 2018/8/29 23:02
 * @Description:
 */
public class PasswordUtil {

    /**
     * 生成含有盐的字符串
     */
    public static String md5HexWithSalt(String text, String salt) {
        if (StringUtils.isNotBlank(salt)) {
            text = md5Hex(text + salt);
            char[] cs = new char[48];
            for (int i = 0; i < 48; i += 3) {
                cs[i] = text.charAt(i / 3 * 2);
                char c = salt.charAt(i / 3);
                cs[i + 1] = c;
                cs[i + 2] = text.charAt(i / 3 * 2 + 1);
            }
            return new String(cs);
        }
        return md5Hex(text);
    }


    /**
     * 生成含有随机盐的字符串
     */
    public static String md5HexWithSalt(String text) {
        String salt = generateSalt();
        text = md5Hex(text + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = text.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = text.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }


    /**
     * 功能描述:
     *
     * @param: 生成随机数的盐
     * @return:
     * @auther: yuntian
     * @date: 2018/8/30 19:08
     */
    public static String generateSalt() {
        String salt = UUIDUitl.generate16ShortUuid();
        return salt;
    }

    /**
     * 校验参数是否正确
     */
    public static boolean verify(String text, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(text + salt).equals(new String(cs1));
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return Hex.encodeHexString(bs);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String password = md5HexWithSalt("123456");
        System.out.println(password);
        System.out.println(verify("123456", password));

    }
}
