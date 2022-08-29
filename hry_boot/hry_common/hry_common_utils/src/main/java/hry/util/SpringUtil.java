package hry.util;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p> 此类不供业务系统调用</p>
 * @author:         Liu Shilei
 * @Date :          2015年9月17日 上午11:52:41
 */

@Component
public class SpringUtil implements ApplicationContextAware {

	// Spring应用上下文环境
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
    	SpringUtil.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取注入对象
     * <p> TODO</p>
     * @author:         Liu Shilei
     * @param:    @param name
     * @param:    @return
     * @return: Object
     * @Date :          2015年9月17日 上午11:56:57
     * @throws:
     */
    public static <T> T getBean(String name)   {
    	try {
    		return (T) applicationContext.getBean(name);
		} catch (NoSuchBeanDefinitionException e) {
		}
    	return null;
    }


}
