package hry.business.qcc.service;

import hry.bean.JsonResult;
import hry.platform.config.service.AppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: yaozh
 * @Description:
 * @Date:
 */
public interface QccFactoryService {
    /**
     * 获取qcc信息
     * @param paramMap
     * @param qccMap
     * @return
     */
    JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap);

}
