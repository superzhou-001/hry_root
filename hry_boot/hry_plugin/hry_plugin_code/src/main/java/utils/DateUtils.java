/**
 * Copyright:   互融云
 * @author:      Liu Shilei
 * @version:      V1.0 
 * @Date:        2016年9月29日 下午3:19:38
 */
package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei 
 * @Date :          2016年9月29日 下午3:19:38 
 */
public class DateUtils {
	
	public static String getDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		return dateFormat.format(new Date());
	}
	
}
