/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 18:34:06
 */
package hry.activiti.process.service.impl;

import hry.activiti.process.model.*;
import hry.activiti.process.service.ProDefineFormService;
import hry.activiti.process.service.ProDefineNodeService;
import hry.activiti.process.service.ProDefineService;
import hry.activiti.process.service.ProSonFormService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> ProSonFormService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 18:34:06
 */
@Service("proSonFormService")
public class ProSonFormServiceImpl extends BaseServiceImpl<ProSonForm, Long> implements ProSonFormService {

	@Resource(name = "proSonFormDao")
	@Override
	public void setDao (BaseDao<ProSonForm, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private ProDefineService proDefineService;

	@Autowired
	private ProDefineNodeService proDefineNodeService;

	@Autowired
	private ProDefineFormService proDefineFormService;

	@Override
	public List<ProSonForm> findByNode(Long mainId, Long defineId, String nodeKey) {
		List<ProSonForm> allList = find(new QueryFilter(ProSonForm.class).addFilter("mainId=",mainId).addFilter("isEnable=", 1));

		if (allList != null && allList.size() > 0) {
			ProDefine proDefine = proDefineService.get(defineId);
			ProDefineNode node = proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
			List<ProDefineForm> nodeList = proDefineFormService.findSonByNodeId(node.getId());

			if (nodeList != null && nodeList.size() > 0) {

				for (ProSonForm form : allList) {

					for (ProDefineForm nodeForm : nodeList) {
						//判断主表单是否包含节点表单
						if (form.getId() == nodeForm.getFormId()) {
							form.setHasNode(1);
							form.setIsEdit(nodeForm.getIsEdit());
							break;
						}
					}
				}
			}
		}

		return allList;
	}
}
