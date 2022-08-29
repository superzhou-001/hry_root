package hry.config;

import hry.platform.communication.email.service.MailConfigService;
import hry.platform.config.service.AppConfigService;
import hry.platform.config.service.NewAppDicService;
import hry.platform.newuser.service.NewAppMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *  @author: liuchenghui
 *  @Date: 2020/3/24 13:45
 *  @Description: 初始配置类
 */
@Component
public class InitDataApplication implements ApplicationRunner {

    @Autowired
    private NewAppDicService newAppDicService;
    @Autowired
    private AppConfigService appConfigService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private NewAppMenuService newAppMenuService;

    @Override
    public void run (ApplicationArguments args) throws Exception {
        try {
            // 初始化缓存app_config
            initConfigData();
            // 初始化new_app_dic缓存
            initDicData();
            // 初始化邮箱配置
            initEmailConfig();
            // 将顶部子菜单同步到菜单表中
            initMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/3/24 13:54
     *  @Description: 初始化字典数据
     */
    private void initDicData () {
        newAppDicService.flushRedis();
        newAppDicService.initAreaCache();
        // 初始化Dic树
        newAppDicService.initDicTree();
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/3/24 13:54
     *  @Description: 初始化网站参数配置数据
     */
    private void initConfigData () {
        appConfigService.initCache();
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/8 17:54
     *  @Description: 初始化邮箱配置
     */
    private void initEmailConfig () {
        mailConfigService.initCache();
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/8 17:55
     *  @Description: 将顶部子菜单同步到菜单表中
     */
    private void initMenu() {
        newAppMenuService.initMenu();
    }

}
