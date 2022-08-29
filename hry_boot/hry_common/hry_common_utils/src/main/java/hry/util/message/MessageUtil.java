/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Zhang Xiaofang
 * @version:      V1.0 
 * @Date:        2016年4月12日 下午2:50:28
 */
package hry.util.message;



import hry.util.SpringUtil;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * <p> TODO</p>
 * @author:         Zhang Xiaofang 
 * @Date :          2016年4月12日 下午2:50:28 
 */
public class MessageUtil {
	
	/**
	 * 
	 * <p> TODO</p>
	 * @author:         Zhang Xiaofang
	 * @param:    @param paramString  message.properties中的key
	 * @param:    @param paramObject  message.properties中的value 第{i}个表示参数的值
	 * @param:    @param paramLocale   语言（国际化时使用）
	 * @param:    @return
	 * @return: String 
	 * @Date :          2016年4月12日 下午3:04:13   
	 * @throws:
	 */
	public static String getText(String paramString, Object paramObject)
	  {
	    MessageSource localMessageSource =  SpringUtil.getBean("messageSource");
	    Locale local=null;
	    return localMessageSource.getMessage(paramString, new Object[] { paramObject }, local);
	  }

	  public static String getText(String paramString, Object[] paramArrayOfObject)
	  {
	    MessageSource localMessageSource =SpringUtil.getBean("messageSource");
	    Locale local=null;
	    return localMessageSource.getMessage(paramString, paramArrayOfObject, local);
	  }
	  /**
	   * 
	   * <p> TODO</p>
	   * @author:         Zhang Xiaofang
	   * @param:    @param paramString
	   * @param:    @param paramObject
	   * @param:    @param paramLocale
	   * @param:    @param mes  如果paramString不存在时 设置默认值
	   * @param:    @return
	   * @return: String 
	   * @Date :          2016年4月12日 下午3:30:59   
	   * @throws:
	   */
	  public static String getText(String paramString, Object paramObject, String mes)
	  {
	    MessageSource localMessageSource = SpringUtil.getBean("messageSource");
	    Locale local=null; 
	    return localMessageSource.getMessage(paramString, new Object[] { paramObject },mes, local);
	  }
	  
	  
	  public static String getText(String paramString)
	  {
	    MessageSource localMessageSource = SpringUtil.getBean("messageSource");
	    Locale local=null;
	    return localMessageSource.getMessage(paramString, null, local);
	  }
}
