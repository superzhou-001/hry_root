/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-24 11:17:34 
 */
package hry.scm.project.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.scm.project.dao.RedeemDetailDao;
import hry.scm.project.model.RedeemDetail;
import hry.scm.project.model.vo.RedeemDetailVo;
import hry.scm.project.service.RedeemDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> RedeemDetailService </p>
 *
 * @author: luyue
 * @Date: 2020-07-24 11:17:34 
 */
@Service("redeemDetailService")
public class RedeemDetailServiceImpl extends BaseServiceImpl<RedeemDetail, Long> implements RedeemDetailService {

	@Resource(name = "redeemDetailDao")
	@Override
	public void setDao (BaseDao<RedeemDetail, Long> dao) {
		super.dao = dao;
	}
	/**
	 * 解除质押物事查询清单方法
	 * @return
	 */
	@Override
	public List<RedeemDetailVo> findAlldetailBySql(Map<String, String> map) {
		return ((RedeemDetailDao)dao).findAlldetailBySql(map);
	}
}
