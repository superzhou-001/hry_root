/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:54:29
 */
package hry.activiti.process.service.impl;

import hry.activiti.process.model.*;
import hry.activiti.process.service.ProDefineDocumentService;
import hry.activiti.process.service.ProDefineNodeService;
import hry.activiti.process.service.ProDefineService;
import hry.activiti.process.service.ProDocumentService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> ProDocumentService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:54:29
 */
@Service("proDocumentService")
public class ProDocumentServiceImpl extends BaseServiceImpl<ProDocument, Long> implements ProDocumentService {

    @Resource(name = "proDocumentDao")
    @Override
    public void setDao(BaseDao<ProDocument, Long> dao) {
        super.dao = dao;
    }

    @Autowired
    private ProDefineService proDefineService;

    @Autowired
    private ProDefineNodeService proDefineNodeService;

    @Autowired
    private ProDefineDocumentService proDefineDocumentService;


    @Override
    public List<ProDocument> findByNode(Long defineId, String nodeKey) {
        List<ProDocument> allList = find(new QueryFilter(ProDocument.class).addFilter("isEnable=", 1));

        //返回的list
        List<ProDocument> result = new ArrayList<>();

        if (allList != null && allList.size() > 0) {
            ProDefine proDefine = proDefineService.get(defineId);
            ProDefineNode node = proDefineNodeService.get(new QueryFilter(ProDefineNode.class).addFilter("defineKey=", proDefine.getDefineKey()).addFilter("nodeKey=", nodeKey).addFilter("version=", proDefine.getVersion()));
            if (node != null) {
                List<ProDefineDocument> nodeList = proDefineDocumentService.findByNodeId(node.getId());

                if (nodeList != null && nodeList.size() > 0) {

                    for (ProDefineDocument defineDocument : nodeList) {

                        for (ProDocument document : allList) {
                            //判断材料是否包含流程材料
                            if (document.getFileKey().equals(defineDocument.getFileKey())) {
                                document.setIsRequire(defineDocument.getIsRequire());
                                document.setViewModel(defineDocument.getViewModel());
                                result.add(document);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}
