/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:56
 */
package hry.activiti.process.service.impl;

import hry.activiti.process.model.ProDefine;
import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.model.ProDefineNotice;
import hry.activiti.process.service.ProDefineNodeService;
import hry.activiti.process.service.ProDefineNoticeService;
import hry.activiti.process.service.ProDefineService;
import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> ProDefineNoticeService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:56
 */
@Service("proDefineNoticeService")
public class ProDefineNoticeServiceImpl extends BaseServiceImpl<ProDefineNotice, Long> implements ProDefineNoticeService {

	@Resource(name = "proDefineNoticeDao")
	@Override
	public void setDao (BaseDao<ProDefineNotice, Long> dao) {
		super.dao = dao;
	}


	@Autowired
	private ProDefineService proDefineService;

	@Autowired
	private ProDefineNodeService proDefineNodeService;

	@Override
	public JsonResult saveByFlow(Long defineId, String nodeKey, ProDefineNotice proDefineNotice) {
		ProDefine proDefine = proDefineService.get(defineId);
		if(proDefine==null){
			return  new JsonResult().setMsg("流程定义不存在");
		}

		//查询节点
		ProDefineNode node =proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
		if(node==null){
			return  new JsonResult().setMsg("流程节点不存在");
		}

		proDefineNotice.setNodeId(node.getId());
		save(proDefineNotice);

		return new JsonResult().setSuccess(true);
	}
}
