/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 18:34:06
 */
package hry.activiti.process.service;

import hry.activiti.process.model.ProSonForm;
import hry.core.mvc.service.BaseService;

import java.util.List;

/**
 * <p> ProSonFormService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 18:34:06
 */
public interface ProSonFormService extends BaseService<ProSonForm, Long> {

    List<ProSonForm> findByNode(Long mainId, Long defineId, String nodeKey);
}
