/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:07
 */
package hry.activiti.process.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.activiti.process.model.ProDefineForm;

import java.util.List;

/**
 * <p> ProDefineFormService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:07
 */
public interface ProDefineFormService extends BaseService<ProDefineForm, Long> {

    JsonResult saveByFlow(Long defineId, String nodeKey, ProDefineForm form);

    JsonResult deleteByFlow(Long defineId, String nodeKey, Long formId, Integer type);

    List<ProDefineForm> findMainByNodeId(Long id);

    List<ProDefineForm> findSonByNodeId(Long id);

    JsonResult setEdit(Long defineId, String nodeKey, Long formId, Integer type, Integer isEdit);

    /**
     * 查询全部子表单key
     * @param nodeKey
     * @param pdKey
     * @param version
     * @return
     */
    String getAllSonKey(String nodeKey, String pdKey, Integer version);
}
