package com.yuntian.basecommon.util;

import org.apache.commons.lang.StringUtils;

import java.util.Random;
import java.util.UUID;

/**
 * @Auther: yuntian
 * @Date: 2018/8/22 22:22
 * @Description:
 */
public class UUIDUitl {

    public static String getUUIDName(String name) {
        // 唯一 字符串  fsd-sfsdf-sfsd-sdfsd
        String uuid = UUID.randomUUID().toString().replace("-", "");

        if (StringUtils.isNotBlank(name)) {
            return uuid + name;
        }
        return uuid;
    }

    public String getUUIDByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
            String str = uuid.toString();
            String uuidStr = str.replace("-", "");
            return uuidStr;
        }
        return getUUIDName("");
    }


    public static String getUUIDName() {
        return getUUIDName("");
    }

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    public static String generate8ShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    public static String generate16ShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 16; i++) {
            String str = uuid.substring(i * 2, i * 2 + 2);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    public static String getOrderIdByUUId() {
        int first = new Random(10).nextInt(8) + 1;
        System.out.println(first);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return first + String.format("%015d", hashCodeV);
    }
    public static void main(String[] args) {
        String orderingID= getOrderIdByUUId();
        System.out.println(orderingID);
    }


}
