/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 10:36:12
 */
package hry.business.fa.service.impl;

import com.alibaba.fastjson.JSON;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaFactoringRate;
import hry.business.fa.service.FaFactoringRateService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> FaFactoringRateService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 10:36:12
 */
@Service("faFactoringRateService")
public class FaFactoringRateServiceImpl extends BaseServiceImpl<FaFactoringRate, Long> implements FaFactoringRateService {

	@Resource(name = "faFactoringRateDao")
	@Override
	public void setDao (BaseDao<FaFactoringRate, Long> dao) {
		super.dao = dao;
	}

	@Override
	public void saveJson(Long projectId, String jsonString) {

		if(projectId==null|| StringUtils.isEmpty(jsonString)){
			return ;
		}

		List<FaFactoringRate> list = JSON.parseArray(jsonString, FaFactoringRate.class);
		if(list!=null&&list.size()>0){
			for(FaFactoringRate faFactoringRate : list){
				faFactoringRate.setProjectId(projectId);
				save(faFactoringRate);
			}
		}
	}
}
