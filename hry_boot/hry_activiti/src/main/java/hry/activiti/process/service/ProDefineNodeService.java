/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:21
 */
package hry.activiti.process.service;

import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.model.ProProcess;
import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;

import java.util.Map;

/**
 * <p> ProDefineNodeService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:21
 */
public interface ProDefineNodeService extends BaseService<ProDefineNode, Long> {

    /**
     * 查询节点配置
     * @param defineKey
     * @param nodeKey
     * @return
     */
    Map<String,Object> getConfig(String defineKey, String nodeKey);

    /**
     * 查询节点配置，通过流程
     * @param proProcess
     * @return
     */
    Map<String,Object>  getConfigByProcess(String taskId,String nodeKey,ProProcess proProcess);

    /**
     * 保存流程节点配置
     * @param defineId
     * @param nodeKey
     * @param nodeType
     * @param timeType
     * @param timeDay
     * @return
     */
    JsonResult saveByFlow(Long defineId, String nodeKey, Integer nodeType, Integer timeType, Integer timeDay,Integer decidetype,Integer decideValue,String personValue);

    /**
     * 返回流程节点配置的流程处理人
     * @param taskDefinitionKey
     * @param processInstanceId
     * @return
     */
    String findAssigneeUser(String taskDefinitionKey, String processInstanceId);

    /**
     * 获取流程配置节点
     * @param nodeKey  节点key
     * @param defineKey 流程定义key
     * @param version  流程版本号
     * @return
     */
    ProDefineNode getNode(String nodeKey,String defineKey,Integer version);

}
