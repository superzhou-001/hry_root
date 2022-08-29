/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:34:53 
 */
package hry.business.fa.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.ct.model.CtContractTemplate;
import hry.business.ct.service.CtContractTemplateService;
import hry.business.fa.dao.FaProductContractDao;
import hry.business.fa.model.FaProduct;
import hry.business.fa.service.FaProductService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaProductContract;
import hry.business.fa.service.FaProductContractService;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> FaProductContractService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:34:53 
 */
@Service("faProductContractService")
public class FaProductContractServiceImpl extends BaseServiceImpl<FaProductContract, Long> implements FaProductContractService {

	@Resource(name = "faProductContractDao")
	@Override
	public void setDao (BaseDao<FaProductContract, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private CtContractTemplateService ctContractTemplateService;

	@Autowired
	private FaProductService faProducttService;

	@Override
	public PageResult findPageProductContractList(QueryFilter filter) {
		Page page = PageFactory.getPage(filter);
		String productname = filter.getRequest().getParameter("productname");
		String productid = filter.getRequest().getParameter("productid");
		String productTypeId = filter.getRequest().getParameter("productTypeId");
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isNull(productname)) {
			map.put("productname", productname+"%");
		}
		if (StringUtil.isNull(productid)) {
			map.put("productid", productid);
		}
		if (StringUtil.isNull(productTypeId)) {
			map.put("productTypeId", productTypeId);
		}
		((FaProductContractDao)dao).findProductContractList(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}

	@Override
	public JsonResult addProductContract(Long productid, String contractids) {
		// 删除已存在关联
		QueryFilter filter = new QueryFilter(FaProductContract.class);
		filter.addFilter("productid=", productid);
		super.delete(filter);
		// 添加新关联记录
		String[] contractIdList = contractids.split(",");
		Arrays.stream(contractIdList).forEach(contractid -> {
			FaProductContract faProductContract = new FaProductContract();
			faProductContract.setProductid(productid);
			faProductContract.setContractid(Long.parseLong(contractid));
			super.save(faProductContract);
		});
		return new JsonResult(true);
	}

	@Override
	public JsonResult getProductContract(Long productid) {
		FaProductContract result = new FaProductContract();
		// 获取产品信息
		FaProduct faProduct = faProducttService.get(productid);
		result.setProductid(faProduct.getId());
		result.setProductname(faProduct.getProductname());
		// 获取该产品下的合同信息
		List<CtContractTemplate> templateList = ((FaProductContractDao)dao).findContractTemplate(productid);
		result.setContractTemplates(templateList);
		return new JsonResult(true).setObj(result);
	}
}
