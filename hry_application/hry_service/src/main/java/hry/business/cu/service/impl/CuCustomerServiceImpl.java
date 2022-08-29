/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:12:26
 */
package hry.business.cu.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.dao.CuCustomerDao;
import hry.business.cu.dao.CuEnterpriseDao;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.service.CuCustomerService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.util.PasswordHelper;
import hry.util.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> CuCustomerService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:12:26
 */
@Service("cuCustomerService")
public class CuCustomerServiceImpl extends BaseServiceImpl<CuCustomer, Long> implements CuCustomerService {

    @Resource(name = "cuCustomerDao")
    @Override
    public void setDao(BaseDao<CuCustomer, Long> dao) {
        super.dao = dao;
    }

    @Override
    public CuCustomer checkMobile(String mobile) {
        QueryFilter filter = new QueryFilter(CuCustomer.class);
        filter.addFilter("mobile=", mobile);
        return this.get(filter);
    }

    @Override
    public JsonResult regist(String nickName, String userName, String password, String email, String mobile) {

        //查询用户是否存在
        CuCustomer cuCustomer = this.checkMobile(mobile);
        if (cuCustomer != null) {
            return new JsonResult().setMsg("用户已存在");
        }
        //获取盐值
        String salt = UUIDUtil.getUUID();
        //密码加密
        String pwd = PasswordHelper.md5(password, salt);

        cuCustomer = new CuCustomer();
        //cuCustomer.setUsername();
        cuCustomer.setStatus(1);
        cuCustomer.setMobile(mobile);
        cuCustomer.setNickname(nickName);
        cuCustomer.setEmail(email);
        cuCustomer.setPassword(pwd);
        cuCustomer.setSalt(salt);
        this.save(cuCustomer);

        return new JsonResult().setObj(cuCustomer.getId()).setSuccess(true).setMsg("注册成功");
    }

    @Override
    public JsonResult updatePassword(Long customerId, String oldPassword, String newPassword) {
        //查询用户信息
        CuCustomer cu = this.get(customerId);
        try {
            if (cu == null) {
                return new JsonResult().setMsg("用户信息不存在");
            }

            //密码加密
            String oldpwd = PasswordHelper.md5(oldPassword, cu.getSalt());
            //验证密码是否与数据库密码相同
            if (!oldpwd.equals(cu.getPassword())) {
                return new JsonResult().setMsg("密码不正确");
            }

            //新密码加密
            String newpwd = PasswordHelper.md5(newPassword, cu.getSalt());
            CuCustomer cuCustomer = new CuCustomer();
            cuCustomer.setId(cu.getId());
            cuCustomer.setPassword(newpwd);
            this.update(cuCustomer);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().setSuccess(false).setMsg("修改失败");
        }
        return new JsonResult().setSuccess(true).setMsg("修改成功");
    }

    @Override
    public Boolean getCustomerByMobile(String mobile) {
        QueryFilter filter = new QueryFilter(CuCustomer.class);
        filter.or("mobile=", mobile);
        CuCustomer cu = this.get(filter);
        if(cu==null){
            return true;
        }
        return false;
    }

    @Override
    public Boolean getCustomerByEmail(String email) {
        QueryFilter filter = new QueryFilter(CuCustomer.class);
        filter.or("email=", email);
        CuCustomer cu = this.get(filter);
        if(cu==null){
            return true;
        }
        return false;
    }

    @Override
    public JsonResult updatePasswordByMobile(String mobile, String newPassword) {
        //查询用户信息
        QueryFilter filter = new QueryFilter(CuCustomer.class);
        filter.addFilter("mobile=",mobile);
        CuCustomer cu = this.get(filter);
        try {
            if (cu == null) {
                return new JsonResult().setMsg("用户信息不存在");
            }

            //新密码加密
            String newpwd = PasswordHelper.md5(newPassword, cu.getSalt());
            CuCustomer cuCustomer = new CuCustomer();
            cuCustomer.setId(cu.getId());
            cuCustomer.setPassword(newpwd);
            this.update(cuCustomer);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().setSuccess(false).setMsg("修改失败");
        }
        return new JsonResult().setSuccess(true).setMsg("修改成功");
    }

    @Override
    public PageResult findCustomerNotEnterprise(QueryFilter filter) {
        Page<CuCustomer> page = PageFactory.getPage(filter);
        Map<String, Object> map = new HashMap<String, Object>();
        ((CuCustomerDao) dao).findCustomerNotEnterprise(map);
        return new PageResult(page, filter.getPage(), filter.getPageSize());
    }
}
