package hry.security.jwt;

import com.alibaba.fastjson.JSON;
import hry.redis.RedisService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTContext {

    private static ThreadLocal<String> currentUser =  new ThreadLocal<String>();

    private JWTContext (){}


    public static void setLogin(String jwtUser){
        currentUser.set(jwtUser);
    }

    private static String getLogin(){
        try {
            return currentUser.get();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            currentUser.remove();
        }
        return null;
    }

    public static  boolean isLogin(){
        String login = getLogin();
        return login != null && !"".equals(login);
    }


    /**
     * 更新用户
     * @param userJson  用户字符串
     */
    public static void updateUser(HttpServletRequest request,String userJson){
        String tokenStr = request.getHeader("token");
        if(!StringUtils.isEmpty(tokenStr)) {
            JWTToken token = new JWTToken(tokenStr);
            RedisService redisService = JWTSpringUtil.getBean("redisService");

            if(token.getType().equals(JWTToken.TYPE_MANAGE)){
                redisService.save(JWTUtil.getManageRefreshTimeKey(tokenStr),userJson, JWTUtil.REFRESH_TIME);
                redisService.save(JWTUtil.getManageUserKey(tokenStr), userJson,JWTUtil.EXPIRE_TIME);
            }else{
                redisService.save(JWTUtil.getCustomerRefreshTimeKey(tokenStr),userJson, JWTUtil.REFRESH_TIME);
                redisService.save(JWTUtil.getCustomerUserKey(tokenStr), userJson,JWTUtil.EXPIRE_TIME);
            }

        }
    }


    /**
     * 获取登录用户
     * @param clazz
     * @return
     */
    public static Object getUser(Class clazz) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        if(request!=null){
            String tokenStr = request.getHeader("token");

            String authorization = response.getHeader("Authorization");
            if(!StringUtils.isEmpty(authorization)){
                tokenStr = authorization;
            }

            if(!StringUtils.isEmpty(tokenStr)){
                JWTToken token = new JWTToken(tokenStr);
                RedisService redisService = JWTSpringUtil.getBean("redisService");
                String userStr = redisService.get("JWT:token:" + token.getSource() + ":" + token.getType() + ":user:" + token.getSignId());
                if(!StringUtils.isEmpty(userStr)){
                    try {
                        return  JSON.parseObject(userStr,clazz);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {

                    }
                }
            }
        }
        return  null;
    }

    /**
     * 获取用户ID
     * @param clazz
     * @return
     */
    public static Long getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        if(request!=null){
            String tokenStr = request.getHeader("token");

            String authorization = response.getHeader("Authorization");
            if(!StringUtils.isEmpty(authorization)){
                tokenStr = authorization;
            }

            if(!StringUtils.isEmpty(tokenStr)){
                JWTToken token = new JWTToken(tokenStr);
                RedisService redisService = JWTSpringUtil.getBean("redisService");
                String userStr = redisService.get("JWT:token:" + token.getSource() + ":" + token.getType() + ":user:" + token.getSignId());
                if(!StringUtils.isEmpty(userStr)){
                    try {
                        return  JSON.parseObject(userStr).getLong("id");
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {

                    }
                }
            }
        }
        return  null;
    }





}
