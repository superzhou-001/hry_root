package hry.platform.communication.sms.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.platform.config.service.AppConfigService;
import hry.redis.RedisService;
import hry.util.SpringUtil;
import hry.util.UUIDUtil;
import hry.util.httpRequest.HttpConnectionUtil;
import hry.util.rsa.FXHParam;
import hry.util.rsa.RSAUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: yaozh
 * @Description: 调用易租云接口
 * @Date: 2020/7/13 15:20
 */
public class HrySmsCallable implements Callable {

    //手机号
    private String phone;
    //短信签名
    private String signName;
    //短信模板变量替换JSON串
    private String templateParam;
    //国内国际状态“0”：国内，“1”：国际
    private String state;
    //内容
    private String msg;
    //请求uri
    private String uri;


    /**
     * @param phone 手机号
     * @param signName 短信签名
     * @param templateParam 短信模板变量替换JSON串
     * @param msg 内容
     * @param state 国内国际状态“0”：国内，“1”：国际
     * @param uri 请求uri
     */
    public HrySmsCallable(String phone, String signName, String templateParam, String msg,String state,String uri) {
        this.phone = phone;
        this.signName = signName;
        this.templateParam = templateParam;
        this.msg = msg;
        this.state = state;
        this.uri = uri;
    }

    @Override
    public JsonResult call () {

        JsonResult jsonResult = new JsonResult();
        try {
            AppConfigService appConfigService = SpringUtil.getBean("appConfigService");
            //公钥
            String publicKey = appConfigService.getValueByKey("publicKey");
            //私钥
            String privateKey = appConfigService.getValueByKey("secretKey");
            //商户号
            String companyCode = appConfigService.getValueByKey("companyCode");
            //URL
            String requestUrl = appConfigService.getValueByKey("yzyRequestUrl");

            String sign = setMap(privateKey);

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("companyCode", companyCode);
            paramMap.put("privateKeyUrl", privateKey);
            paramMap.put("publicKeyUrl", publicKey);
            paramMap.put("sign", sign);

            String returnMsg = HttpConnectionUtil.doPostQuery(requestUrl+this.uri, paramMap);
            if (StringUtils.isEmpty(returnMsg)) {
                jsonResult.setSuccess(false);
                jsonResult.setMsg(returnMsg);
            } else {
                JSONObject jsonObject = JSONObject.parseObject(returnMsg);
                if ("8888".equals(jsonObject.getString("resultCode"))) {
                    jsonResult.setSuccess(true);
                    jsonResult.setMsg(returnMsg);
                } else {
                    jsonResult.setSuccess(false);
                    jsonResult.setMsg(returnMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * 生成签名
     * @param privateKey 私钥
     * @return 签名
     * @throws Exception
     */
    private String setMap (String privateKey) throws Exception {
        //map存需要加密的字段
        Map<String, Object> map = new HashMap<>(16);
        map.put("signName", this.signName);
        map.put("ordernumber", UUIDUtil.getUUID());
        map.put("belonged", "");
        map.put("phone", this.phone);
        map.put("msg", this.msg);
        map.put("templateParam", this.templateParam);
        map.put("state", this.state);
        return RSAUtil.encryptByPrivateKey(JSONObject.toJSONString(map), privateKey);
    }

}
