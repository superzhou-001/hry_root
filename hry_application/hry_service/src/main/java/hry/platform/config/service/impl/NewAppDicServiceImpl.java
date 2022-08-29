/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-24 10:28:18
 */
package hry.platform.config.service.impl;

import com.alibaba.fastjson.JSON;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.config.model.NewAppDic;
import hry.platform.config.service.NewAppDicService;
import hry.platform.newuser.model.NewAppUser;
import hry.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> NewAppDicService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-24 10:28:18
 */
@Service("newAppDicService")
public class NewAppDicServiceImpl extends BaseServiceImpl<NewAppDic, Long> implements NewAppDicService {

	private final static String SYSLANGUAGE = "sysLanguage";
	private final static String AREA_CACHE = "areaCache";

	@Autowired
	private RedisService redisService;

	@Resource(name = "newAppDicDao")
	@Override
	public void setDao (BaseDao<NewAppDic, Long> dao) {
		super.dao = dao;
	}

	@Override
	public void flushRedis() {
		QueryFilter filter = new QueryFilter(NewAppDic.class);
		filter.addFilter("type=", 2);
		filter.addFilter("pkey!=","addressarea");
		List<NewAppDic> list = find(filter);
		if (list != null && list.size() > 0) {
			list.stream().forEach(newAppDic -> {
				QueryFilter f = new QueryFilter(NewAppDic.class);
				f.addFilter("pkey=", newAppDic.getMkey());
				if (SYSLANGUAGE.equals(newAppDic.getMkey())) {
					// 开启
					f.addFilter("remark2=", "1");
					f.setOrderby("remark1");
				}
				List<NewAppDic> appDics = find(f);
				if (appDics != null && appDics.size() > 0) {
					redisService.hset("new_app_dic", newAppDic.getMkey(), JSON.toJSONString(appDics));
				}
			});
		}
	}

	@Override
	public void initAreaCache() {
		List<Map<String, Object>> areaList = new ArrayList<>();
		QueryFilter filter2 = new QueryFilter(NewAppDic.class);
		filter2.addFilter("type=", 2);
		filter2.addFilter("pkey=", "addressarea");
		List<NewAppDic> list = find(filter2);
		if (list != null && list.size() > 0) {
			list.stream().forEach(newAppDic -> {
				Map<String, Object> pMap = new HashMap<>();
				pMap.put("key", newAppDic.getValue());
				pMap.put("province", newAppDic.getName());
				QueryFilter f = new QueryFilter(NewAppDic.class);
				f.addFilter("pkey=", newAppDic.getMkey());
				List<NewAppDic> appDics = find(f);

				List<Map<String, Object>> cl = new ArrayList<>();
				if (appDics.size() == 0) {
					pMap.put("cities", null);
				} else {
					for (NewAppDic dic : appDics) {
						Map<String, Object> map = new HashMap<>();
						map.put("city", dic.getName());
						cl.add(map);
					}
					pMap.put("cities", cl);
				}
				areaList.add(pMap);
			});
		}
		String data = JSON.toJSONString(areaList);
		redisService.save(AREA_CACHE, data);
	}

	@Override
	public void initDicTree() {
		// 查询根节点
		QueryFilter filter = new QueryFilter(NewAppUser.class);
		filter.addFilter("type=", 1);
		List<NewAppDic> rootList = find(filter);
		if (rootList != null && rootList.size() > 0) {
			for (NewAppDic newAppDic : rootList) {
				createTreeData(newAppDic);
				redisService.hset("new_app_tree_dic", newAppDic.getMkey(), JSON.toJSONString(newAppDic));
			}
		}
	}

	@Override
	public List<NewAppDic> findDicTree() {
		// 查询根节点
		QueryFilter filter = new QueryFilter(NewAppUser.class);
		filter.addFilter("type=", 1);
		List<NewAppDic> rootList = find(filter);
		List<NewAppDic> newAppDicList = new ArrayList<>();
		if (rootList != null && rootList.size() > 0) {
			for (NewAppDic newAppDic : rootList) {
				String result = redisService.hget("new_app_tree_dic", newAppDic.getMkey());
				NewAppDic newDic = JSON.parseObject(result, NewAppDic.class);
				newAppDicList.add(newDic);
			}
		}
		return newAppDicList;
	}

	/**
	 * 递归生成树结构
	 * */
	private void createTreeData(NewAppDic dic) {
		QueryFilter filter = new QueryFilter(NewAppDic.class);
		filter.addFilter("pkey=", dic.getMkey());
		List<NewAppDic> childrenList = find(filter);
		if (childrenList != null && childrenList.size() > 0) {
			dic.setChildren(childrenList);
			for (NewAppDic newAppDic : childrenList) {
				createTreeData(newAppDic);
			}
		}
	}

	@Override
	public void flushTreeRedis(String pkey) {
		String oneKey = this.oneClassKey(pkey);
		// 查询一级父
		QueryFilter filter = new QueryFilter(NewAppUser.class);
		filter.addFilter("mkey=", oneKey);
		NewAppDic rootDic = this.get(filter);
		createTreeData(rootDic);
		redisService.hset("new_app_tree_dic", rootDic.getMkey(), JSON.toJSONString(rootDic));
	}

	/**
	 * 递归查询一级父key
	 * */
	private String oneClassKey(String pkey) {
		QueryFilter filter = new QueryFilter(NewAppDic.class);
		filter.addFilter("mkey=", pkey);
		NewAppDic dic = this.get(filter);
		if (dic != null && !"".equals(dic.getPkey())) {
			return oneClassKey(dic.getPkey());
		}
		return pkey;
	}

	@Override
	public List<NewAppDic> findDicList(String pkey) {
		QueryFilter filter = new QueryFilter(NewAppDic.class);
		filter.addFilter("pkey=", pkey);
		return super.find(filter);
	}
}
