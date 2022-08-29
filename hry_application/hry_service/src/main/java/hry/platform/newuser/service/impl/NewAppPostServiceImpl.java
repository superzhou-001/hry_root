/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:11:00 
 */
package hry.platform.newuser.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.newuser.model.NewAppPost;
import hry.platform.newuser.service.NewAppPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> NewAppPostService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:11:00 
 */
@Service("newAppPostService")
public class NewAppPostServiceImpl extends BaseServiceImpl<NewAppPost, Long> implements NewAppPostService {

	@Resource(name = "newAppPostDao")
	@Override
	public void setDao (BaseDao<NewAppPost, Long> dao) {
		super.dao = dao;
	}

}
