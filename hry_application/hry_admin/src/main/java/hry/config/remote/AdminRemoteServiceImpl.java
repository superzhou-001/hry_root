package hry.config.remote;

import com.alibaba.fastjson.JSON;
import hry.core.mvc.controller.BaseController;
import hry.platform.flow.service.FlowService;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.newuser.service.NewAppUserService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.util.flowModel.FlowParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;


@RestController
@RequestMapping("/adminRemoteService")
public class AdminRemoteServiceImpl extends BaseController {

    @Autowired
    NewAppUserService newAppUserService;

    @Autowired
    FlowService flowService;

    /**
     * 查询后台用户
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getAppUser/{id}")
    @UnAuthentication
    @ResponseBody
    public NewAppUser getAppUser(@PathVariable("id") Long id) {
        return newAppUserService.get(id);
    }

    /**
     * 启动流程--设置流程业务表
     *
     * @return
     */
    @PostMapping(value = "/startFlow")
    @UnAuthentication
    @ResponseBody
    public FlowParams startFlow(
            @RequestParam String params//流程名称

    ) {
        FlowParams flowParams = JSON.parseObject(HtmlUtils.htmlUnescape(params), FlowParams.class);
        return flowService.execute(flowParams);
    }

    /**
     * 启动流程/执行流程
     *
     * @return
     */
    @PostMapping(value = "/completeTask")
    @UnAuthentication
    @ResponseBody
    public FlowParams completeTask(@RequestParam String params//流程参数
    ) {
        System.out.println(HtmlUtils.htmlUnescape(params));
        FlowParams flowParams = JSON.parseObject(HtmlUtils.htmlUnescape(params), FlowParams.class);
        return flowService.execute(flowParams);
    }

    /**
     * 查看流程信息，回显流程业务数据
     *
     * @return
     */
    @PostMapping(value = "/viewFlow")
    @UnAuthentication
    @ResponseBody
    public FlowParams viewFlow(@RequestParam String params//流程参数
    ) {
        FlowParams flowParams = JSON.parseObject(HtmlUtils.htmlUnescape(params), FlowParams.class);
        return flowService.execute(flowParams);
    }

}
