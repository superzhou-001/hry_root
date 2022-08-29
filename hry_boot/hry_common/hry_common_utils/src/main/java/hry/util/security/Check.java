package hry.util.security;

import hry.util.StringUtil;
import hry.util.md5.Md5Encrypt;

/**
 * 系统之间调用安全校验码
 * <p> TODO</p>
 * @author:         shangxl
 * @Date :          2017年11月13日 下午3:40:04
 */
public class Check {
	
	/**
	 * 根据传递的参数生成校验码
	 * 
	 * <p> 
	 * 规则：先数组排序，然后md5加密
	 * 
	 * </p>
	 * @author:         shangxl
	 * @param:    @param s
	 * @param:    @return
	 * @return: String 
	 * @Date :          2017年11月13日 下午3:46:18   
	 * @throws:
	 */
	public static String authCode(String [] s){
		String auth_code=null;
		if(StringUtil.containEmpty(s)){
			return auth_code;
		}
		auth_code = StringUtil.stringSort(s, "_");
		auth_code = Md5Encrypt.md5(auth_code);
		return auth_code;
	}
}
