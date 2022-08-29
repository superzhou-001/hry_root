package hry.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 密码加密
 * <p>
 * TODO
 * </p>
 *
 * @author: Liu Shilei
 * @Date : 2015年9月25日 上午10:36:45
 */
public class PasswordHelper {

	private static final String DefalutSalt = "#$@(@123";


	public static String md5(String pwd,String salt)  {
		try {
			String md5 = DigestUtils.md5Hex(DefalutSalt + pwd + salt);
			return uuidview(md5,pwd,salt);
		}catch (Exception e){
		    e.printStackTrace();
		}finally {

		}
		return null;
	}

	private static String uuidview(String encryStr,String pwd,String salt){
		encryStr = DigestUtils.md5Hex(encryStr+salt+pwd);
		String uuid = DigestUtils.md5Hex(salt+encryStr+pwd);
		char[] arr = new char[64];

		for(int i = 0 ; i < encryStr.length(); i++){
			arr[i*2] = encryStr.charAt(i);
			arr[i*2+1] = uuid.charAt(i);
		}
		String str = new String(arr).toUpperCase();

		return str;
	}


	public static boolean validatePassword(String passWord, String passWord1, String salt) {
		String s = md5(passWord1, salt);
		return passWord.equals(s);
	}


	public static void main(String[] args) {
		for(int i = 0; i < 10; i++){
			System.out.println(PasswordHelper.md5("123456","e10adc3949ba59abbe56e057f20f883e"));
		}


	}

}
