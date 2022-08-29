package hry.config.remote;

import hry.security.jwt.annotation.UnAuthentication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("hry-admin")
@RequestMapping("/adminRemoteService")
public interface AdminRemoteService {

    @GetMapping(value = "/getAppUser/{id}")
    String getAppUser(@PathVariable("id") Long id);


    @PostMapping(value = "/startFlow")
    @UnAuthentication
    String startFlow(@RequestParam String params);

    @PostMapping(value = "/completeTask")
    @UnAuthentication
    String completeTask(@RequestParam String params);

    @PostMapping(value = "/viewFlow")
    @UnAuthentication
    String viewFlow(@RequestParam String params);
}
