/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-17 15:08:55 
 */
package hry.scm.project.service.impl;

import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.scm.project.model.Price;
import hry.scm.project.service.PriceService;
import hry.util.date.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p> PriceService </p>
 *
 * @author: luyue
 * @Date: 2020-07-17 15:08:55 
 */
@Service("priceService")
public class PriceServiceImpl extends BaseServiceImpl<Price, Long> implements PriceService {

	@Resource(name = "priceDao")
	@Override
	public void setDao (BaseDao<Price, Long> dao) {
		super.dao = dao;
	}

	/**
	 * 查询最近60天价格记录
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult findList(Map<String, String> map) {
		String goodsName=map.get("goodsName");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate= DateUtil.addMonthsToDate(new Date(),-2);
		QueryFilter qf=new QueryFilter(Price.class);
		qf.addFilter("goodsName=",goodsName);
		qf.addFilter("recordDate<=",sdf.format(new Date()));
		qf.addFilter("recordDate>",sdf.format(startDate));
		List<Price> list=this.find(qf);
		return new JsonResult().setSuccess(true).setObj(list);
	}
}
