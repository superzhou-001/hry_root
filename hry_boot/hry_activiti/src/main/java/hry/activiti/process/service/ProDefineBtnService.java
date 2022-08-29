/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:40:57
 */
package hry.activiti.process.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.activiti.process.model.ProDefineBtn;

/**
 * <p> ProDefineBtnService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:40:57
 */
public interface ProDefineBtnService extends BaseService<ProDefineBtn, Long> {

    JsonResult saveByFlow(Long defineId, String nodeKey, ProDefineBtn proDefineBtn);
}
