/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:54:29
 */
package hry.activiti.process.service;

import hry.activiti.process.model.ProDocument;
import hry.core.mvc.service.BaseService;

import java.util.List;

/**
 * <p> ProDocumentService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:54:29
 */
public interface ProDocumentService extends BaseService<ProDocument, Long> {

    List<ProDocument> findByNode(Long defineId, String nodeKey);
}
