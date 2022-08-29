/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-28 16:12:18 
 */
package hry.platform.config.service.impl;

import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.config.model.YzyConfig;
import hry.platform.config.model.vo.YzyProductVo;
import hry.platform.config.service.AppConfigService;
import hry.platform.config.service.YzyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> YzyConfigService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-28 16:12:18 
 */
@Service("yzyConfigService")
public class YzyConfigServiceImpl extends BaseServiceImpl<YzyConfig, Long> implements YzyConfigService {

	@Resource(name = "yzyConfigDao")
	@Override
	public void setDao (BaseDao<YzyConfig, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private AppConfigService appConfigService;

	@Override
	public YzyConfig getYzyConfigByProductCode(String productCode) {
		QueryFilter filter = new QueryFilter(YzyConfig.class);
		filter.addFilter("productCode=",productCode);
		return super.get(filter);
	}

	@Override
	public List<YzyConfig> findYzyConfigByProductClassify(Integer productClassify) {
		QueryFilter filter = new QueryFilter(YzyConfig.class);
		filter.addFilter("productClassify=",productClassify);
		filter.addFilter("isOpen=",1);
		return super.find(filter);
	}

	@Override
	public JsonResult updateYzyProduct() {
		JsonResult jsonResult = new JsonResult();

		String ipAdrees = appConfigService.getValueByKey("yzyRequestUrl");
		String uri = "/api/sys/getOpenInterface";
		//商户号
		String companyCode = appConfigService.getValueByKey("companyCode");

		RestTemplate restTemplate = new RestTemplate();
		//请求地址
		String url = ipAdrees+uri;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("companyCode",companyCode);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
		//System.out.println(response.getBody());
		if(response.getStatusCodeValue()!=200){
			return jsonResult.setMsg("接口请求失败");
		}
		JSONObject result = JSONObject.parseObject(response.getBody());
		List<YzyProductVo> list = JSONObject.parseArray(result.getString("jsonObject"),YzyProductVo.class);
		if(list.size()<=0){
			return jsonResult.setMsg("获取信息为空");
		}
		//查询数据库信息
		List<YzyConfig> all = super.findAll();
		if(list.size()<=0){
			return jsonResult.setMsg("数据库信息为空");
		}
		list.forEach(product->{
			all.forEach(yzyConfig -> {
				if(yzyConfig.getUuid().equals(product.getId())){
					yzyConfig.setRemainingnumber(product.getRemainingnumber());
					yzyConfig.setTotalnumber(product.getTotalnumber());
					update(yzyConfig);
				}
			});
		});

		return jsonResult.setSuccess(true).setMsg("成功");
	}
}
