package plugin;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

/**
 * 读取代码生成参数配置
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年10月14日 下午4:21:22
 */
public class Config {

    public static Properties config;

    private Config () {}

    public Config (String resourcePath) {
        // 获得配置文件
        config = new Properties();
        try {
            // 加载配置文件内容
            config.load(new FileReader(new File(resourcePath + File.separator + "code.properties")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
