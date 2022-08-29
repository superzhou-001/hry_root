/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Yuan Zhicheng
 * @version:      V1.0 
 * @Date:        2015年9月16日 上午11:04:39
 */
package hry.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.nutz.lang.Strings;

import com.alibaba.fastjson.JSON;
/**
 * 字符串工具类
 * 
 * @author:      Yuan Zhicheng
 *
 */

public class StringUtil {

	/**
	 * 首字母转小写
	 * 
	 * @param s
	 * @return
	 */
	public static String lowerFirst(String s) {
		return Strings.lowerFirst(s);
	}

	/**
	 * 首字母转大写
	 * 
	 * @param s
	 * @return
	 */
	public static String upperFirst(String s) {
		return Strings.upperFirst(s);
	}

	/**
	 * 获得一个不带-符号的uuid
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 字符串转空字符  如果为空转“”  如果不为空原本返回
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @return
	 * @return: String 
	 * @Date :          2015年10月15日 上午10:36:04   
	 * @throws:
	 */
	public static String toNULL(String str){
		if(str!=null){
			return str;
		}else{
			return "";
		}
	}
	
	/**
	 * 字符串判空  
	 *   空返回false  
	 *   非空返回true
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @param str
	 * @param:    @return 空返回false  非空返回true
	 * @return: String 
	 * @Date :          2015年10月19日 下午2:16:54   
	 * @throws:
	 */
	public static boolean isNull(String str){
		if(str!=null&&!"".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	  /**
	   * 将 String 转为 map
	   * 
	   * @param param
	   *            {aa=11,bb=22,cc=33}
	   * @return
	   */
	  public static Map<String, Object> str2map(String param) {
	      Map<String, Object> map = new HashMap<String, Object>();
	      if ("".equals(param) || null == param) {
	          return map;
	      }
	      param=param.replace("{", "").replace("}", "");
	      String[] params = param.split(",");
	      for (int i = 0; i < params.length; i++) {
	          String[] p = params[i].split("=");
	          if (p.length == 2) {
	              map.put(p[0], p[1]);
	          }
	      }
	      return map;
	  }
	  
	  /**
	   * 
	   * 将手机号转成  159****0107 格式 
	   * 
	   * @author:    ** *****
	   * @version:   V1.0 
	   * @date:      2016年9月8日 下午5:05:58
	   */
	  public static String regularString(String str){
		  String s2 = str.replaceAll("^(.{3})(.*)(.{4})$", "$1****$3");
		  return s2;
	  }
	  
	 
	  /**
	   * <p> TODO</p>
	   * @author:         Shangxl
	   * @param:    @param list
	   * @param:    @param sigin
	   * @param:    @return
	   * @return: String 
	   * @Date :          2017年6月14日 下午7:29:48   
	   * @throws:
	   */
	  public static String getSplitList(List<String> list,String sigin){
			StringBuffer b=new StringBuffer();
			for(String l:list){
				b.append(l+sigin);
			}
			b.deleteCharAt(b.length()-1);
			return b.toString();
	  }
	  
	  /**
	   * <p> TODO</p>
	   * @author:         Shangxl
	   * @param:    @param nums 格式："1,2,3"
	   * @param:    @param sigin 格式：","
	   * @param:    @return
	   * @return: List<String> 
	   * @Date :          2017年6月14日 下午7:31:44
	   * @throws:
	   */
	  public static List<Integer> getMergeIntegerList(String nums,String sigin){
		  List<Integer> list=new ArrayList<Integer>();
		  if(null!=nums){
			String[] arry=nums.split(sigin);
			for(int a=0;a<arry.length;a++){
				list.add(Integer.valueOf(arry[a]));
			}
		  }
		  return list;
	  	}
	  

	
	/**
	 * 数组字典排序，并用符号链接
	 * <p> TODO</p>
	 * @author:         Shangxl
	 * @param:    @param s
	 * @param:    @return
	 * @return: String[] 
	 * @Date :          2017年7月26日 上午10:41:25   
	 * @throws:
	 */
	public static String stringSort(String[] s,String rule) {
		StringBuffer result=new StringBuffer();
		List<String> list = new ArrayList<String>(s.length);
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}
		Collections.sort(list);
		String [] arr=list.toArray(s);
		for(int i=0;i<arr.length;i++){
			result.append(arr[i]).append(rule);
		}
		return result.deleteCharAt(result.length()-1).toString();
	}
	
	/**
	 * map转化为post请求参数
	 * <p> TODO</p>
	 * @author:         Shangxl
	 * @param:    @param map
	 * @param:    @return  a=1&b=2
	 * @return: String 
	 * @Date :          2017年7月26日 上午11:06:08   
	 * @throws:
	 */
	public static String string2params(Map<String,String> map){
		StringBuffer result=new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) { 
			String key=entry.getKey();
			String value=entry.getValue();
			result.append(key).append("=").append(value==null?"":value.trim()).append("&");
		}
		return result.deleteCharAt(result.length()-1).toString();
	}
	
	/**
	 * <p> TODO</p>
	 * @author:         Shangxl
	 * @param:    @return
	 * @return: String 
	 * @Date :          2017年8月9日 下午7:00:15   
	 * @throws:
	 */
	public static String string2json(String str){
		String result="";
		if(!"".equals(str)&&str!=null){
			if(str.contains("{")&&str.contains("}")){
				int start=str.indexOf("{");
				int end=str.lastIndexOf("}")+1;
				result=str.substring(start,end);
				//检测是json
				try {
					JSON.parseObject(result);
				} catch (Exception e) {
					e.printStackTrace();
					result="";
				}
			}
		}
		return result;
	}
	
	/**
	 * 数据中的元素包含空
	 * <p> TODO</p>
	 * @author:         Shangxl
	 * @param:    @param s
	 * @param:    @return
	 * @return: boolean 
	 * @Date :          2017年8月10日 上午10:25:41   
	 * @throws:
	 */
	public static boolean containEmpty(String[] s){
		if(s!=null&&s.length>0){
			for(String l:s){
				if(l==null||"".equals(l)){
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
}
