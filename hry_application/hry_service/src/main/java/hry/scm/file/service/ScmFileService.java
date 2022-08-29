/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:43:10 
 */
package hry.scm.file.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.scm.file.model.ScmFile;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STCfType;

import java.util.List;

/**
 * <p> ScmFileService </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:43:10 
 */
public interface ScmFileService extends BaseService<ScmFile, Long> {
    /**
     * 查询未被删除的文件列表
     * @param bsKey 文件类型key值
     * @param businessId 业务表主键
     * @return
     */
    List<ScmFile> findList(String bsKey,Long businessId);

    /**
     * 保存文件集合
     * @param bsKey 文件类型key值
     * @param businessId 业务表主键
     * @param jsonStr 文件josn数组
     * @return
     */
    JsonResult saveFiles(String bsKey,Long businessId,String jsonStr);

}
