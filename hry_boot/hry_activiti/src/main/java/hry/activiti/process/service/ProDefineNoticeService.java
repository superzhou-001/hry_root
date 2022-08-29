/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:56
 */
package hry.activiti.process.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.activiti.process.model.ProDefineNotice;

/**
 * <p> ProDefineNoticeService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:56
 */
public interface ProDefineNoticeService extends BaseService<ProDefineNotice, Long> {

    JsonResult saveByFlow(Long defineId, String nodeKey, ProDefineNotice proDefineNotice);
}
