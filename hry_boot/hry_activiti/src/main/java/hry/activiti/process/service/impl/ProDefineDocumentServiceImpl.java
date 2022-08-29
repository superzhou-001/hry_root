/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:53:14
 */
package hry.activiti.process.service.impl;

import hry.activiti.process.model.ProDefine;
import hry.activiti.process.model.ProDefineDocument;
import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.model.ProDocument;
import hry.activiti.process.service.ProDefineDocumentService;
import hry.activiti.process.service.ProDefineNodeService;
import hry.activiti.process.service.ProDefineService;
import hry.activiti.process.service.ProDocumentService;
import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> ProDefineDocumentService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:53:14
 */
@Service("proDefineDocumentService")
public class ProDefineDocumentServiceImpl extends BaseServiceImpl<ProDefineDocument, Long> implements ProDefineDocumentService {

	@Resource(name = "proDefineDocumentDao")
	@Override
	public void setDao (BaseDao<ProDefineDocument, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private ProDefineService proDefineService;

	@Autowired
	private ProDefineNodeService proDefineNodeService;

	@Autowired
	private ProDocumentService proDocumentService;

	@Override
	public JsonResult saveByFlow(Long defineId, String nodeKey, ProDefineDocument document) {


		ProDefine proDefine = proDefineService.get(defineId);
		if(proDefine==null){
			return  new JsonResult().setMsg("流程定义不存在");
		}

		//查询节点
		ProDefineNode node =proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
		if(node==null){
			return  new JsonResult().setMsg("流程节点不存在");
		}

		//判断是否添加过，添加过不添加
		ProDefineDocument defineDocument = get(new QueryFilter(ProDefineDocument.class).addFilter("nodeId=", node.getId()).addFilter("fileKey=", document.getFileKey()));
		if(defineDocument!=null){
			return new JsonResult().setSuccess(true);
		}

		ProDocument proDocument = proDocumentService.get(new QueryFilter(ProDocument.class).addFilter("fileKey=", document.getFileKey()));
		if(proDocument==null){
			return  new JsonResult().setMsg("fileKey错误");
		}

		document.setName(proDocument.getName());
		document.setNodeId(node.getId());
		save(document);

		return new JsonResult().setSuccess(true);
	}

	@Override
	public JsonResult deleteByFlow(Long defineId, String nodeKey, String fileKey) {
		ProDefine proDefine = proDefineService.get(defineId);
		if(proDefine==null){
			return  new JsonResult().setMsg("流程定义不存在");
		}

		//查询节点
		ProDefineNode node =proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
		if(node==null){
			return  new JsonResult().setMsg("流程节点不存在");
		}

		delete(new QueryFilter(ProDefineDocument.class).addFilter("nodeId=",node.getId()).addFilter("fileKey=",fileKey));

		return new JsonResult().setSuccess(true);
	}

	@Override
	public List<ProDefineDocument> findByNodeId(Long nodeId) {

		return find(new QueryFilter(ProDefineDocument.class).addFilter("nodeId=",nodeId));
	}

	@Override
	public JsonResult setReuqire(Long defineId, String nodeKey, String fileKey, Integer isRequire) {
		ProDefine proDefine = proDefineService.get(defineId);
		if(proDefine==null){
			return  new JsonResult().setMsg("流程定义不存在");
		}

		//查询节点
		ProDefineNode node =proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
		if(node==null){
			return  new JsonResult().setMsg("流程节点不存在");
		}

		ProDefineDocument defineDocument = get(new QueryFilter(ProDefineDocument.class).addFilter("nodeId=", node.getId()).addFilter("fileKey=", fileKey));
		defineDocument.setIsRequire(isRequire);
		update(defineDocument);

		return new JsonResult().setSuccess(true);
	}

	@Override
	public JsonResult setViewModel(Long defineId, String nodeKey, String fileKey, Integer viewModel) {
		ProDefine proDefine = proDefineService.get(defineId);
		if(proDefine==null){
			return  new JsonResult().setMsg("流程定义不存在");
		}

		//查询节点
		ProDefineNode node =proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
		if(node==null){
			return  new JsonResult().setMsg("流程节点不存在");
		}

		ProDefineDocument defineDocument = get(new QueryFilter(ProDefineDocument.class).addFilter("nodeId=", node.getId()).addFilter("fileKey=", fileKey));
		defineDocument.setViewModel(viewModel);
		update(defineDocument);

		return new JsonResult().setSuccess(true);
	}
}
