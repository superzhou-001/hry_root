/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:22:05 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.fa.model.FaProductContract;
import hry.business.fa.service.FaProductContractService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FaProductContractController </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:22:05 
 */
@Api(value = "产品合同管理 ", tags = "产品合同管理 ", description = "产品合同管理 ")
@RestController
@RequestMapping("/fa/faproductcontract")
public class FaProductContractController extends BaseController {

	@Autowired
	private FaProductContractService faProductContractService;

	/**
     * <p> 产品合同表 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 15:22:05 
     */
    @ApiOperation(value = "产品合同表 -id查询", notes = "产品合同表 -id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getProductContract")
    public JsonResult getProductContract (@ApiParam(name = "productid", value = "productid", required = true) @RequestParam Long productid) {
        return faProductContractService.getProductContract(productid);
    }

	/**
     * <p> 产品关联合同 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 15:22:05 
     */
    @ApiOperation(value = "产品关联合同 -添加", notes = "产品关联合同 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (@ApiParam(name = "productid", value = "产品id", required = false) @RequestParam String productid,
                           @ApiParam(name = "contractids", value = "合同id集合，用逗号隔开", required = false) @RequestParam String contractids,
                           HttpServletRequest request) {
        return faProductContractService.addProductContract(Long.parseLong(productid), contractids);
    }


    /**
     * <p> 产品关联合同 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 15:22:05 
     */
    @ApiOperation(value = "产品关联合同 -分页查询", notes = "产品关联合同 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "productname", value = "产品名称", required = false) @RequestParam String productname,
            @ApiParam(name = "productid", value = "产品id", required = false) @RequestParam String productid,
            @ApiParam(name = "productTypeId", value = "产品分类id", required = false) @RequestParam String productTypeId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaProductContract.class, request);
        return faProductContractService.findPageProductContractList(filter);
    }

}
