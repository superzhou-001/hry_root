
package hry.util.rsa;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RSAUtil {
    public static final String KEY_ALGORITHM="RSA";
    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     * */
    private static final int KEY_SIZE=1024;
    //公钥
    private static final String PUBLIC_KEY="RSAPublicKey";
    //私钥
    private static final String PRIVATE_KEY="RSAPrivateKey";
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static Map<String, Object> getKeys() throws NoSuchAlgorithmException{
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair=keyPairGenerator.generateKeyPair();
        //甲方公钥
        RSAPublicKey publicKey=(RSAPublicKey) keyPair.getPublic();
        log.info("系数Modulus："+publicKey.getModulus()+",加密指数PublicExponent："+publicKey.getPublicExponent());
        //甲方私钥
        RSAPrivateKey privateKey=(RSAPrivateKey) keyPair.getPrivate();
        log.info("系数Modulus："+privateKey.getModulus()+",解密指数PrivateExponent："+privateKey.getPrivateExponent());
        //将密钥存储在map中
        Map<String,Object> keyMap=new HashMap<String,Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
    /**
     * <p>Description:获取公钥<／p>
     * @author yanyq
     * @date 2018年4月25日下午5:20:06
     * @version 1.0
     * @param keyMap 密钥map
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap){
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        String publicKey = Base64.encodeBase64String(key.getEncoded());
        log.info("公钥为publicKey：" + publicKey);
        return publicKey;
    }
    /**
     * <p>Description:获取私钥<／p>
     * @author yanyq
     * @date 2018年4月25日下午5:18:34
     * @version 1.0
     * @param keyMap 密钥map
     * @return
     * @throws Exception
     */

    public static String getPrivateKey(Map<String, Object> keyMap){
        //获得map中的私钥对象 转为key对象
        Key key=(Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        String privateKey = Base64.encodeBase64String(key.getEncoded());
        log.info("私钥为：" + privateKey);
        return privateKey;
    }

    /**
     * <p>Description:公钥加密<／p>
     * @author yanyq
     * @date 2018年4月25日下午5:23:55
     * @version 1.0
     * @param data       待加密数据
     * @param publicKey 公钥密钥
     * @return  String 加密后字符串
     * @throws Exception
     */
    public static String encryptByPublicKey(String data,String publicKey) throws Exception{
        log.info("公钥加密前数据：" + data + ",公钥为：" + publicKey);
        byte[] key = Base64.decodeBase64(publicKey);
        byte[] datab = data.getBytes("utf-8");
        //实例化密钥工厂
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥转换
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据加密
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        //return Base64.encodeBase64String(cipher.doFinal(datab));
        //分段加密
        int inputLen = datab.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(datab, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(datab, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        String encodeData = Base64.encodeBase64String(encryptedData);
        log.info("公钥加密后数据为：" + encodeData);
        return encodeData;
    }
    /**
     * <p>Description:私钥解密<／p>
     * @author yanyq
     * @date 2018年4月25日下午5:31:32
     * @version 1.0
     * @param data       待解密数据
     * @param privateKey 私钥密钥
     * @return            解密后字符
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data,String privateKey) throws Exception{
        log.info("私钥解密前数据：" + data + ",私钥为：privateKey：" + privateKey);
        byte[] key = Base64.decodeBase64(privateKey);
        byte[] dataB = Base64.decodeBase64(data);
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey _privateKey=keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, _privateKey);
        //return new String(cipher.doFinal(dataB));
        //分段解密
        int inputLen = dataB.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataB, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataB, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        String decrData = new String(decryptedData,"utf-8");
        log.info("私钥解密后数据为：" + decrData);
        return decrData;
    }

    /**
     * <p>Description: 私钥加密<／p>
     * @author yanyq
     * @date 2018年4月25日下午5:43:23
     * @version 1.0
     * @param data 待加密数据
     * @return  String  加密数据
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data ,String privateKey) throws Exception{
        //log.info("私钥加密前数据：" + data + ",私钥为privateKey：" + privateKey);
        byte[] key = Base64.decodeBase64(privateKey);
        byte[] datab = data.getBytes("utf-8");
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey_ = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey_);
        // return Base64.encodeBase64String(cipher.doFinal(datab));
        //分段加密
        int inputLen = datab.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(datab, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(datab, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        String endodeData = Base64.encodeBase64String(encryptedData);
        //log.info("私钥加密后：" + endodeData);
        return endodeData;
    }
    /**
     * <p>Description:公钥解密<／p>
     * @author yanyq
     * @date 2018年4月25日下午5:47:30
     * @version 1.0
     * @param data       解密数据
     * @param publicKey  公钥密钥
     * @return String 解密后的数据
     * @throws Exception
     */
    public static String decryptByPublicKey(String data,String publicKey) throws Exception{
        //log.info("公钥解密前数据：" + data + ",公钥为：publicKey：" + publicKey);
        byte[] key = Base64.decodeBase64(publicKey);
        byte[] dataB = Base64.decodeBase64(data);
        //实例化密钥工厂
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        //return new String(cipher.doFinal(dataB));
        //分段解密
        int inputLen = dataB.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataB, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataB, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        String decrypData = new String(decryptedData,"utf-8");
        //log.info("公钥解密后数据：" + decrypData);
        return decrypData;
    }
    /**
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(String encodedData, String privateKey) throws Exception {
        //byte[] keyBytes = Base64Utils.decode(privateKey);
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        byte[] data = Base64.decodeBase64(encodedData);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        //return Base64Utils.encode(signature.sign());
        return Base64.encodeBase64String(signature.sign());
    }
    public static boolean verify(String encodedData, String publicKey, String sign)
            throws Exception {
        //byte[] keyBytes = Base64Utils.decode(publicKey);
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        byte[] data = Base64.decodeBase64(encodedData);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        //return signature.verify(Base64Utils.decode(sign));
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * java端私钥解密
     */
    public static String decryptDataOnJava(String data, String PRIVATEKEY) {
        String temp = "";
        try {
            byte[] rs = Base64Utils.decodeFromString(data);
            temp = new String(RSAUtil.decryptByPrivateKey(rs, PRIVATEKEY),"UTF-8"); //以utf-8的方式生成字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
    /** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> keyMap = getKeys();
        String publicKey = getPublicKey(keyMap);
        String privateKey = getPrivateKey(keyMap);
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);
        String data = "%7B%22companyCode%22%3A%22a153f7f541284dd1bf1f7b59054abc27%22%2C%22msg%22%3A%22%E6%B5%8B%E8%AF%95%22%2C%22phone%22%3A%2215927121823%22%7D";
        System.out.println("-------------------公钥加密--------私钥解密------------");
        String jiami = encryptByPublicKey(data, publicKey);
        System.out.println("加密：" + jiami);
        String jiemi = decryptByPrivateKey(jiami, privateKey);
        System.out.println(jiemi);

        System.out.println("------------------私钥加密-------公钥解密-------------");
        jiami = encryptByPrivateKey(data, privateKey);
        System.out.println("加密：" +jiami);
        jiemi = decryptByPublicKey(jiami, publicKey);
        System.out.println(jiemi);

        System.err.println("私钥签名——公钥验证签名");
        String sign = sign(jiami, privateKey);
        System.err.println("签名:" + sign);
        boolean status = verify(jiami, publicKey, sign);
        System.err.println("验证结果:" + status);
    }
}
