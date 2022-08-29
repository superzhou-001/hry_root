/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:43:10 
 */
package hry.scm.file.service.impl;

import com.alibaba.fastjson.JSONArray;
import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.scm.file.model.ScmFile;
import hry.scm.file.service.ScmFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p> ScmFileService </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:43:10 
 */
@Service("scmFileService")
public class ScmFileServiceImpl extends BaseServiceImpl<ScmFile, Long> implements ScmFileService {

	@Resource(name = "scmFileDao")
	@Override
	public void setDao (BaseDao<ScmFile, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<ScmFile> findList(String bsKey,Long businessId) {
		if(StringUtils.isEmpty(bsKey)){
			return null;
		}
		if(StringUtils.isEmpty(businessId)){
			return null;
		}
		QueryFilter filter = new QueryFilter(ScmFile.class);
		filter.addFilter("bsKey=",bsKey);
		filter.addFilter("businessId=",businessId);
		filter.addFilter("isDelete=",0);
		return this.find(filter);
	}

	@Override
	public JsonResult saveFiles(String bsKey,Long businessId,String jsonStr) {
		JsonResult jsonResult = new JsonResult();
		try {
			ScmFile scmFile = null;
			List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(jsonStr);
			for(Map<String,String> mapList : listObjectFir){
				scmFile = new ScmFile();
				for (Map.Entry entry : mapList.entrySet()){
					if(entry.getKey().equals("id")){
						if(!StringUtils.isEmpty(entry.getValue())){
							scmFile.setId(Long.valueOf(entry.getValue().toString()));
						}
					}
					if(entry.getKey().equals("fileUrl")){
						if(!StringUtils.isEmpty(entry.getValue())){
							scmFile.setFileUrl(entry.getValue().toString());
						}
					}
					if(entry.getKey().equals("fileType")){
						if(!StringUtils.isEmpty(entry.getValue())){
							scmFile.setFileType(entry.getValue().toString());
						}
					}
					if(entry.getKey().equals("oldFileName")){
						if(!StringUtils.isEmpty(entry.getValue())){
							scmFile.setOldFileName(entry.getValue().toString());
						}
					}

					if(entry.getKey().equals("newFileName")){
						if(!StringUtils.isEmpty(entry.getValue())){
							scmFile.setNewFileName(entry.getValue().toString());
						}
					}
					/*if(entry.getKey().equals("isDelete")){
						if(!StringUtils.isEmpty(entry.getValue())){
							scmFile.setIsDelete(Integer.valueOf(entry.getValue().toString()));
						}
					}*/

					System.out.println( entry.getKey()  + "  " +entry.getValue());

				}
				scmFile.setBsKey(bsKey);
				scmFile.setBusinessId(businessId);
				if(StringUtils.isEmpty(scmFile.getId())){
					this.save(scmFile);
				}else{
					this.update(scmFile);
				}
			}
			jsonResult.setSuccess(true).setMsg("处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setSuccess(false).setMsg("文件json数组解析出错");
		}

		return jsonResult;
	}
}
