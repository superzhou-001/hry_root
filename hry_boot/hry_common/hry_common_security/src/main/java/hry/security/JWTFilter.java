package hry.security;

import com.alibaba.fastjson.JSONObject;
import hry.redis.RedisService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.JWTToken;
import hry.security.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt过滤器,如果有token表示登录
 */
@Slf4j
public class JWTFilter implements Filter {

    /**
     * 设置存储到redis中的RefreshToken key的前缀
     */

    @Autowired
    RedisService redisService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        isAccessAllowed(servletRequest, servletResponse);
        //放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response) {

        // 判断用户是否想要登入
        if (this.isAuthorization(request)) {

            // 获取当前请求中的AccessToken
            String accessToken = this.getToken(request);
            JWTToken token = new JWTToken(accessToken);
            // 执行reids认证登录
            boolean isLogin = this.executeRedisLogin(token);
            if (!isLogin) {
                // 该异常为JWT的AccessToken已过期

                // 获取AccessToken中的用户名
                String userName = JWTUtil.getUsername(accessToken);
                // 获取AccessToken中的来源
                String source = token.getSource();
                String type = token.getType();
                String signId = token.getSignId();


                // 获取当前时间戳
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                // 获取刷新token的剩余过期时间
                Object refreshTime = redisService.get("JWT:token:" + source + ":" + type + ":refreshTime:"+ signId);
                // 如果刷新时间大于0，则进行重新签名
                if (refreshTime !=null) {
                    //重新生成AccessToken
                    String newToken = this.reSign(token);
                    if(!StringUtils.isEmpty(accessToken)){
                        // 将刷新的AccessToken存放在Response的Header中的Authorization字段返回
                        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                        httpServletResponse.setHeader("Authorization", newToken);
                        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                    }
                    return true;
                }
            }

        }

        return true;
    }

    /**
     * 检测Header里面是否包含Authorization字段，有就进行Token登录认证授权
     */
    protected boolean isAuthorization(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("token");
        return authorization != null;
    }

    /**
     * 利用singId  redis信息登录
     * @param token
     * @return
     */
    protected boolean executeRedisLogin(JWTToken token) {
        //获取signId
        String signId = token.getSignId();
        if(!StringUtils.isEmpty(signId)){
           String userStr =  redisService.get("JWT:token:"  + token.getSource() + ":" + token.getType() + ":user:" + signId);
           if (!StringUtils.isEmpty(userStr)) {
               JSONObject manageUser = JSONObject.parseObject(userStr);
               if (manageUser != null) {
                   if(token.getType().equals(JWTToken.TYPE_MANAGE)){
                       if (JWTUtil.verify(token.getToken(), token.getSignId(),token.getUserName(), token.getSource(), token.getType(), manageUser.getString("passWord"))) {
                           JWTContext.setLogin(userStr);
                           return true;
                       }
                   }else{
                       if (JWTUtil.verify(token.getToken(), token.getSignId(),token.getUserName(), token.getSource(), token.getType(), manageUser.getString("password"))) {
                           JWTContext.setLogin(userStr);
                           return true;
                       }
                   }


               }
           }
        }
        return false;
    }


    /**
     * 重新签名
     */
    protected String reSign(JWTToken token) {

        String userStr = (String) redisService.get("JWT:token:"  + token.getSource() + ":" + token.getType() + ":refreshTime:" + token.getSignId());
        JSONObject user = JSONObject.parseObject(userStr);
        //生成签名
        String sign = JWTUtil.sign(token.getUserName(), token.getSource(),token.getType(),user.getString("passWord")==null?user.getString("password"):user.getString("passWord"));
        //保存当前用户
        JWTContext.setLogin(userStr);
        // 将token存储到redis中作为刷新token的凭证，并设置key的过期时间作为刷新有效期
        redisService.save("JWT:token:"+token.getSource()+":"+token.getType()+":refreshTime:" + JWTUtil.getSignId(sign),userStr, JWTUtil.REFRESH_TIME);
        redisService.save("JWT:token:"+token.getSource()+":"+token.getType()+":user:" + JWTUtil.getSignId(sign), userStr,JWTUtil.EXPIRE_TIME);

        //如果是后台用户重置token，同时更新登录时长的key
        if("manage".equals(token.getType())) {
            redisService.lock(JWTUtil.getManageAllKey()+"lock");
            try {
                String loginId = redisService.hget(JWTUtil.getManageAllKey(), token.getSignId());
                redisService.hset(JWTUtil.getManageAllKey(), JWTUtil.getSignId(sign), loginId);
                redisService.hdel(JWTUtil.getManageAllKey(), token.getSignId());
            }catch (Exception e){

            }finally {
                redisService.unLock(JWTUtil.getManageAllKey()+"lock");
            }
        }

        redisService.delete("JWT:token:"+token.getSource()+":"+token.getType()+":refreshTime:"+token.getSignId());
        return sign;
    }


    /**
     * 获取Token
     */
    private String getToken(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        return token;
    }


}
