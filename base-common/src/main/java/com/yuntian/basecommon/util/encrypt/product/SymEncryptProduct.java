package com.yuntian.basecommon.util.encrypt.product;


import com.yuntian.basecommon.util.ProjectPath;
import com.yuntian.basecommon.util.encrypt.Base64Utils;
import com.yuntian.basecommon.util.encrypt.IEncrypStrategy;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Auther: yuntian
 * @Date: 2018/8/30 21:17
 * @Description: 对称加密策略
 */
public class SymEncryptProduct implements IEncrypStrategy {


    public static final String AES = "AES";
    public static final String DES = "DES";
    public static final String DES3 = "DESede";


    //KeyGenerator 提供对称密钥生成器的功能，支持各种算法
    private KeyGenerator keygen;
    //SecretKey 负责保存对称密钥
    private SecretKey deskey;
    //Cipher负责完成加密或解密工作
    private Cipher c;
    //该字节数组负责保存加密的结果
    private byte[] cipherByte;

    private String ALGORITHM = "";
    private String path = "";
    private String textPath = "";
    private static  final  String seed="944610721@qq.com";

    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    private SecretKey geneKey() throws Exception {
        //获取一个密钥生成器实例
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom random = new SecureRandom();
        random.setSeed(seed.getBytes());//设置加密用的种子，密钥
        keyGenerator.init(random);
        SecretKey secretKey = keyGenerator.generateKey();
        //把上面的密钥存起来
        Path keyPath = Paths.get(path);
        Files.write(keyPath, secretKey.getEncoded());

        Path keyTextPath = Paths.get(textPath);
        Files.write(keyTextPath, Base64Utils.encode(secretKey.getEncoded()).getBytes());
        return secretKey;
    }

    /**
     * 读取存储的密钥
     *
     * @return
     * @throws Exception
     */
    private SecretKey readKey() throws Exception {
        //读取存起来的密钥
        byte[] keyBytes = Files.readAllBytes(Paths.get(path));
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        return keySpec;
    }


    private SymEncryptProduct(String algorithm) throws Exception {
        ALGORITHM = algorithm;
        path = getSecurePath() + ALGORITHM + ".key";
        textPath = getSecurePath() + ALGORITHM + "_key.text";
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        //实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
        //生成密钥
        deskey = geneKey();
        //生成Cipher对象,指定其支持的DES算法
        c = Cipher.getInstance(algorithm);
    }


    private String getSecurePath() {
        return ProjectPath.getProjectPath() + File.separator + "secure" + File.separator;
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
        cipherByte = c.doFinal(src);
        return cipherByte;
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
        cipherByte = c.doFinal(buff);
        return cipherByte;
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

        System.out.println("-----------AES----------------");
        SymEncryptProduct symEncryptAES = new SymEncryptProduct(AES);
        System.out.println("deskey:" + Base64Utils.encode(symEncryptAES.deskey.getEncoded()));
        System.out.println("明文是:" + msg);


        String encrytMsgAES = symEncryptAES.encryt(msg);
        System.out.println("加密后:" + encrytMsgAES);
        System.out.println("解密后:" + symEncryptAES.decrypt(encrytMsgAES));

        System.out.println("-----------DES----------------");

        SymEncryptProduct symEncryptDES = new SymEncryptProduct(DES);
        System.out.println("明文是:" + msg);
        String encrytMsgDES = symEncryptDES.encryt(msg);
        System.out.println("加密后:" + encrytMsgDES);
        System.out.println("解密后:" + symEncryptDES.decrypt(encrytMsgDES));


        System.out.println("----------DES3----------------");
        SymEncryptProduct symEncryptDES3 = new SymEncryptProduct(DES3);
        System.out.println("明文是:" + msg);

        String encrytMsgDES3 = symEncryptDES3.encryt(msg);
        System.out.println("加密后:" + encrytMsgDES3);
        System.out.println("解密后:" + symEncryptDES3.decrypt(encrytMsgDES3));

        System.out.println(ProjectPath.getProjectPath());


    }


}
