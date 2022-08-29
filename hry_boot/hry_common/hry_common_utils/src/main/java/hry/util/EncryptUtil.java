/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Zhang Xiaofang
 * @version:      V1.0 
 * @Date:        2016年5月16日 上午9:40:47
 */
package hry.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * <p> TODO</p>
 * @author:         Zhang Xiaofang 
 * @Date :          2016年5月16日 上午9:40:47 
 */
public class EncryptUtil {
	
	private static String encodingCharset = "UTF-8";
    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_256 = "SHA-256";
    private static final char[] CH_HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
        '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		
		/**
		 * 生成签名消息
		 * @param aValue  要签名的字符�?
		 * @param aKey  签名密钥
		 * @return
		 */
	public static String hmacSign(String aValue, String aKey) {
			byte k_ipad[] = new byte[64];
			byte k_opad[] = new byte[64];
			byte keyb[];
			byte value[];
			try {
				keyb = aKey.getBytes(encodingCharset);
				value = aValue.getBytes(encodingCharset);
			} catch (UnsupportedEncodingException e) {
				keyb = aKey.getBytes();
				value = aValue.getBytes();
			}

			Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
			Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
			for (int i = 0; i < keyb.length; i++) {
				k_ipad[i] = (byte) (keyb[i] ^ 0x36);
				k_opad[i] = (byte) (keyb[i] ^ 0x5c);
			}

			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {

				return null;
			}
			md.update(k_ipad);
			md.update(value);
			byte dg[] = md.digest();
			md.reset();
			md.update(k_opad);
			md.update(dg, 0, 16);
			dg = md.digest();
			
			return toHex(dg);
		}

	
	public static String toHex(byte input[]) {
			if (input == null)
				return null;
			StringBuffer output = new StringBuffer(input.length * 2);
			for (int i = 0; i < input.length; i++) {
				int current = input[i] & 0xff;
				if (current < 16)
					output.append("0");
				output.append(Integer.toString(current, 16));
			}

			return output.toString();
		}

		/**
		 * 
		 * @param args
		 * @param key
		 * @return
		 */
		public static String getHmac(String[] args, String key) {
			if (args == null || args.length == 0) {
				return (null);
			}
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < args.length; i++) {
				str.append(args[i]);
			}
			return (hmacSign(str.toString(), key));
		}


	
		
     	public static String encrypt(String decript,String algorithmsName) {
			try {
				MessageDigest digest = MessageDigest
						.getInstance(algorithmsName);
				digest.update(decript.getBytes());
				byte messageDigest[] = digest.digest();
				// Create Hex String
				StringBuffer hexString = new StringBuffer();
				// 字节数组转换为 十六进制 数
				for (int i = 0; i < messageDigest.length; i++) {
					String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
					if (shaHex.length() < 2) {
						hexString.append(0);
					}
					hexString.append(shaHex);
				}
				return hexString.toString();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return "";
		}
    	
		//生成公钥
	   private static String  getPubKey(String id){
		    Random random=new Random();
			String ran=id+String.valueOf(random.nextInt(4))+System.nanoTime();
			String pubKey=EncryptUtil.encrypt(ran, EncryptUtil.MD5);
			System.out.println(pubKey);
		   return pubKey;
	   }
		
		//生成私钥
	   public static String  getPriKey(String pubKey){
		  
		   char [] arr = pubKey.toCharArray();
		   StringBuffer sb = new StringBuffer();
		   for(int i=0; i<arr.length; i++){
		    sb.append(arr[i]^33);
		   }
		   String prvKey=EncryptUtil.encrypt(sb.toString(), EncryptUtil.MD5);
			System.out.println(prvKey);
		   return prvKey;
	   }
		
	   
	 //生成密钥对
	   public static Map<String,String>  getKeys(String id){
		   String pubKey=getPubKey(id);
		   String prvKey=getPriKey(pubKey);
		   Map<String,String> map=new HashMap<String,String>();
		    map.put("pubKey", pubKey);
	        map.put("prvKey", prvKey);
		   return map;
	   }
		
		
		 /**
	     * 加密字符串
	     * 
	     * @param sourceStr    需要加密目标字符串
	     * @param algorithmsName 算法名称(如:MD5,SHA-1,SHA-256)
	     * @return
	     */
	    public static String passAlgorithmsCiphering(String sourceStr,String algorithmsName){
	        String password = "";
	        MessageDigest md;
	        try {
	            md = MessageDigest.getInstance(algorithmsName);
	            // 使用指定byte[]更新摘要
	            md.update(sourceStr.getBytes());
	            // 完成计算，返回结果数组
	            byte[] b = md.digest();
	            password = byteArrayToHex(b);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return password;
	    }
	 
	    /**
	     * 将字节数组转为十六进制字符串
	     *
	     * @param bytes
	     * @return 返回16进制字符串
	     */
	    private static String byteArrayToHex(byte[] bytes) {
	        // 一个字节占8位，一个十六进制字符占4位；十六进制字符数组的长度为字节数组长度的两倍
	        char[] chars = new char[bytes.length * 2];
	        int index = 0;
	        for (byte b : bytes) {
	            // 取字节的高4位
	            chars[index++] = CH_HEX[b >>> 4 & 0xf];
	            // 取字节的低4位
	            chars[index++] = CH_HEX[b & 0xf];
	        }
	        return new String(chars);
	    }

	/**
	 * <p> TODO</p>
	 * @author:         Zhang Xiaofang
	 * @param:    @param args
	 * @return: void 
	 * @Date :          2016年5月16日 上午9:40:47   
	 * @throws:
	 */
	public static void main(String[] args) {
/*		Random random=new Random();
		String ran=String.valueOf(random.nextInt(4))+System.nanoTime();
		System.out.println(ran);
		String pubKey=EncryptUtil.encrypt(ran, EncryptUtil.SHA_1).substring(0, 32);
		String prvKey=EncryptUtil.encrypt("53547699e30f1a635a77767dadef8d9f".substring(3, 23), EncryptUtil.MD5).substring(0, 32);
	System.out.println(EncryptUtil.encrypt(ran, EncryptUtil.SHA_1));
	System.out.println(pubKey);
	System.out.println(prvKey);
    System.out.println(hmacSign("werrtyt","wre"));	*/
		//getKeys("33");
		

	}

}
