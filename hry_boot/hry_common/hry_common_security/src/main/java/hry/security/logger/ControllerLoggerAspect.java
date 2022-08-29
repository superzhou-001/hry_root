package hry.security.logger;

import com.alibaba.fastjson.JSON;
import hry.security.jwt.JWTUtil;
import hry.util.httpRequest.IpUtil;
import hry.util.httpRequest.UserAgentUtils;
import hry.util.rmq.RabbitMQProducer;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: liuchenghui
 * @Date: 2020/4/10 15:05
 * @Description: controller日志处理
 */
@Aspect
@Component
@Slf4j
public class ControllerLoggerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/10 19:17
     *  @Description: 设置切点和方法
     */
    //@Pointcut("execution(* hry..*.controller.*.*(..)) || @annotation(hry.security.logger.ControllerLogger)")
    @Pointcut("@annotation(hry.security.logger.ControllerLogger)")
    public void controllerLog () {
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/10 19:18
     *  @Description: 前置切面，设置请求日志信息发送MQ入库
     */
    @Before(value = "controllerLog()")
    public void doBefore (JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // 获取请求ip
        String ip = IpUtil.getIp(request);
        // 获取访问方法
        String methodName = joinPoint.getSignature().getName();
        // 获取请求参数
        String args = Arrays.toString(joinPoint.getArgs());
        // 访问目标类
        Object target = joinPoint.getTarget();
        // 请求路径
        String requestURI = request.getRequestURI();
        // 请求端口
        int port = request.getRemotePort();
        // 请求来源
        String source = UserAgentUtils.getOs(request) + " " + UserAgentUtils.getBrowserName(request);

        // 获取方法ApiOperation注解及ApiOperation注解内容
        String value = "";
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        if (annotation != null) {
            value = annotation.value();
        }

        // 根据token获取用户名，如果是登录方法，直接用登录名
        String username = "System";
        String token = request.getHeader("token");
        if (token == null) {
            String userName = request.getParameter("userName");
            if (StringUtils.isNotEmpty(userName)) {
                username = userName;
            }
        } else {
            username = JWTUtil.getUsername(token);
        }

        // 封装访问日志对象
        ControllerLoggerVo loggerVo = new ControllerLoggerVo();
        loggerVo.setIp(ip);
        loggerVo.setArgs(args);
        loggerVo.setMethodName(methodName);
        loggerVo.setTarget(target.toString());
        loggerVo.setUserName(username);
        loggerVo.setMethodDesc(value);
        loggerVo.setUrl(requestURI);
        loggerVo.setSource(source);
        loggerVo.setPort(port);

        // 发送MQ消息
        logger.info("发送日志消息：{}", JSON.toJSONString(loggerVo));
        rabbitMQProducer.sendMsgByQueue("requestLogger", JSON.toJSONString(loggerVo));
    }
}
