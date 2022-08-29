/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-24 17:45:18 
 */
package hry.business.cf.service.impl;

import com.github.pagehelper.Page;
import hry.bean.PageResult;
import hry.business.cf.dao.CfFacilityFlowDao;
import hry.business.cf.dao.CfProcessContractDao;
import hry.business.cf.model.CfFacilityFlow;
import hry.business.ct.model.CtContractTemplate;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cf.model.CfProcessContract;
import hry.business.cf.service.CfProcessContractService;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> CfProcessContractService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-24 17:45:18 
 */
@Service("cfProcessContractService")
public class CfProcessContractServiceImpl extends BaseServiceImpl<CfProcessContract, Long> implements CfProcessContractService {

	@Resource(name = "cfProcessContractDao")
	@Override
	public void setDao (BaseDao<CfProcessContract, Long> dao) {
		super.dao = dao;
	}


	@Override
	public List<CtContractTemplate> findListBySql(Long id) {
		return ((CfProcessContractDao) dao).findListBySql(id);
	}
}
