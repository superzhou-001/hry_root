package hry.user.scm.access;

import hry.business.cu.model.CuCustomer;
import hry.security.jwt.JWTContext;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod)handler;
            //拿到注解
            RoleAccess accessRole = hm.getMethodAnnotation(RoleAccess.class);
            if(null==accessRole){
                return true;
            }
            String roleType = accessRole.roleType();
            CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
            Long infoId = user.getInfoId();
            Integer userType = user.getUserType();
            if(roleType.equals("fundSupport")){
                String fundSupportId = request.getParameter("fundSupportId");
                if(null!=fundSupportId && userType == 2 && fundSupportId.equals(infoId.toString())){
                    return true;
                }
            }else if(roleType.equals("enterprise")){
                String enterpriseId = request.getParameter("enterpriseId");
                if(null!=enterpriseId && userType == 1 && enterpriseId.equals(infoId.toString())){
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
