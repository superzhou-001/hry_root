/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:42:17
 */
package hry.activiti.process.service;

import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.model.ProDefinePerson;
import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;

/**
 * <p> ProDefinePersonService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:42:17
 */
public interface ProDefinePersonService extends BaseService<ProDefinePerson, Long> {

    JsonResult saveByFlow(Long defineId, String nodeKey, ProDefinePerson proDefinePerson);

    /**
     * 获取流程处理人
     * @param node
     * @return
     */
    String getAssigneeUserId(String piId,ProDefineNode node);

    /**
     * 查询流程节点配置人显示名称
     * @param person
     * @return
     */
    String getPersonName(ProDefinePerson person);

    /**
     * 保存会签人员配置
     * @param personValue
     */
    void saveMultiPerson(Long nodeId, String personValue);
}
