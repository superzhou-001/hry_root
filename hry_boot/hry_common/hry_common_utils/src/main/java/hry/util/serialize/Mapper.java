/**
 * Copyright:   北京互融时代软件有限公司
 * @author:        Wu Shuiming
 * @version:      V1.0
 * @Date :    2015年12月15日  下午4:54:56
 */
package hry.util.serialize;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nutz.json.Json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author: wu shuiming
 * @date： 日期：2015年12月15日 时间：下午4:54:56
 * @version 1.0
 */
public class Mapper {
	private final static ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 此方法将json数据转换成list 集合
	 *
	 * @author: Wu Shuiming
	 * @Date : 日期：2015年12月18日 时间 ：下午6:26:35
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> List<T> jsonToList(String json) {

		if (json != null) {
			JavaType javaType = MAPPER.getTypeFactory()
					.constructParametricType(List.class, Object.class);
			List<T> list = new ArrayList<T>();
			try {
				list = MAPPER.readValue(json, javaType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		return null;

	}

	/**
	 * 此方法用用于将json串转换成map
	 *
	 * @author: Wu Shuiming
	 * @Date : 日期：2015年12月18日 时间 ：下午6:29:37
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> Map<T, T> jsonToMap(String json) {

		if (json != null) {
			JavaType javaType = MAPPER.getTypeFactory()
					.constructParametricType(Map.class, Object.class);
			Map map = new HashMap();
			try {
				map = MAPPER.readValue(json, javaType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return map;
		}
		return null;
	}

	/**
	 * 将json 转化成bean
	 *
	 * @author: Wu Shuiming
	 * @Date : 日期：2015年12月15日 时间 ：下午7:24:19
	 * @param jsonStr
	 * @param obj
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T JSONToObj(String jsonStr, Class<T> obj) {
		T t = null;
		try {
			t = MAPPER.readValue(jsonStr, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 将javaBean转成json
	 *
	 * @author: Wu Shuiming
	 * @Date : 日期：2015年12月15日 时间 ：下午7:26:07
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static <T> String objectToJson(T obj) {
		ObjectMapper mapper = new ObjectMapper();
		// Convert object to JSON string
		String jsonStr = "";
		try {
			jsonStr = MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;

	}

	public static String listToJson(List list) {
		String json = Json.toJson(list);
		return json;
	}

	public static String mapToJson(Map map) {
		String json = Json.toJson(map);
		return json;
	}


	/**
	 * 将javaBean 的所有字段都封装成一个个键值对的map集合
	 *
	 * @author: Wu Shuiming
	 * @Date : 日期：2015年12月18日 时间 ：下午6:36:33
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBean(Object bean) throws Exception {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * 这是反封装上面的方法
	 *
	 * @author: Wu Shuiming
	 * @Date : 日期：2015年12月15日 时间 ：下午6:37:47
	 * @param type
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object convertMap(Class type, Map map) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = null;

				try {
					value = map.get(propertyName);
				} catch (Exception e) {
				}

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * 将装有多个javaBean的list封装成一个个对象的map的集合
	 *
	 * @author: Wu Shuiming
	 * @Date : 日期：2015年12月15日 时间 ：下午7:27:33
	 * @param l
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map> getList(List list) {
		List lm = new ArrayList<Map>();
		for (Object o : list) {
			try {
				Map m = convertBean(o);
				lm.add(m);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return lm;
	}

}








