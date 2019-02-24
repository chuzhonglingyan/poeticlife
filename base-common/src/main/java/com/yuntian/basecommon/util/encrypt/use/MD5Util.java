package com.yuntian.basecommon.util.encrypt.use;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: yuntian
 * @Date: 2018/8/23 21:04
 * @Description:
 */
public class MD5Util {

    static MessageDigest messageDigest = null;

    /**
     * 判断新字符串和字符串是否正确  返回true 和 false
     *
     * @param newStr
     * @param oldMD5Str
     * @return
     */
    public final static boolean checkMD5(String newStr, String oldMD5Str) {
        String temp = encoderByMd5(newStr);
        return (temp != null && temp.equals(oldMD5Str)) ? true : false;
    }

    /**
     * 对给定的字符串进行加密
     *
     * @param source
     * @return 加密后的16进制的字符串
     */
    public final static String encoderByMd5(String source) {
        if (StringUtils.isBlank(source)) {
            return "";
        }
        String slat = source.substring(0, 1) + source.subSequence(source.length() - 1, source.length());
        slat = md5(slat);
        return md5(source + slat);
    }

    private static String md5(String source) {
        try {

            byte[] strTemp = source.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            return Hex.encodeHexString(md);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String encodeByMD5(String str) {
        try {
            if (messageDigest == null)
                messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (messageDigest == null)
            return "";
        byte[] byteArray = messageDigest.digest();
        return new String(new Hex().encode(byteArray));
    }

    public static void main(String[] args) {
        System.out.println(encodeByMD5("123456"));
        System.out.println(encoderByMd5("123456"));
        System.out.println(checkMD5("123456", "b0bfee8fe33bc6a0234ddf40ccffc004"));
    }

}
