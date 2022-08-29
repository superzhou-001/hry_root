package hry.util.ukey;

import CRYPTOCard.API.CRYPTOCardAPI;
import hry.redis.RedisService;
import hry.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UKey工具类
 */
public class UkeyUtil {

    static Logger logger = LoggerFactory.getLogger(UkeyUtil.class);

    // 返回状态码
    static int AUTH_FAILURE = 0; // 认证成功
    static int AUTH_SUCCESS = 1; // 认证失败
    static int CHALLENGE = 2;
    static int SERVER_PIN_PROVIDED = 3;
    static int USER_PIN_CHANGE = 4;
    static int OUTER_WINDOW_AUTH = 5;
    static int CHANGE_STATIC_PASSWORD = 6;
    static int STATIC_CHANGE_FAILED = 7;
    static int PIN_CHANGE_FAILED = 8;

    /**
     * 初始化加载Ukey配置
     * @return
     */
    private CRYPTOCardAPI initUkey(){
        // 获取实例
        CRYPTOCardAPI cryptocardApi = CRYPTOCardAPI.getInstance();

        //call this method to set INI file location which will be used for initialization
        //If this file is not present, same file must be at the binary launch directory
        // 从redis中读取ini的绝对路径
        RedisService redisService = SpringUtil.getBean("redisService");
        String inipath = redisService.get("ukey.iniRealPath");
        logger.info("当前系统ukey的ini配置文件绝对路径 ： " + inipath);

        // 加载ini配置文件
        cryptocardApi.setINIPath(inipath);

        //Load libraries from Path set by API Manager
        try {
            // Legacy initialization
            // 加载动态库
            cryptocardApi.LoadJNILibrary();
        } catch (UnsatisfiedLinkError ex) {
            logger.info(ex.toString());
        } catch (Exception ex) {
            logger.info(ex.toString());
        }
        //no error thrown means initialization is OK
        logger.info("Loading and Initialization OK");
        return cryptocardApi;
    }

    /**
     * 验证动态口令
     * @param username 用户名
     * @param dynaPass 动态口令
     * @return
     */
    public static boolean validDynamicPass (String username, String dynaPass) {
    	System.out.println("==========1.UK开始验证===========");
        CRYPTOCardAPI cryptocardApi = new UkeyUtil().initUkey();

        /*
         jstring UserName;
         jstring Organization;
         jstring OTP;
         jstring Challenge;
         jstring State;
         jstring ChallengeData;
         jstring ChallengeMessage;
         jstring ReturndResult;
         jstring BothServersDown;
         jstring ErrorMessage;
         jstring IP;
         */
        // 封装参数
        String[] arrData = new String[11];
        arrData[0] = username;               //username input parameter
        arrData[1] = "";                            //organization input parameter
        arrData[2] = dynaPass;                            //Pin + OTP input parameter
        arrData[3] = "";                            //Challenge output parameter
        arrData[4] = "";                            //State in / output parameter
        arrData[5] = "";                            //ChallengeData output parameter
        arrData[6] = "";                            //ChallengeMessage output parameter
        arrData[7] = "";                            //ReturndResult output parameter - Normal BSID return results 0 to 8
        arrData[8] = "";                            //BothServersDown output parameter - 1 or 0 (1 if down)
        arrData[9] = "";                            //ErrorMessage output parameter - Error Message for Log or client
        arrData[10] = "";                  //Client IP Address input parameter
        
        System.out.println("==========2.UK开始验证===========");
        
        // 调用服务认证中心
        cryptocardApi.Authenticate(arrData);
        
        System.out.println("==========3.UK开始验证===========");

        // 返回结果判断
        if (Integer.parseInt(arrData[7]) == AUTH_SUCCESS) { // 认证成功
            logger.info(String.format("Authentication Test OK" + arrData[7]));
            System.out.println("动态口令认证成功");
            return true;
        } else if (Integer.parseInt(arrData[7]) == AUTH_FAILURE) { // 认证失败
            logger.info(String.format("Authentication FAILED" + arrData[7]));
            System.out.println("动态口令认证失败");
            logger.info(StringUtils.join(arrData, ","));
            return false;
        }
        logger.info(String.format("Authentication FAILED" + arrData[7]));
        logger.info("UK返回结果集====="+StringUtils.join(arrData, ","));
        System.out.println("UK返回结果arrData[7]====="+arrData[7]);
        System.out.println("UK返回结果集====="+StringUtils.join(arrData, ","));
        return false;
    }


    // 测试用
    public static void main(String[] args){
         validDynamicPass("leijinrong-cs", "2345162358");
    }
}
