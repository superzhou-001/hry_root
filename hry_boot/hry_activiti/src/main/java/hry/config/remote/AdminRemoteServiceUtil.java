package hry.config.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class AdminRemoteServiceUtil {

     @Autowired
     AdminRemoteService adminRemoteService;


    /**
     * 根据id获取一个或多个用户的名称
     * @param ids
     * @return
     */
     public String getUserName(String ids){
         String personName = "";
         //如果有多个批量处理
         if (!StringUtils.isEmpty(ids) &&ids.contains(",")) {
             String[] split =ids.split(",");
             for (int i = 0; i < split.length; i++) {
                 String appUserStr = adminRemoteService.getAppUser(Long.valueOf(split[i]));
                 if (!StringUtils.isEmpty(appUserStr)) {
                     JSONObject appUser = JSON.parseObject(appUserStr);
                     personName += appUser.getString("name");
                     if (i != split.length - 1) {
                         personName += ",";
                     }
                 }
             }
         }
         if(!StringUtils.isEmpty(ids)){
             String appUserStr = adminRemoteService.getAppUser(Long.valueOf(ids));
             if (!StringUtils.isEmpty(appUserStr)) {
                 JSONObject appUser = JSON.parseObject(appUserStr);
                 personName = appUser.getString("name");
             }
         }

         return personName;
     }

}
