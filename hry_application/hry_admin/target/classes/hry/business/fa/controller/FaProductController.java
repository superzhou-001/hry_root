/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 14:23:46 
 */
package hry.business.fa.controller;

import com.alibaba.fastjson.JSONArray;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.model.FaCost;
import hry.business.fa.model.FaFundInitParam;
import hry.business.fa.model.FaProduct;
import hry.business.fa.model.FaProductRate;
import hry.business.fa.service.FaCostService;
import hry.business.fa.service.FaProductRateService;
import hry.business.fa.service.FaProductService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> FaProductController </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 14:23:46 
 */
@Api(value = "产品表 ", tags = "产品表 ", description = "产品表 ")
@RestController
@RequestMapping("/fa/faproduct")
public class FaProductController extends BaseController {

	@Autowired
	private FaProductService faProductService;
	@Autowired
    private FaProductRateService faProductRateService;
	@Autowired
    private FaCostService faCostService;

	/**
     * <p> 产品表 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:23:46 
     */
    @ApiOperation(value = "产品表 -id查询", notes = "产品表 -id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaProduct faProduct = faProductService.get(id);
        if (faProduct != null) {
            // 获取产品的费率集合
            QueryFilter filter = new QueryFilter(FaProductRate.class);
            filter.addFilter("productid=", id);
            List<FaProductRate> rateList = faProductRateService.find(filter);
            if (rateList != null && rateList.size() > 0) {
                String productRateJson = JSONArray.toJSONString(rateList);
                faProduct.setProductRateJson(productRateJson);
            }
            List<FaCost> costList = faCostService.findFaCostList(id);
            faProduct.setFaCostList(costList);
            jsonResult.setObj(faProduct);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 产品表 -创建产品 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:23:46 
     */
    @ApiOperation(value = "产品表 -创建产品", notes = "产品表 -创建产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/createProduct")
    public JsonResult createProduct (FaProduct faProduct,
                                     HttpServletRequest request) {
        if (faProduct.getStatus() == null) {
            return new JsonResult(false).setObj("创建产品步骤不正确");
        }
        return faProductService.createProduct(faProduct);
    }

    /**
     * <p> 产品表 -上一步</p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:23:46
     */
    @ApiOperation(value = "产品表 -上一步", notes = "产品表 -上一步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/backStep")
    public JsonResult backStep (@ApiParam(name = "productId", value = "产品Id", required = true) @RequestParam String productId,
                                @ApiParam(name = "status", value = "当前步骤，1: 产品基础信息 2: 配置计息模型 3: 选择附加费用 4: 创建完成", required = true) @RequestParam String status,
                                HttpServletRequest request) {
        if ("1".equals(status)) {
            return new JsonResult(false).setObj("当前步骤没有上一步");
        }
        FaProduct faProduct = faProductService.get(Long.parseLong(productId));
        faProduct.setStatus(Integer.parseInt(status) - 1);
        faProductService.update(faProduct);
        // 获取产品的费率集合
        QueryFilter filter = new QueryFilter(FaProductRate.class);
        filter.addFilter("productid=", productId);
        List<FaProductRate> rateList = faProductRateService.find(filter);
        if (rateList != null && rateList.size() > 0) {
            String productRateJson = JSONArray.toJSONString(rateList);
            faProduct.setProductRateJson(productRateJson);
        }
        List<FaCost> costList = faCostService.findFaCostList(Long.parseLong(productId));
        faProduct.setFaCostList(costList);
        return new JsonResult(true).setObj(faProduct);
    }

    /**
     * <p> 产品表 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:23:46 
     */
    @ApiOperation(value = "产品表 -id删除", notes = "产品表 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        // 删除产品相关费率信息
        QueryFilter filter = new QueryFilter(FaProductRate.class);
        filter.addFilter("productid=", id);
        faProductRateService.delete(filter);
        faProductService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品表 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:23:46 
     */
    @ApiOperation(value = "产品表 -分页查询", notes = "产品表 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "status", value = "status 4 已完成 非4 草稿箱", required = true) @RequestParam String status,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaProduct.class, request);
        if ("4".equals(status)) {
            filter.addFilter("status=", 4);
        } else {
            filter.addFilter("status_in", "1,2,3");
        }
        return faProductService.findPageResult(filter);
    }

    /**
     * <p> 产品表 -查询集合 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:23:46
     */
    @ApiOperation(value = "产品表 -查询集合", notes = "产品表 -查询集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findProductList")
    public JsonResult findProductList (
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaProduct.class);
        filter.addFilter("openstart=",1);
        List<FaProduct> faProducts = faProductService.find(filter);
        return new JsonResult(true).setObj(faProducts);
    }

    /**
     * <p> 产品表 -启用or禁用 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:23:46
     */
    @ApiOperation(value = "产品表 -启用or禁用", notes = "产品表 -启用or禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/openProduct")
    public JsonResult openProduct (
            @ApiParam(name = "productid", value = "产品id", required = true) @RequestParam String productid,
            @ApiParam(name = "openstart", value = "是否启用 0 关闭 1 启动", required = true) @RequestParam String openstart,
            HttpServletRequest request) {
        FaProduct faProduct = new FaProduct();
        faProduct.setId(Long.parseLong(productid));
        faProduct.setOpenstart(Integer.parseInt(openstart));
        faProductService.update(faProduct);
        return new JsonResult(true);
    }


    @ApiOperation(value = "分录生成还款记录", notes = "分录生成还款记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/createFundList")
    public JsonResult createFundList(FaFundInitParam param,
                                    HttpServletRequest request) {
        return faProductService.createFundList(param);
    }

    @ApiOperation(value = "合计生成还款记录", notes = "合计生成还款记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/createFundListSynthesize")
    public JsonResult createFundListSynthesize(FaFundInitParam param,
                                     HttpServletRequest request) {
        return faProductService.createFundListSynthesize(param);
    }

    @ApiOperation(value = "获取到期时间", notes = "获取到期时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/getIntentDate")
    public JsonResult getIntentDate(
                                   @ApiParam(name = "financingTerm", value = "融资期数", required = true) @RequestParam String financingTerm,
                                   @ApiParam(name = "repayperiod", value = " 1 日 2 月 3 季 4 半年 5年 6 自定义", required = true) @RequestParam String repayperiod,
                                   @ApiParam(name = "periodday", value = "自定义 天数", required = false) @RequestParam String periodday,
                                   @ApiParam(name = "applyLoanDate", value = "放款日期", required = true) @RequestParam String applyLoanDate,
                                   HttpServletRequest request) {
        Map<String, String> param = new HashMap<>();
        param.put("financingTerm", financingTerm);
        param.put("repayperiod", repayperiod);
        param.put("periodday", periodday);
        param.put("applyLoanDate", applyLoanDate);
        return faProductService.getIntentDate(param);
    }
}
