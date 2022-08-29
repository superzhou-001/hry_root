/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 18:33:52
 */
package hry.activiti.process.service;

import hry.core.mvc.service.BaseService;
import hry.activiti.process.model.ProMainForm;

import java.util.List;

/**
 * <p> ProMainFormService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 18:33:52
 */
public interface ProMainFormService extends BaseService<ProMainForm, Long> {

    /**
     * 查询主表单--回显流程节点主表单
     * @param defineId
     * @param nodeKey
     * @return
     */
    List<ProMainForm> findByNode(Long defineId, String nodeKey);
}
