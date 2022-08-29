/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-13 14:31:34
 */
package hry.business.cu.service.impl;

import com.github.pagehelper.Page;
import hry.bean.PageResult;
import hry.business.cu.dao.CuEnterprisePersonDao;
import hry.business.cu.model.CuEnterprisePerson;
import hry.business.cu.model.CuPerson;
import hry.business.cu.service.CuEnterprisePersonService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> CuEnterprisePersonService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-13 14:31:34
 */
@Service("cuEnterprisePersonService")
public class CuEnterprisePersonServiceImpl extends BaseServiceImpl<CuEnterprisePerson, Long> implements CuEnterprisePersonService {

	@Resource(name = "cuEnterprisePersonDao")
	@Override
	public void setDao (BaseDao<CuEnterprisePerson, Long> dao) {
		super.dao = dao;
	}


	@Override
	public PageResult findPageBySql(QueryFilter filter) {
		// ----------------------分页查询头部外壳------------------------------
		Page<CuPerson> page = PageFactory.getPage(filter);
		String type = filter.getRequest().getParameter("type");
		String enterpriseId = filter.getRequest().getParameter("enterpriseId");

		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(type)) {
			map.put("type", type);
		}
		if (!StringUtils.isEmpty(enterpriseId)) {
			map.put("enterpriseId", enterpriseId);
		}

		((CuEnterprisePersonDao) dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}

	@Override
	public List<CuPerson> personList(QueryFilter filter) {
		String type = filter.getRequest().getParameter("type");
		String enterpriseId = filter.getRequest().getParameter("enterpriseId");

		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(type)) {
			map.put("type", type);
		}
		if (!StringUtils.isEmpty(enterpriseId)) {
			map.put("enterpriseId", enterpriseId);
		}

		return ((CuEnterprisePersonDao) dao).findPageBySql(map);
	}

	@Override
	public CuPerson getCuEnterprisePersonByEnterpriseIdAndType(Long enterpriseId, Integer type) {
		return ((CuEnterprisePersonDao) dao).getCuEnterprisePersonByEnterpriseIdAndType(enterpriseId,type);
	}

	@Override
	public PageResult findCuEnterprisePersonByEnterpriseIdAndType(QueryFilter filter) {
		String type = filter.getRequest().getParameter("type");
		String enterpriseId = filter.getRequest().getParameter("enterpriseId");

		Page<CuEnterprisePerson> page = PageFactory.getPage(filter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("enterpriseId", enterpriseId);
		((CuEnterprisePersonDao) dao).findCuEnterprisePersonByEnterpriseIdAndType(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());

	}

	@Override
	public void updatwIsMainPerson(Long enterpriseId) {
		((CuEnterprisePersonDao) dao).updatwIsMainPerson(enterpriseId);
	}

	@Override
	public List<CuPerson> findPersonList(Long enterpriseId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(type)) {
			map.put("type", type);
		}
		if (!StringUtils.isEmpty(enterpriseId)) {
			map.put("enterpriseId", enterpriseId);
		}

		return ((CuEnterprisePersonDao) dao).findPageBySql(map);
	}
}
