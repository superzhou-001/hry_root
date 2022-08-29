/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 18:33:52
 */
package hry.activiti.process.service.impl;

import hry.activiti.process.model.ProDefine;
import hry.activiti.process.model.ProDefineForm;
import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.model.ProMainForm;
import hry.activiti.process.service.ProDefineFormService;
import hry.activiti.process.service.ProDefineNodeService;
import hry.activiti.process.service.ProDefineService;
import hry.activiti.process.service.ProMainFormService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> ProMainFormService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 18:33:52
 */
@Service("proMainFormService")
public class ProMainFormServiceImpl extends BaseServiceImpl<ProMainForm, Long> implements ProMainFormService {

	@Resource(name = "proMainFormDao")
	@Override
	public void setDao(BaseDao<ProMainForm, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private ProDefineService proDefineService;

	@Autowired
	private ProDefineNodeService proDefineNodeService;

	@Autowired
	private ProDefineFormService proDefineFormService;

	@Override
	public List<ProMainForm> findByNode(Long defineId, String nodeKey) {

		List<ProMainForm> allList = find(new QueryFilter(ProMainForm.class).addFilter("isEnable=", 1));

		if (allList != null && allList.size() > 0) {
			ProDefine proDefine = proDefineService.get(defineId);
			ProDefineNode node = proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
			if(node!=null) {
				List<ProDefineForm> nodeList = proDefineFormService.findMainByNodeId(node.getId());

				if (nodeList != null && nodeList.size() > 0) {

					for (ProMainForm form : allList) {

						for (ProDefineForm nodeForm : nodeList) {
							//判断主表单是否包含节点表单
							if (form.getId() == nodeForm.getFormId()) {
								form.setHasNode(1);
								break;
							}
						}
					}
				}
			}
		}

		return allList;
	}


}
