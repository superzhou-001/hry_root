/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:41:09 
 */
package hry.scm.enterprise.service;

import hry.bean.JsonResult;
import hry.business.cu.model.CuCustomer;
import hry.core.mvc.service.BaseService;
import hry.scm.enterprise.model.ScmEnterprise;
import hry.scm.enterprise.model.UserRelation;

import java.util.List;

/**
 * <p> UserRelationService </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:41:09 
 */
public interface UserRelationService extends BaseService<UserRelation, Long> {
    /**
     * 查询用户关系集合
     * @param cuCustomer 用户
     */
    public List<UserRelation> getRelationList(CuCustomer cuCustomer);
    /**
     * 保存用户关系
     * @param customerId 注册用户Id
     * @param infoId 详细主体id
     * @param userType 1:采购方，2:资金方,3:供应商
     * @param role 1:管理员（自己创建的企业），2:业务员（别人授权的企业）
     */
    public void saveUserRelation(Long customerId,Long infoId,Integer userType,Integer role);

    /**
     * 查询企业管理员
     * @param infoId 企业Id
     * @param userType 1:采购方，2:资金方,3:供应商
     * @return
     */
    public CuCustomer getCustomer(Long infoId,Integer userType);

    /**
     * 查询用户关系
     * @param customerId 注册用户Id
     * @param infoId 详细主体id
     * @return
     */
    public UserRelation getRelation(Long customerId,Long infoId);


}
