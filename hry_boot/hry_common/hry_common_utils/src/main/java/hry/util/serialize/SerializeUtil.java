/**
 * Copyright:   北京互融时代软件有限公司
 * @author:        Wu Shuiming
 * @version:      V1.0 
 * @Date :    2015年12月15日  上午10:55:29
 */
package hry.util.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *类描述：
 *@author: wu shuiming
 *@date： 日期：2015年12月15日        时间：上午10:55:29
 *@version 1.0
 */
public class SerializeUtil {
	
	/**
	 * 序列化一个对象
	 * @author:         Wu Shuiming 
	 * @Date :  日期：2015年12月15日       时间 ：上午10:57:16
	 * @param object
	 * @return
	 */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {

        }
        return null;
    }
    
    /**
     * 反序列化一个对象
     * @author:         Wu Shuiming 
     * @Date :  日期：2015年12月15日       时间 ：上午10:56:58
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        	
        }
        return null;
    }


}
