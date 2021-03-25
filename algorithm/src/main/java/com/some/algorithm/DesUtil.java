package com.some.algorithm;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 解密逻辑
 */
public class DesUtil {


    private static String key = "37ad9f8e";

    private final static String DES = "DES";

    private static String enCipher = "enCipher";

    private static String deCipher = "deCipher";

    private static SecretKeyFactory keyFactory;

    private static DESKeySpec dks;

    /**
     * 里面存储的Cipher对象实际完成解密操作
     */
    private static ConcurrentHashMap cipherMap = new ConcurrentHashMap();


    private static SecretKey getSecretKey() {
        try {
            if (dks == null) {
                dks = new DESKeySpec(key.getBytes());
            }

            if (keyFactory == null) {
                keyFactory = SecretKeyFactory.getInstance(DES);
            }

            return keyFactory.generateSecret(dks);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cipher getEnCipher() {
        try {
            if (cipherMap.get(enCipher) == null) {
                Cipher cipher = Cipher.getInstance(DES);
                SecureRandom sr = new SecureRandom();
                cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), sr);
                cipherMap.put(enCipher, cipher);

            }
            return (Cipher) cipherMap.get(enCipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cipher getDeCipher() {
        try {
            if (cipherMap.get(deCipher) == null) {
                Cipher cipher = Cipher.getInstance(DES);
                SecureRandom sr = new SecureRandom();
                cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), sr);
                cipherMap.put(deCipher, cipher);

            }
            return (Cipher) cipherMap.get(deCipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     * server返回的原始数据，需要先base64解密，再用DEs解密。
     * 是否解密由服务器的配置项控制
     * @param data
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data) throws IOException,
        Exception {
        if (data == null) {
            return "";
        }
        byte[] buf = new BASE64Decoder().decodeBuffer(data);
        byte[] bt = decrypt(buf);
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data) throws Exception {

        Cipher cipher = getEnCipher();

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data) throws Exception {
        return getDeCipher().doFinal(data);
    }
}