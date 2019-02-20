package com.yuntian.basecommon.util.encrypt;

import org.apache.commons.codec.DecoderException;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * @Auther: yuntian
 * @Date: 2018/8/30 21:06
 * @Description:
 */
public interface IEncrypStrategy {


    /**
     * 功能描述:
     *
     * @param: 加密字符串成hexString
     * @return:
     * @auther: yuntian
     * @date: 2018/8/30 21:15
     */
    public String encryt(String str) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException;


    public String decrypt(String hexString) throws DecoderException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException;

}
