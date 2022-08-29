package hry.util.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {
	/**
	 * 写入文件
	 * <p>
	 * TODO
	 * </p>
	 * 
	 * @author: Shangxl
	 * @param: @param url
	 * @param: @param map
	 * @return: void
	 * @Date : 2017年9月14日 上午11:13:48
	 * @throws:
	 */
	public static void writeProperties(String url, Map<String, String> map) {
		Properties properties = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream(url);
			Iterator<Entry<String, String>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				properties.setProperty(entry.getKey(), entry.getValue());
			}
			properties.store(output, "modify:" + new Date().toString());// 保存键值对到文件中
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 遍历properties
	 * <p> TODO</p>
	 * @author:         Shangxl
	 * @param:    @param url
	 * @param:    @return
	 * @return: Map<String,String> 
	 * @Date :          2017年9月14日 上午11:29:07   
	 * @throws:
	 */
	public static Map<String, String> printAll(String url) {
		Properties prop = new Properties();
		Map<String, String> map = null;
		InputStream input = null;
		try {
			input = PropertiesUtil.class.getClassLoader().getResourceAsStream(url);
			if (input == null) {
				System.out.println("Sorry, unable to find " + url);
			}
			prop.load(input);
			Set<Entry<Object, Object>> entrys = prop.entrySet();// 返回的属性键值对实体
			map = new HashMap<String, String>();
			for (Entry<Object, Object> entry : entrys) {
				String key=entry.getKey().toString();
				String value=entry.getValue().toString();
				map.put(key,value);
			}
			return map;
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
}
