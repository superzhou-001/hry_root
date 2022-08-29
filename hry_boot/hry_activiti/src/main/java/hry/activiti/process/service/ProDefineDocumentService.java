/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:53:14
 */
package hry.activiti.process.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.activiti.process.model.ProDefineDocument;

import java.util.List;

/**
 * <p> ProDefineDocumentService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:53:14
 */
public interface ProDefineDocumentService extends BaseService<ProDefineDocument, Long> {

    JsonResult saveByFlow(Long defineId, String nodeKey, ProDefineDocument document);

    JsonResult deleteByFlow(Long defineId, String nodeKey, String fileKey);

    List<ProDefineDocument> findByNodeId(Long nodeId);

    JsonResult setReuqire(Long defineId, String nodeKey, String fileKey, Integer isRequire);

    JsonResult setViewModel(Long defineId, String nodeKey, String fileKey, Integer viewModel);
}
