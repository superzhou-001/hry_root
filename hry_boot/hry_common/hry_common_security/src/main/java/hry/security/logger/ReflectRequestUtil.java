package hry.security.logger;

import hry.security.jwt.annotation.UnAuthentication;
import hry.util.ReadClassUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Slf4j
public class ReflectRequestUtil {

    private final static String backPackage = "hry";

    public static void findRequest(){

        Set<String> noLoginSet = new HashSet<>();

        Set<Class<?>> set = ReadClassUtil.getClasses(backPackage);
        Iterator<Class<?>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            //如果是controller类则进行权限资源操作
            if(clazz.getName().contains(".controller.")){
                //获得类的RequestMapping 注解
                String currentViewPrefix = "";
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                if (requestMapping != null && requestMapping.value().length > 0) {
                    currentViewPrefix = requestMapping.value()[0];
                }

                //如果为/  则置空  防止双//
                if("/".equals(currentViewPrefix)){
                    currentViewPrefix = "";
                }

                //获得类中全部方法
                Method[] methods = clazz.getDeclaredMethods();
                //循环所有方法
                for(Method method : methods){
                    UnAuthentication noLoginAnnotation = method.getAnnotation(UnAuthentication.class);
                    if(noLoginAnnotation!=null){
                        String[] value = null;
                        String apiValue = "";
                        RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                        if(methodRequestMapping!=null){
                            value = methodRequestMapping.value();
                        }

                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        if(getMapping!=null){
                            value = getMapping.value();
                        }

                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if(postMapping!=null){
                            value = postMapping.value();
                        }

                        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                        if(apiOperation!=null){
                            apiValue = apiOperation.value();
                        }

                        if(value!=null) {
                            log.info("提示:未授权访问路径[" + currentViewPrefix + value[0] + "] @ApiOperation.value="+apiValue);
                            noLoginSet.add(currentViewPrefix + value[0]);
                        }
                    }
                }
            }
        }
    }

}
