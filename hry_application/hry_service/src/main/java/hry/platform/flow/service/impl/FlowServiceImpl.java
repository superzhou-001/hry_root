package hry.platform.flow.service.impl;

import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.platform.flow.service.FlowService;
import hry.util.SpringUtil;
import hry.util.flowModel.FlowParams;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
public class FlowServiceImpl  implements FlowService {


    @Override
    public JsonResult startFlow(FlowParams flowParams) {

//        //控制器名称
//        String controlName = flowParams.getControlName();
//        String[] split = controlName.split("\\.");
//        Object bean = SpringUtil.getBean(split[0]);
//
//        try {
//            String beanClassName = bean.getClass().getName().substring(0,bean.getClass().getName().indexOf("$"));
//            String modelClassName = beanClassName.replace("service.impl","model").replace("ServiceImpl","");
//            Object modelObj = Class.forName(modelClassName).newInstance();
//
//            Method method = modelObj.getClass().getMethod("setDefineKey",String.class);
//            method.invoke(modelObj,defineKey);
//            Class<?> beanClass = bean.getClass();
//            Method[] methods = beanClass.getMethods();
//            for (Method m : methods){
//                System.out.println(m.getName());
//            }
//            Method save = beanClass.getMethod("save", Serializable.class);
//            save.invoke(bean,modelObj);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new JsonResult().setMsg("保存主表错误");
//        }

        return new JsonResult().setSuccess(true);

    }

    @Override
    public JsonResult completeTask(FlowParams flowParams) {

        //控制器名称
        String controlName = flowParams.getControlName();
        String[] split = controlName.split("\\.");
        Object bean = SpringUtil.getBean(split[0]);

        try {
            Class<?> beanClass = bean.getClass();
//            Method[] methods = beanClass.getMethods();
//            for(Method method :methods){
//                System.out.println(method.getName());
//            }
            Method save = beanClass.getMethod(split[1], JSONObject.class);
            JsonResult result = (JsonResult) save.invoke(bean, flowParams);
            if(result==null){
                return new JsonResult().setMsg("控制方法执行错误");
            }
            if(!result.getSuccess()){
                return new JsonResult().setMsg(result.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().setMsg("按钮控制方法不存在");
        }
        return new JsonResult().setSuccess(true);
    }

    @Override
    public FlowParams execute(FlowParams flowParams) {
        //控制器名称
        String controlName = flowParams.getControlName();
        String[] split = controlName.split("\\.");
        Object bean = SpringUtil.getBean(split[0]);

        try {
            Class<?> beanClass = bean.getClass();
//            Method[] methods = beanClass.getMethods();
//            for(Method method :methods){
//                System.out.println(method.getName());
//            }
            Method save = beanClass.getMethod(split[1], FlowParams.class);
            FlowParams result = (FlowParams) save.invoke(bean, flowParams);
            if(result==null){
                return new FlowParams().setMsg("控制方法执行错误|"+controlName);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new FlowParams().setMsg("控制方法不存在|"+controlName);
        }
    }
}
