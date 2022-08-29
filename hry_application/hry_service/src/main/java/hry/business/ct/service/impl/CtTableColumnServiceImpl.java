/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:23:51 
 */
package hry.business.ct.service.impl;

import hry.bean.JsonResult;
import hry.business.ct.dao.CtTableColumnDao;
import hry.business.ct.model.CtTable;
import hry.business.ct.service.CtTableService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.ct.model.CtTableColumn;
import hry.business.ct.service.CtTableColumnService;
import hry.core.util.QueryFilter;
import hry.util.SpringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * <p> CtTableColumnService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:23:51 
 */
@Service("ctTableColumnService")
public class CtTableColumnServiceImpl extends BaseServiceImpl<CtTableColumn, Long> implements CtTableColumnService {

	@Resource(name = "ctTableColumnDao")
	@Override
	public void setDao (BaseDao<CtTableColumn, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult addFieldAll(Long tableId) {
		JsonResult jsonResult = new JsonResult();
		try {
			//查询表信息
			CtTableService ctTableService = SpringUtil.getBean("ctTableService");
			CtTable ctTable = ctTableService.get(tableId);

			//查询表中所有字段
			List<CtTableColumn> ctTableColumnList = ((CtTableColumnDao) dao).findFieldByTableName(ctTable.getTableTable());

			QueryFilter filter = new QueryFilter(CtTableColumn.class);
			filter.addFilter("tableId=",tableId);
			List<CtTableColumn> ctTableColumns = this.find(filter);

			Iterator<CtTableColumn> it_b=ctTableColumnList.iterator();
			while(it_b.hasNext()){
				CtTableColumn ctTableColumn=it_b.next();
				//查询当前字段是否已经配置过
				if(ctTableColumns!=null&&ctTableColumns.size()>0){
					for (CtTableColumn tableColumn : ctTableColumns) {
						if(tableColumn.getTableColumn().equals(ctTableColumn.getTableColumn())){
							it_b.remove();
							break;
						}else {
							ctTableColumn.setIsOpen(1);
							ctTableColumn.setTableId(ctTable.getId());
						}
					}
				}
			}
			if(ctTableColumnList.size()>0){
				this.saveAll(ctTableColumnList);
			}
			return jsonResult.setSuccess(true).setMsg("成功");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonResult.setSuccess(false).setMsg("失败");
	}
}
