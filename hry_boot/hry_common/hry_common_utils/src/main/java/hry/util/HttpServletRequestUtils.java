/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0 
 * @Date:        2016年3月14日 上午11:48:37
 */
package hry.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * <p>mybatis通用查询组件</p>
 * @author:         Liu Shilei 
 * @Date :          2016年3月14日 上午11:48:37 
 */
public class HttpServletRequestUtils{

	public static Map<String,String> getParams(HttpServletRequest request){
		Map<String,String> map = new HashMap<String,String>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = StringUtils.trim(request.getParameter(name));
			if (StringUtils.isNotBlank(value)) {
				if("limit".equals(name)){//防止恶意攻击,最多查100条
					if(Integer.valueOf(value)>100){
						map.put(name, "100");
					}else if(Integer.valueOf(value)==0){
						map.put(name, "10");
					}else{
						map.put(name, value);
					}
				}else{
					map.put(name, value);
				}
			}
		}
		return map;
	}
	
	/**
	 * 获取所有的参数加密成md5
	 * @param request
	 * @return
	 */
	public static String getParamsMd5(HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = StringUtils.trim(request.getParameter(name));
			sb.append(name).append(value);
		}
		return "_"+DigestUtils.md5DigestAsHex(sb.toString().getBytes());
	}

	
}
