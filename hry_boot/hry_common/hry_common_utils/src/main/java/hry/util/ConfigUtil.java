/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Yuan Zhicheng
 * @version:      V1.0 
 * @Date:        2015年9月16日 上午11:04:39
 */
package hry.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author Yuan Zhicheng
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("core-jdbc");

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}

	/**
	 * 获得hibernate批量操作的最大数值
	 * 
	 * @return
	 */
	public static final String getBatchSize() {
		return get("hibernate.jdbc.batch_size");
	}

}
