/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 13:41:22 
 */
package hry.business.fa.service.impl;

import hry.bean.JsonResult;
import hry.business.fa.dao.FaBillDao;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaBill;
import hry.business.fa.service.FaBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> FaBillService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 13:41:22 
 */
@Service("faBillService")
public class FaBillServiceImpl extends BaseServiceImpl<FaBill, Long> implements FaBillService {

	@Resource(name = "faBillDao")
	@Override
	public void setDao (BaseDao<FaBill, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<FaBill> findBillByProjectId(Long projectId) {
		return ((FaBillDao)dao).findBillByProjectId(projectId);
	}

	@Override
	public void updateStatusByIds(String ids) {
		((FaBillDao)dao).updateStatusByIds(ids);
	}
}
