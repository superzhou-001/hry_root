package hry.platform.communication.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.core.thread.ThreadPoolUtils;
import hry.platform.communication.sms.model.AppSmsSend;
import hry.platform.communication.sms.model.AppSmsTemplate;
import hry.platform.communication.sms.service.AppSmsSendService;
import hry.platform.communication.sms.service.AppSmsTemplateService;
import hry.platform.communication.sms.util.HrySmsCallable;
import hry.platform.config.model.YzyConfig;
import hry.platform.config.service.YzyConfigService;
import hry.util.sms.SmsSendVo;
import hry.util.sms.SmsStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: yaozh
 * @Description: 易租云短信发送
 * @Date: 2020/7/13 16:40
 */
@Component
@Slf4j
public class HrySmsServiceImpl implements SmsStrategy {
    private final static Integer CONST_ONE = 1;
    private final static String STATUS_CODE = "8888";
    private final static String SMS_PRODUCT = "getSms";

    @Autowired
    private AppSmsSendService appSmsSendService;
    @Autowired
    private AppSmsTemplateService appSmsTemplateService;
    @Autowired
    private YzyConfigService yzyConfigService;

    @Override
    public JsonResult smsSend(SmsSendVo sendParam) {
        System.out.println("----------发送短信前的验证及发送记录对象封装----------");
        JsonResult jsonResult = new JsonResult();
        try {
            AppSmsSend appSmsSend = new AppSmsSend();
            /** 设置短信内容 */
            // 获取模板
            AppSmsTemplate smsTemplate = appSmsTemplateService.getSmsTemplate(sendParam.getSmsType(), sendParam.getSmsLang());
            if (smsTemplate == null) {
                return jsonResult.setSuccess(false).setMsg("模板内容不能为空");
            }
            // 设置第三方短信模版id
            appSmsSend.setThirdTemplateId("");
            // 设置短信内容
            appSmsSend.setSendContent(smsTemplate.getTempContent());
            // 设置短信发送类型
            appSmsSend.setType(sendParam.getSmsType());
            // 发送手机号
            appSmsSend.setMobileNum(sendParam.getPhone());
            boolean b = sendSmsHandler(appSmsSend, sendParam);
            // 如果发送标记为成功,则标记为已发送
            if (b) {
                System.out.println("----------发送成功----------");
                appSmsSend.setSendStatus("1");
                jsonResult.setSuccess(true);
            } else {
                System.out.println("----------发送失败----------");
                jsonResult.setSuccess(false);
            }
            // 标记为已接收
            appSmsSend.setReceiveStatus("1");
            // 保存发送记录
            appSmsSend.setModified(new Date());
            System.out.println("----------保存发送记录----------");
            appSmsSendService.save(appSmsSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * @Description: 发送短信操作
     * @Date: 2020/7/13 17:15
     */
    private boolean sendSmsHandler(AppSmsSend appSmsSend, SmsSendVo param) {
        //获取易租云短信接口配置信息
            YzyConfig yzyConfig = yzyConfigService.getYzyConfigByProductCode(SMS_PRODUCT);
        // 接口是否开启
        if (CONST_ONE != yzyConfig.getIsOpen()) {
            appSmsSend.setThirdPartyResult("系统短信功能未开启");
            return false;
        }
        // 获取短信内容
        String sendContent = appSmsSend.getSendContent();
        if (StringUtils.isEmpty(sendContent)) {
            appSmsSend.setReceiveStatus("0");
            appSmsSend.setThirdPartyResult("短信模板未设置或未开启");
            return false;
        }
        //获取模板签名
        int start = sendContent.indexOf("【");
        int end = sendContent.indexOf("】");
        String signName = sendContent.substring(start + 1, end);
        sendContent = sendContent.substring(end + 1);
        //替换模板参数
        if(!StringUtils.isEmpty(param.getParam())){
            sendContent = this.replaceString(sendContent, param.getParam());
        }
        appSmsSend.setSendContent(sendContent);
        // 设置短信发送参数
        appSmsSend.setPostParam(JSON.toJSONString(param));
        //调用易租云接口
        HrySmsCallable hrySmsCallable = new HrySmsCallable(param.getCountry() + param.getPhone(),
                signName, param.getParam() == null ? "" : JSONObject.toJSONString(param),
                sendContent, param.getCountry().contains("+") ? "1" : "0", yzyConfig.getProductUrl());
        Future<JsonResult> result = ThreadPoolUtils.submit(hrySmsCallable);
        JsonResult jsonResult = new JsonResult();
        try {
            jsonResult = result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断返回结果
        if (StringUtils.isEmpty(jsonResult)) {
            appSmsSend.setThirdPartyResult("发送失败，调用接口异常！");
            return false;
        }
        JSONObject myJsonObject = JSONObject.parseObject(jsonResult.getMsg());
        String code = myJsonObject.getString("resultCode");
        if (STATUS_CODE.equals(code)) {
            appSmsSend.setReceiveStatus("1");
            appSmsSend.setThirdPartyResult("发送成功！");
            System.out.println(myJsonObject.toString());
            return true;
        } else {
            appSmsSend.setReceiveStatus("0");
            appSmsSend.setThirdPartyResult(myJsonObject.toString());
            return false;
        }
    }

    /**
     * 替换短信模板中的变量
     * @param s
     * @param map
     * @return
     */
    public String replaceString(String s, Map<String, String> map) {
        Pattern mPattern = Pattern.compile("\\$\\{(.*?)}");
        Matcher mMatcher = mPattern.matcher(s);
        while (mMatcher.find()) {
            String find = mMatcher.group(1);
            s = s.replace(mMatcher.group(0), map.get(find));
        }
        return s;
    }

}
