package hry.config.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("hry-activiti")
@RequestMapping("/activitiRemoteService")
public interface ActivitiRemoteService {

    /**
     * 查询授信流程,返回list
     * @return
     */
    @GetMapping(value = "/findCreditDefine")
    String findCreditDefine();

    /**
     * 获取流程定义,返回obj
     * @return
     */
    @GetMapping(value = "/getDefine/{id}")
    String getDefine(@PathVariable("id") Long id);


}
