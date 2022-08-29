/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:12:26 
 */
package hry.business.cu.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.business.cu.model.CuCustomer;
import hry.core.util.QueryFilter;

import java.util.List;
import java.util.Map;

/**
 * <p> CuCustomerService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:12:26 
 */
public interface CuCustomerService extends BaseService<CuCustomer, Long> {

    /**
     * 验证用户是否存在
     * @param mobile
     * @return
     */
    CuCustomer checkMobile(String mobile);

    /**
     * 功能描述: 用户注册
     * @auther: yaozh
     * @date: 2020/4/27 17:28
     */
    JsonResult regist(String nickName, String userName, String password, String email, String mobile);
    /**
     * 功能描述: 修改密码
     * @param null
     * @return:
     * @auther: yaozh
     * @date: 2020/4/28 11:40
     */
    JsonResult updatePassword(Long customerId,String oldPassword, String newPassword);

    /**
     * 查看手机号是否绑定过
     * @param mobile
     * @return
     */
    Boolean getCustomerByMobile(String mobile);
    /**
     * 查看邮箱是否绑定过
     * @param email
     * @return
     */
    Boolean getCustomerByEmail(String email);

    /**
     * 根据手机号修改密码
     * @param mobile
     * @param newPassword
     * @return
     */
    JsonResult updatePasswordByMobile(String mobile,String newPassword);

    /**
     * 查询没有绑定企业的用户
     * @param filter
     * @return
     */
    PageResult findCustomerNotEnterprise(QueryFilter filter);

}
