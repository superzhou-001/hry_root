package hry.platform.dic;

import hry.core.util.QueryFilter;
import hry.platform.config.model.NewAppDic;
import hry.platform.config.service.NewAppDicService;
import hry.security.jwt.annotation.UnAuthentication;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(value = "数据字典", tags = "数据字典", description = "数据字典")
@RestController
@RequestMapping("/config/newappdic")
public class NewAppDicController {

    @Autowired
    private NewAppDicService newAppDicService;

    @UnAuthentication
    @ApiOperation(value = "数据字典-根据多pkey查询", notes = "数据字典-根据多pkey查询")
    @GetMapping(value = "/findMapList")
    public Map<String, List<NewAppDic>> findMapList (@ApiParam(name = "pkeys", value = "父类字典key,多key用逗号连接", required = true) @RequestParam String pkeys,
                                                     HttpServletRequest request) {
        Map<String, List<NewAppDic>> map = new HashMap<>();
        if (!StringUtils.isEmpty(pkeys)) {
            String[] pkeyList = pkeys.split(",");
            Arrays.stream(pkeyList).forEach((pkey) -> {
                map.put(pkey, newAppDicService.findDicList(pkey));
            });
        }
        return map;
    }


    /**
     * <p> 数据字典-根据pkey查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:28:18
     */
    @UnAuthentication
    @ApiOperation(value = "数据字典-根据pkey查询", notes = "数据字典-根据pkey查询,地区数据字典pkey=addressarea")
    @GetMapping(value = "/list")
    public List<NewAppDic> list (
            @ApiParam(name = "pkey", value = "父类字典key", required = true) @RequestParam String pkey,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppDic.class, request);
        if (!StringUtils.isEmpty(pkey)) {
            filter.addFilter("pkey=", pkey);
        }
        return newAppDicService.find(filter);
    }


    @UnAuthentication
    @ApiOperation(value = "地区数据字典二级联动-根据pkey查询", notes = "地区数据字典二级联动-根据pkey查询,地区数据字典pkey=addressarea")
    @GetMapping(value = "/listAddress")
    public List<NewAppDic> listAddress (
            @ApiParam(name = "pkey", value = "父类字典key", required = true) @RequestParam String pkey,
            HttpServletRequest request) {
        if (StringUtils.isEmpty(pkey)) {
            return null;
        }
        QueryFilter filter = new QueryFilter(NewAppDic.class);
        //110000:北京；120000：天津；500000：重庆； 310000：上海
        if("110000".equals(pkey) || "120000".equals(pkey) ||"500000".equals(pkey) ||"310000".equals(pkey)){
            List<NewAppDic> dicList = new ArrayList<>();
            filter.addFilter("pkey=", pkey);
            List<NewAppDic> list = newAppDicService.find(filter);
            for(NewAppDic newAppDic : list){
                dicList.addAll(newAppDicService.find(new QueryFilter(NewAppDic.class).addFilter("pkey=", newAppDic.getMkey())));
            }
            return dicList;
        }else{
            filter.addFilter("pkey=", pkey);
            return newAppDicService.find(filter);
        }

    }
}
