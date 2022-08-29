/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:01:27 
 */
package hry.scm.project.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.scm.project.dao.MortgageTotalDao;
import hry.scm.project.model.MortgageDetail;
import hry.scm.project.model.MortgageTotal;
import hry.scm.project.model.Storage;
import hry.scm.project.service.MortgageDetailService;
import hry.scm.project.service.MortgageTotalService;
import hry.scm.project.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p> MortgageTotalService </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:01:27 
 */
@Service("mortgageTotalService")
public class MortgageTotalServiceImpl extends BaseServiceImpl<MortgageTotal, Long> implements MortgageTotalService {

	@Resource(name = "mortgageTotalDao")
	@Override
	public void setDao (BaseDao<MortgageTotal, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	public StorageService storageService;

	@Autowired
	public MortgageDetailService mortgageDetailService;


	@Override
	public List<MortgageTotal> findTotalBySql(Map<String, String> map) {
		return ((MortgageTotalDao)dao).findTotalBySql(map);
	}

	/**
	 * 查询某种质押物的具体库存信息
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult findStorage(Map<String, String> map) {
		JsonResult result=new JsonResult();
        String totalId=map.get("totalId");
        MortgageTotal total=this.get(Long.valueOf(totalId));
        if(null==total || "".equals(total)){
			result.setSuccess(false).setMsg("该质押物不存在，请确认");
			return result;
		}
        map.put("goodsName",total.getGoodsName());
		List<Storage> list=storageService.findStorage(map);
		return result.setSuccess(true).setObj(list);
	}

	/**
	 * 现货质押价格管理
	 * @param filter
	 * @return
	 */
	@Override
	public PageResult list(QueryFilter filter) {
		Page<MortgageTotal> page = PageFactory.getPage(filter);
		String projectNumber = filter.getRequest().getParameter("projectNumber");
		String goodsName = filter.getRequest().getParameter("goodsName");
		QueryFilter filter1=new QueryFilter(MortgageTotal.class);
		if(null!=projectNumber && !"".equals(projectNumber)){
			filter1.addFilter("projectNumber_like","%"+projectNumber+"%");
		}
		if(null!=goodsName && !"".equals(goodsName)){
			filter1.addFilter("goodsName_like","%"+goodsName+"%");
		}
		filter1.setOrderby("id desc");
		List<MortgageTotal> list=this.find(filter1);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}
	/**
	 * 查询质押业务总价值
	 * @param map
	 * @return
	 */
	@Override
	public BigDecimal findMortWeight(Map<String, String> map) {
		return ((MortgageTotalDao)dao).findMortWeight(map);
	}

	@Override
	public JsonResult findDetail(Map<String, String> map) {
		JsonResult result=new JsonResult();
		String totalId=map.get("totalId");
		MortgageTotal total=this.get(Long.valueOf(totalId));
		if(null==total || "".equals(total)){
			result.setSuccess(false).setMsg("该质押物不存在，请确认");
			return result;
		}
	    QueryFilter qf=new QueryFilter(MortgageDetail.class);
		qf.addFilter("totalId=",totalId);
		List<MortgageDetail> list=mortgageDetailService.find(qf);
		return result.setSuccess(true).setObj(list);
	}

}
