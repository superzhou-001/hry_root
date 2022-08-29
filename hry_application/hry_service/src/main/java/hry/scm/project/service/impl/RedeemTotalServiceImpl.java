/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-22 17:18:30 
 */
package hry.scm.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.scm.project.dao.RedeemTotalDao;
import hry.scm.project.model.MortgageDetail;
import hry.scm.project.model.MortgageTotal;
import hry.scm.project.model.RedeemDetail;
import hry.scm.project.model.RedeemTotal;
import hry.scm.project.model.vo.RedeemDetailVo;
import hry.scm.project.model.vo.RedeemTotalVo;
import hry.scm.project.service.MortgageDetailService;
import hry.scm.project.service.MortgageTotalService;
import hry.scm.project.service.RedeemDetailService;
import hry.scm.project.service.RedeemTotalService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p> RedeemTotalService </p>
 *
 * @author: luyue
 * @Date: 2020-07-22 17:18:30 
 */
@Service("redeemTotalService")
public class RedeemTotalServiceImpl extends BaseServiceImpl<RedeemTotal, Long> implements RedeemTotalService {

	@Resource(name = "redeemTotalDao")
	@Override
	public void setDao (BaseDao<RedeemTotal, Long> dao) {
		super.dao = dao;
	}
	@Autowired
	public RedeemDetailService redeemDetailService;
	@Autowired
	public MortgageTotalService mortgageTotalService;
	@Autowired
	public MortgageDetailService mortgageDetailService;
	/**
	 * 解除质押物-查询质押物清单方法
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult findDetail(Map<String, String> map) {
		JsonResult result=new JsonResult();
        String id=map.get("id");
        //1、查询当前赎货汇总
		RedeemTotal redeemTotal=this.get(Long.valueOf(id));
		if(null==redeemTotal || "".equals(redeemTotal)){
			result.setSuccess(false).setMsg("该赎回货品不存在，请重新选择");
		}
		map.put("totalId",redeemTotal.getTotalId().toString());
		List<RedeemDetailVo> list=redeemDetailService.findAlldetailBySql(map);
		result.setSuccess(true).setObj(list);
		return result;
	}
	/**
	 * 解除质押物-确认解除货品抵押
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult confirmRelieve(Map<String, String> map) {
		JsonResult result=new JsonResult();
		String id=map.get("id");
		String jsonStr=map.get("jsonStr");
		//1、查询当前赎货汇总
		RedeemTotal redeemTotal=this.get(Long.valueOf(id));
		if(null==redeemTotal || "".equals(redeemTotal)){
			result.setSuccess(false).setMsg("该赎回货品不存在，请重新选择");
			return  result;
		}
		MortgageTotal mt=mortgageTotalService.get(redeemTotal.getTotalId());
		//2、解析json
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		JSONArray tarray = jsonObject.getJSONArray("storage");
		int count=0;//解除质押的总件数
		List<RedeemDetail> list=new ArrayList<>();
		if(!tarray.isEmpty() && tarray.size()>0){
			for(int i=0;i<tarray.size();i++){
				JSONObject tobject = (JSONObject) tarray.get(i);
				RedeemDetail rd= JSON.parseObject(tobject.toString(),RedeemDetail.class);
				count=count+rd.getBackCount();
				list.add(rd);
			}
			//3、解除质押总件数大于企业本次申请赎回件数
			if(count>redeemTotal.getBackCount()){
				result.setSuccess(false).setMsg("解除质押总件数大于企业本次申请赎回件数，请重新选择");
				return  result;
			}
			//4、处理解质押数据
			for(RedeemDetail rd:list){
				//5、查询该数据是否已保存，如果数据库已有，则修改，如无则新增
				QueryFilter qf=new QueryFilter(RedeemDetail.class);
				qf.addFilter("redeemTotalId=",id);
				qf.addFilter("detailId=",rd.getDetailId());
				List<RedeemDetail> rlist=redeemDetailService.find(qf);
				if(null!=rlist && rlist.size()>0){//有则修改
					RedeemDetail detail=rlist.get(0);
					detail.setBackCount(rd.getBackCount());
					detail.setBackWeight(mt.getAveWeight().multiply(new BigDecimal(rd.getBackCount())));
					redeemDetailService.update(detail);
				}else{//无则新保存
					rd.setRedeemId(redeemTotal.getRedeemId());//赎回记录id
					rd.setRedeemTotalId(redeemTotal.getId());//赎回质押物汇总id
					rd.setBackWeight(mt.getAveWeight().multiply(new BigDecimal(rd.getBackCount())));//赎回重量
					MortgageDetail md=mortgageDetailService.get(rd.getDetailId());
					rd.setNumber(md.getNumber());//仓库行列唯一标识
					redeemDetailService.save(rd);
				}
			}
		}
		result.setSuccess(true).setMsg("处理成功");
		return result;
	}

	@Override
	public List<RedeemTotalVo> findRedeemTotalBySql(Map<String, String> map) {
		return ((RedeemTotalDao)dao).findRedeemTotalBySql(map);
	}
}
