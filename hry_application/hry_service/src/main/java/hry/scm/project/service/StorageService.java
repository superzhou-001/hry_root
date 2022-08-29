/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-10 11:34:53 
 */
package hry.scm.project.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.scm.project.model.Storage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p> StorageService </p>
 *
 * @author: luyue
 * @Date: 2020-07-10 11:34:53 
 */
public interface StorageService extends BaseService<Storage, Long> {
    /**
     * 导入仓库存储数据
     * @param file
     * @return
     */
    public JsonResult  importExcel(MultipartFile file) throws IOException;

    /**
     * 质押物清单查询质押物详细信息
     * @param map
     * @return
     */
    public List<Storage> findStorage(Map<String,String> map);

}
