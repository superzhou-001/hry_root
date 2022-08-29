package hry.config.remote;

import hry.activiti.process.model.ProDefine;
import hry.activiti.process.service.ProDefineService;
import hry.core.mvc.controller.BaseController;
import hry.security.jwt.annotation.UnAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/activitiRemoteService")
public class ActivitiRemoteServiceImpl extends BaseController {

    @Autowired
    ProDefineService proDefineService;

    /**
     * 查询授信流程
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/findCreditDefine")
    @UnAuthentication
    @ResponseBody
    public List<ProDefine> findCreditDefine() {
        return proDefineService.findAll();
    }

    /**
     * 获取流程定义信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getDefine/{id}")
    @UnAuthentication
    @ResponseBody
    public ProDefine getDefine(@PathVariable("id") Long id) {
        return proDefineService.get(id);
    }




}
