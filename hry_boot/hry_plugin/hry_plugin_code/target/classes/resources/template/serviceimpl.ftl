/**
 * Copyright: ${HRY_COMPANY}
 *
 * @author: ${codeAuth!}
 * @version: V1.0
 * @Date: ${codeDate!}
 */
package ${HRY_PACKAGE!}.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import ${HRY_PACKAGE!}.model.${HRY_MODEL!};
import ${HRY_PACKAGE!}.service.${HRY_MODEL!}Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> ${HRY_MODEL!}Service </p>
 *
 * @author: ${codeAuth!}
 * @Date: ${codeDate!}
 */
@Service("${HRY_MODEL_SMALL!}Service")
public class ${HRY_MODEL!}ServiceImpl extends BaseServiceImpl<${HRY_MODEL!}, Long> implements ${HRY_MODEL!}Service {

	@Resource(name = "${HRY_MODEL_SMALL!}Dao")
	@Override
	public void setDao (BaseDao<${HRY_MODEL!}, Long> dao) {
		super.dao = dao;
	}

}
