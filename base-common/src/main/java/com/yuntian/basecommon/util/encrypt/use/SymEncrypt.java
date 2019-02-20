package com.yuntian.basecommon.util.encrypt.use;


import com.yuntian.basecommon.util.encrypt.Base64Utils;
import com.yuntian.basecommon.util.encrypt.IEncrypStrategy;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Auther: yuntian
 * @Date: 2018/8/30 21:17
 * @Description: 对称加密策略
 */
public class SymEncrypt implements IEncrypStrategy {


    public static final String AES = "AES";
    public static final String DES = "DES";
    public static final String DES3 = "DESede";

    //Cipher负责完成加密或解密工作
    private Cipher c;
    //SecretKey 负责保存对称密钥
    private SecretKey deskey;


    public SymEncrypt(String algorithm, String des) throws Exception {
        //生成Cipher对象,指定其支持的DES算法
        c = Cipher.getInstance(algorithm);
        deskey = (SecretKey) byteToKey(Base64Utils.decode(des),algorithm);
    }

    /**
     * 转换密钥
     * @param key 密钥的字节数组
     * @return
     */
    private  static Key byteToKey(byte[] key,String algorithm) {
        SecretKey secretKey = null;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            SecretKeySpec dks = new SecretKeySpec(key,algorithm);

            secretKey = factory.generateSecret(dks);
            // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
   //            secretKey = new SecretKeySpec(key, KEY_DES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return secretKey;
    }



    @Override
    public String encryt(String str) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return Hex.encodeHexString(encrytor(str));
    }

    @Override
    public String decrypt(String hexString) throws DecoderException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return new String(decryptor(Hex.decodeHex(hexString.toCharArray())));
    }

    /**
     * 对字符串加密
     *
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private byte[] encrytor(String str) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
        c.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] src = str.getBytes();
        // 加密，结果保存进cipherByte
        return c.doFinal(src);
    }

    /**
     * 对字符串解密
     *
     * @param buff
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private byte[] decryptor(byte[] buff) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
        c.init(Cipher.DECRYPT_MODE, deskey);
        return c.doFinal(buff);
    }


    /**
     * @param args
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static void main(String[] args) throws Exception {
        String msg = "哈哈";


//        deskey:a3281099e56f011b6fd9ab4f53162154
//        明文是:哈哈
//        加密后:29c773b9caa404767188ba5b31c9d55c
//        解密后:哈哈

//        System.out.println("-----------AES----------------");
//        SymEncrypt symEncryptAES = new SymEncrypt(AES, "a7SDfrdDKRBe5FaN2n3Gfg==");
//        System.out.println("明文是:" + msg);
//
//        String encrytMsgAES = symEncryptAES.encryt(msg);
//        System.out.println("加密后:" + encrytMsgAES);
//        System.out.println("解密后:" + symEncryptAES.decrypt(encrytMsgAES));

//
//        System.out.println("-----------DES----------------");
//
        SymEncrypt symEncryptDES = new SymEncrypt(DES,"FlJ8FgR6O/g=");
        System.out.println("明文是:" + msg);
        String encrytMsgDES=symEncryptDES.encryt(msg);
        System.out.println("加密后:" + encrytMsgDES);
        System.out.println("解密后:" + symEncryptDES.decrypt(encrytMsgDES));
//
//
        System.out.println("----------DES3----------------");
        SymEncrypt symEncryptDES3 = new SymEncrypt(DES3,"FlJ8FgR6O/g7f0A0KubWrnVYYnbxXSmd");
        System.out.println("明文是:" + msg);

        String encrytMsgDES3=symEncryptDES3.encryt(msg);
        System.out.println("加密后:" + encrytMsgDES3);
        System.out.println("解密后:" + symEncryptDES3.decrypt(encrytMsgDES3));

    }


}
