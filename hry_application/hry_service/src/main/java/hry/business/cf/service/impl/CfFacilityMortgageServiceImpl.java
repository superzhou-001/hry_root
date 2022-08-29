/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 15:55:35
 */
package hry.business.cf.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cf.dao.CfFacilityMortgageDao;
import hry.business.cf.model.CfFacilityMortgage;
import hry.business.cf.service.CfFacilityMortgageService;
import hry.business.cu.model.CuCar;
import hry.business.cu.model.CuHouse;
import hry.business.cu.service.CuCarService;
import hry.business.cu.service.CuHouseService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> CfFacilityMortgageService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 15:55:35 
 */
@Service("cfFacilityMortgageService")
public class CfFacilityMortgageServiceImpl extends BaseServiceImpl<CfFacilityMortgage, Long> implements CfFacilityMortgageService {

    @Resource(name = "cfFacilityMortgageDao")
    @Override
    public void setDao(BaseDao<CfFacilityMortgage, Long> dao) {
        super.dao = dao;
    }

    @Autowired
    private CuCarService cuCarService;
    @Autowired
    private CuHouseService cuHouseService;

    @Override
    public PageResult findPageBySql(QueryFilter filter) {
        // ----------------------分页查询头部外壳------------------------------
        Page<CfFacilityMortgage> page = PageFactory.getPage(filter);
        Map<String, Object> map = new HashMap<String, Object>();

        ((CfFacilityMortgageDao) dao).findPageBySql(map);
        return new PageResult(page, filter.getPage(), filter.getPageSize());
    }

    @Override
    public JsonResult mortgageAdd(String jsonStr) {
        JsonResult jsonResult = new JsonResult();
        jsonStr = StringEscapeUtils.unescapeHtml(jsonStr);
        CuCar cuCar = null;
        CuHouse cuHouse = null;
        CfFacilityMortgage cfFacilityMortgage = null;
        try {
            //将参数转为json类型
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            //抵质押物基本信息
            String mortgage = jsonObject.getString("mortgage");
            if (StringUtils.isEmpty(mortgage)) {
                return jsonResult.setSuccess(false).setMsg("抵质押物基本信息不能为空");
            }
            cfFacilityMortgage = JSONObject.parseObject(mortgage, CfFacilityMortgage.class);
            Long projectId = cfFacilityMortgage.getProjectId();
            if (StringUtils.isEmpty(projectId)) {
                return jsonResult.setSuccess(false).setMsg("projectId不能为空");
            }

            //抵质押物类型 1.车辆 2.住宅 3. 股权 4.基金 5.公寓 6.应收账款
            switch (cfFacilityMortgage.getMortgageType()) {
                case 1:
                    //车辆信息
                    String car = jsonObject.getString("car");
                    if (!StringUtils.isEmpty(car)) {
                        cuCar = JSONObject.parseObject(car, CuCar.class);
                        cuCar.setSubjectId(projectId);
                        cuCar.setType(3);
                        cuCarService.save(cuCar);
                    }
                    cfFacilityMortgage.setMortgageSubjectId(cuCar.getId());
                    break;
                case 2:
                    //房产信息
                    String house = jsonObject.getString("house");
                    if (!StringUtils.isEmpty(house)) {
                        cuHouse = JSONObject.parseObject(house, CuHouse.class);
                        cuHouse.setSubjectId(projectId);
                        cuHouse.setType(3);
                        cuHouseService.save(cuHouse);
                    }
                    cfFacilityMortgage.setMortgageSubjectId(cuHouse.getId());
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + cfFacilityMortgage.getMortgageType());
            }
            this.save(cfFacilityMortgage);

            return jsonResult.setSuccess(true).setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResult;
    }

    @Override
    public JsonResult getMortgage(Long id) {
        JsonResult jsonResult = new JsonResult();
        CfFacilityMortgage cfFacilityMortgage = ((CfFacilityMortgageDao)dao).getCfFacilityMortgage(id);
        CuCar cuCar = null;
        CuHouse cuHouse = null;

        if (cfFacilityMortgage == null) {
            return jsonResult.setSuccess(false).setMsg("未查询到信息");
        }
        Long projectId = cfFacilityMortgage.getProjectId();
        //抵质押物类型 1.车辆 2.住宅 3. 股权 4.基金 5.公寓 6.应收账款
        switch (cfFacilityMortgage.getMortgageType()) {
            case 1:
                //车辆信息
                QueryFilter carFilter = new QueryFilter(CuCar.class);
                carFilter.addFilter("subjectId=", projectId);
                carFilter.addFilter("type=", 3);
                cuCar = cuCarService.get(carFilter);
                cfFacilityMortgage.setCuCar(cuCar);
                break;
            case 2:
                //车辆信息
                QueryFilter houseFilter = new QueryFilter(CuCar.class);
                houseFilter.addFilter("subjectId=", projectId);
                houseFilter.addFilter("type=", 3);
                cuHouse = cuHouseService.get(houseFilter);
                cfFacilityMortgage.setCuHouse(cuHouse);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cfFacilityMortgage.getMortgageType());
        }
        jsonResult.setObj(cfFacilityMortgage);
        return jsonResult.setSuccess(true);
    }

    @Override
    public JsonResult updateMortgage(String jsonStr) {
        JsonResult jsonResult = new JsonResult();
        jsonStr = StringEscapeUtils.unescapeHtml(jsonStr);
        //将参数转为json类型
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        //抵质押物基本信息
        String mortgage = jsonObject.getString("mortgage");
        if (StringUtils.isEmpty(mortgage)) {
            return jsonResult.setSuccess(false).setMsg("抵质押物基本信息不能为空");
        }
        CfFacilityMortgage cfFacilityMortgage = JSONObject.parseObject(mortgage, CfFacilityMortgage.class);
        //不允许修改
        cfFacilityMortgage.setMortgageSubjectId(null);
        cfFacilityMortgage.setMortgageType(null);
        cfFacilityMortgage.setMortgageType(null);
        this.update(cfFacilityMortgage);

        CuCar cuCar = null;
        CuHouse cuHouse = null;
        //抵质押物类型 1.车辆 2.住宅 3. 股权 4.基金 5.公寓 6.应收账款
        switch (cfFacilityMortgage.getMortgageType()) {
            case 1:
                //车辆信息
                String car = jsonObject.getString("car");
                if (!StringUtils.isEmpty(car)) {
                    cuCar = JSONObject.parseObject(car, CuCar.class);
                    cuCar.setSubjectId(null);
                    cuCar.setType(null);
                    cuCarService.update(cuCar);
                }
                break;
            case 2:
                //房产信息
                String house = jsonObject.getString("house");
                if (!StringUtils.isEmpty(house)) {
                    cuHouse = JSONObject.parseObject(house, CuHouse.class);
                    cuHouse.setSubjectId(null);
                    cuHouse.setType(null);
                    cuHouseService.update(cuHouse);
                }
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cfFacilityMortgage.getMortgageType());
        }

        return jsonResult.setSuccess(true);
    }

    @Override
    public JsonResult removeMortgage(Long id) {
        JsonResult jsonResult = new JsonResult();
        CfFacilityMortgage cfFacilityMortgage = this.get(id);

        if (cfFacilityMortgage != null) {
            return jsonResult.setSuccess(false).setMsg("未查询到信息");
        }
        Long projectId = cfFacilityMortgage.getProjectId();
        //抵质押物类型 1.车辆 2.住宅 3. 股权 4.基金 5.公寓 6.应收账款
        switch (cfFacilityMortgage.getMortgageType()) {
            case 1:
                //车辆信息
                QueryFilter carFilter = new QueryFilter(CuCar.class);
                carFilter.addFilter("subjectId=", projectId);
                carFilter.addFilter("type=", 3);
                cuCarService.delete(carFilter);
                break;
            case 2:
                //车辆信息
                QueryFilter houseFilter = new QueryFilter(CuCar.class);
                houseFilter.addFilter("subjectId=", projectId);
                houseFilter.addFilter("type=", 3);
                cuHouseService.delete(houseFilter);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cfFacilityMortgage.getMortgageType());
        }
        this.delete(id);
        return jsonResult.setSuccess(true);
    }
}
