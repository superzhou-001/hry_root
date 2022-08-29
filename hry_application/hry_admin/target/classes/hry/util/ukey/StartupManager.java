package hry.util.ukey;

import hry.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务启动时加载
 * 初始化配置Ukey配置文件中密钥路径
 *
 * @author liuchenghui
 */
@Component
@Order
public class StartupManager implements CommandLineRunner {

    static Logger logger = LoggerFactory.getLogger(StartupManager.class);
    /**
     * 获取主配置文件中的密钥路径
     */
    @Value("${ukey.bsidkeyPath}")
    private String bsidkeyPath;

    /**
     * 获取windows下主配置文件中的ini配置文件
     */
    @Value("${ukey.winIniPath}")
    private String winIniPath;

    /**
     * 获取后台超级管理员账户--暂时和ukey放再一块
     * */
    @Value("${app.system.admin}")
    private String superAdmin;

    /**
     * 获取linux下主配置文件中的ini配置文件
     */
    @Value("${ukey.linuxIniPath}")
    private String linuxIniPath;

    @Autowired
    private RedisService redisService;

    @Override
    public void run (String... args) throws Exception {
        try {
            logger.info("...............存入admin超级管理员账户................");
            redisService.save("app.system.admin", superAdmin);
            logger.info("................正在准备设置ini配置文件................");
            String path = StartupManager.class.getClassLoader().getResource("").getPath();
            logger.info(".......加载设置ini配置文件.......获取路径中.........");
            logger.info("密钥文件路径：" + path + bsidkeyPath);
            // 获取当前系统类型
            String osName = System.getProperty("os.name").toLowerCase();
            logger.info("当前系统为：" + osName);
            String inipath = "";
            // windows系统
            if (osName.indexOf("windows") != -1) {
                if (path.startsWith("/")) {
                    path = path.substring(1);
                }
                inipath = path + winIniPath;
                logger.info("windows下ini文件路径：" + inipath);
            } else if (osName.indexOf("linux") != -1) { // linux系统
                inipath = path +linuxIniPath;
                logger.info("linux下ini文件路径：" + inipath);
            }
            boolean b = ReadAndSetINIFileUtil.writeCfgValue(inipath, "SETTINGS", "EncryptionKeyFile", path + bsidkeyPath);
            logger.info("................结束设置ini配置文件................");
            if (b) {
                logger.info("................设置成功................");
                // 将ini配置文件绝对路径存储到redis中
                redisService.save("ukey.iniRealPath", inipath);
            } else {
                logger.info("................设置失败................");
            }
        } catch (Exception e) {
            System.out.println("-----"+e.getLocalizedMessage());
        }

    }

}

