package hry.security;

import com.alibaba.fastjson.JSONObject;
import hry.security.jwt.JWTContext;
import hry.security.jwt.annotation.UnAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * ManageToken JWT登录拦截器
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;

                if(isStatic(request)){
                    return true;
                }

                //swagger跳过
                if(isSwagger(request)){
                    return true;
                }

                UnAuthentication unAuthentication = handlerMethod.getMethod().getAnnotation(UnAuthentication.class);
                //如果标记  unAuthentication，直接跳过身份认证
                if(unAuthentication!=null){
                    return true;
                }

                String token = request.getHeader("token");
                if (StringUtils.isEmpty(token)) {

                    PrintWriter writer = response.getWriter();
                    response.setStatus(401);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("msg", "no token");
                    jsonObject.put("success", false);

                    writer.print(jsonObject.toJSONString());
                    writer.flush();
                    writer.close();
                    return false;
                }
                //判断是否登录
                if (!JWTContext.isLogin()) {
                    PrintWriter writer = response.getWriter();
                    response.setStatus(401);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("msg", "please login");
                    jsonObject.put("success", false);
                    writer.print(jsonObject.toJSONString());
                    writer.flush();
                    writer.close();
                    return false;
                }

                // 校验该请求是否有请求权限
                /*String URI = request.getRequestURI();
                URI = URI.substring(0,URI.lastIndexOf("/"));
                JWTToken tokenStr = new JWTToken(token);
                RedisService redisService = JWTSpringUtil.getBean("redisService");
                String shiroUrls = redisService.get("JWT:token:" + tokenStr.getSource() + ":" + tokenStr.getType() + ":shiroUrls:" + tokenStr.getSignId());
                if (shiroUrls.indexOf(URI) == -1) {
                    PrintWriter writer = response.getWriter();
                    response.reset();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("msg",  "insufficient privileges");
                    jsonObject.put("success", false);
                    writer.print(jsonObject.toJSONString());
                    writer.flush();
                    writer.close();
                    return false;
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    private boolean  isSwagger(HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        if(requestURL.toString().contains("swagger-resources")){
            return  true;
        }
        if(requestURL.toString().contains("/process")){
            return  true;
        }
        return false;
    }

    private boolean  isStatic(HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        if(requestURL.toString().contains("error")){
            return  true;
        }
        if(requestURL.toString().contains("static")){
            return  true;
        }
        if(requestURL.toString().contains("flow")){
            return  true;
        }
        return false;
    }
}
