/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:35:04
 */
package hry.activiti.process.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.activiti.process.model.ProDefine;

/**
 * <p> ProDefineService </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:35:04
 */
public interface ProDefineService extends BaseService<ProDefine, Long> {

    /**
     * 保存并生成节点信息
     * @param proDefine
     * @return
     */
    JsonResult saveAndNode(ProDefine proDefine);

    /**
     * 保存并保存节点信息
     * @param proDefine
     * @param isDelete 是否删除当前版本节点
     * @return
     */
    JsonResult updateAndNode(ProDefine proDefine,boolean isDelete);

    /**
     * 通过流程key查询流程定义
     * @param defineKey
     * @return
     */
    ProDefine getByDefineKey(String defineKey);

    /**
     * 发部流程
     * @param proDefine
     * @return
     */
    JsonResult deploy(ProDefine proDefine);
}
