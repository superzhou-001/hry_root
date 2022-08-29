/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:07
 */
package hry.activiti.process.service.impl;

import hry.activiti.process.model.ProDefine;
import hry.activiti.process.model.ProDefineForm;
import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.service.ProDefineFormService;
import hry.activiti.process.service.ProDefineNodeService;
import hry.activiti.process.service.ProDefineService;
import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> ProDefineFormService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:07
 */
@Service("proDefineFormService")
public class ProDefineFormServiceImpl extends BaseServiceImpl<ProDefineForm, Long> implements ProDefineFormService {

	@Resource(name = "proDefineFormDao")
	@Override
	public void setDao (BaseDao<ProDefineForm, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private ProDefineService proDefineService;

	@Autowired
	private ProDefineNodeService proDefineNodeService;

	@Override
	public JsonResult saveByFlow(Long defineId, String nodeKey, ProDefineForm form) {
		ProDefine proDefine = proDefineService.get(defineId);
		if(proDefine==null){
			return  new JsonResult().setMsg("流程定义不存在");
		}

		//查询节点
		ProDefineNode node =proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
		if(node==null){
			return  new JsonResult().setMsg("流程节点不存在");
		}

		form.setNodeId(node.getId());
		save(form);

		return new JsonResult().setSuccess(true);
	}

	@Override
	public JsonResult deleteByFlow(Long defineId, String nodeKey, Long formId, Integer type) {

		ProDefine proDefine = proDefineService.get(defineId);
		if(proDefine==null){
			return  new JsonResult().setMsg("流程定义不存在");
		}

		//查询节点
		ProDefineNode node =proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
		if(node==null){
			return  new JsonResult().setMsg("流程节点不存在");
		}

		delete(new QueryFilter(ProDefineForm.class).addFilter("nodeId=",node.getId()).addFilter("formId=",formId).addFilter("type=",type));

		return new JsonResult().setSuccess(true);
	}

	@Override
	public List<ProDefineForm> findMainByNodeId(Long id) {
		return find(new QueryFilter(ProDefineForm.class).addFilter("type=", ProDefineForm.TYPE_1).addFilter("nodeId=",id));
	}

	@Override
	public List<ProDefineForm> findSonByNodeId(Long id) {
		return find(new QueryFilter(ProDefineForm.class).addFilter("type=", ProDefineForm.TYPE_2).addFilter("nodeId=",id));
	}

	@Override
	public JsonResult setEdit(Long defineId, String nodeKey, Long formId, Integer type, Integer isEdit) {

		ProDefine proDefine = proDefineService.get(defineId);
		if(proDefine==null){
			return  new JsonResult().setMsg("流程定义不存在");
		}

		//查询节点
		ProDefineNode node =proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
		if(node==null){
			return  new JsonResult().setMsg("流程节点不存在");
		}

		ProDefineForm pdf = get(new QueryFilter(ProDefineForm.class).addFilter("type=", type).addFilter("formId=", formId).addFilter("nodeId=", node.getId()));
		if(pdf!=null) {
			pdf.setIsEdit(isEdit);
			update(pdf);
			return  new JsonResult().setSuccess(true);
		}
		return new JsonResult();
	}

	@Override
	public String getAllSonKey(String nodeKey, String pdKey, Integer version) {
		//获取节点
		ProDefineNode node = proDefineNodeService.getNode(nodeKey,pdKey,version);
		List<ProDefineForm> sonFormList = find(new QueryFilter(ProDefineForm.class).addFilter("nodeId=", node.getId()).addFilter("type=",ProDefineForm.TYPE_2));
		if(sonFormList!=null&&sonFormList.size()>0){
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < sonFormList.size(); i++){
				ProDefineForm form = sonFormList.get(i);
				sb.append(form.getFormKey());
				if(i!=sonFormList.size()-1){
					sb.append(",");
				}
			}
			return sb.toString();
		}
		return null;
	}
}
