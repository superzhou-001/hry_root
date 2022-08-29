/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-24 10:37:13
 */
package hry.helpLoan;

import hry.bean.JsonResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppProduct;
import hry.platform.website.model.AppProductClass;
import hry.platform.website.service.AppProductClassService;
import hry.platform.website.service.AppProductService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> AppProductController </p>
 *
 * @author: liushilei
 * @Date: 2020-07-24 10:37:13
 */
@Api(value = "产品", tags = "产品", description = "产品")
@RestController
@RequestMapping("/product")
public class ProductCotroller extends BaseController {

	@Autowired
	private AppProductService appProductService;

	@Autowired
    private AppProductClassService appProductClassService;

	/**
     * <p> 产品详情 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:37:13
     */
    @ApiOperation(value = "产品详情", notes = "产品详情")
    @ControllerLogger
    @UnAuthentication
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppProduct appProduct = appProductService.get(id);
        if (appProduct != null) {
            AppProductClass appProductClass = appProductClassService.get(appProduct.getClassId());
            if(appProductClass!=null){
                appProduct.setClassName(appProductClass.getName());
            }
            if(!StringUtils.isEmpty(appProduct.getProductShow())){
                appProduct.setProductShow(HtmlUtils.htmlUnescape(appProduct.getProductShow()));
            }
            if(!StringUtils.isEmpty(appProduct.getProductApproval())){
                appProduct.setProductApproval(HtmlUtils.htmlUnescape(appProduct.getProductApproval()));
            }
            jsonResult.setObj(appProduct);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }


    /**
     * <p> 产品-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:37:13
     */
    @ApiOperation(value = "产品-分页查询", notes = "产品-分页查询")
    @ControllerLogger
    @UnAuthentication
    @PostMapping(value = "/list")
    public List<AppProduct> list (HttpServletRequest request) {
        return appProductService.find(new QueryFilter(AppProduct.class).addFilter("isEnable=",1));
    }

}
