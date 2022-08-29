/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:42:17
 */
package hry.activiti.process.service.impl;

import hry.activiti.process.model.ProDefine;
import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.model.ProDefinePerson;
import hry.activiti.process.service.ActivitiService;
import hry.activiti.process.service.ProDefineNodeService;
import hry.activiti.process.service.ProDefinePersonService;
import hry.activiti.process.service.ProDefineService;
import hry.bean.JsonResult;
import hry.config.remote.AdminRemoteServiceUtil;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> ProDefinePersonService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:42:17
 */
@Service("proDefinePersonService")
public class ProDefinePersonServiceImpl extends BaseServiceImpl<ProDefinePerson, Long> implements ProDefinePersonService {

    @Resource(name = "proDefinePersonDao")
    @Override
    public void setDao(BaseDao<ProDefinePerson, Long> dao) {
        super.dao = dao;
    }

    @Autowired
    private ProDefineService proDefineService;

    @Autowired
    private ProDefineNodeService proDefineNodeService;

    @Autowired
    AdminRemoteServiceUtil adminRemoteServiceUtil;

    @Autowired
    ActivitiService activitiService;

    @Override
    public JsonResult saveByFlow(Long defineId, String nodeKey, ProDefinePerson proDefinePerson) {

        ProDefine proDefine = proDefineService.get(defineId);
        if (proDefine == null) {
            return new JsonResult().setMsg("流程定义不存在");
        }

        //查询节点
        ProDefineNode node = proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
        if (node == null) {
            return new JsonResult().setMsg("流程节点不存在");
        }

        //删除节点全部人员配置
        delete(new QueryFilter(ProDefinePerson.class).addFilter("nodeId=", node.getId()));

        proDefinePerson.setNodeId(node.getId());
        save(proDefinePerson);

        return new JsonResult().setSuccess(true);
    }

    @Override
    public String getAssigneeUserId(String piId,ProDefineNode node) {
        ProDefinePerson person = get(new QueryFilter(ProDefinePerson.class).addFilter("nodeId=", node.getId()));

        String userId  = getPersonInfo(person)[0];
        if("startUserId".equals(userId)){
            Long id = (Long)activitiService.getVariable(piId,userId);
            return  id.toString() ;
        }

        return userId;

    }

    /**
     * 根据person配置信息查询对应的ID和名称
     * @param person
     * @return
     */
    private String[] getPersonInfo(ProDefinePerson person){
        String userId = "";
        String personName = "";
        if (person != null) {
            //发起人
            if(ProDefinePerson.PERSONTYPE_1 == person.getPersonType()){
                userId = "startUserId";
            }
            //指定人、会签人员
            if (ProDefinePerson.PERSONTYPE_2 == person.getPersonType()||ProDefinePerson.PERSONTYPE_5 == person.getPersonType()) {
                //如果有多个批量处理
                personName = adminRemoteServiceUtil.getUserName(person.getPersonValue());
                userId  =  person.getPersonValue();
            }
            //人员组
            if (ProDefinePerson.PERSONTYPE_3 == person.getPersonType()) {
                if (person.getPersonValue().contains(",")) {


                } else {

                }
            }
        }

        return new String[]{userId,personName};
    }

    @Override
    public String getPersonName(ProDefinePerson person) {
        return getPersonInfo(person)[1];
    }

    @Override
    public void saveMultiPerson(Long nodeId, String personValue) {
        //删除节点全部人员配置
        delete(new QueryFilter(ProDefinePerson.class).addFilter("nodeId=", nodeId));

        ProDefinePerson person = new ProDefinePerson();
        person.setPersonType(ProDefinePerson.PERSONTYPE_5);
        person.setNodeId(nodeId);
        person.setPersonValue(personValue);
        save(person);
    }
}
