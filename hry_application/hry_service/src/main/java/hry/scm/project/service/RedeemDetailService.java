/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-24 11:17:34 
 */
package hry.scm.project.service;

import hry.core.mvc.service.BaseService;
import hry.scm.project.model.RedeemDetail;
import hry.scm.project.model.vo.RedeemDetailVo;

import java.util.List;
import java.util.Map;

/**
 * <p> RedeemDetailService </p>
 *
 * @author: luyue
 * @Date: 2020-07-24 11:17:34 
 */
public interface RedeemDetailService extends BaseService<RedeemDetail, Long> {

    /**
     * 解除质押物事查询清单方法
     * @return
     */
    public List<RedeemDetailVo> findAlldetailBySql(Map<String,String> map);

}
