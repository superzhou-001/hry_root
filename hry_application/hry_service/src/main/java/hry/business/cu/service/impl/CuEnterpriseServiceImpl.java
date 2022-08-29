/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:18:39
 */
package hry.business.cu.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.dao.CuEnterpriseDao;
import hry.business.cu.model.*;
import hry.business.cu.service.*;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.newuser.service.NewAppUserService;
import hry.security.jwt.JWTContext;
import hry.util.date.DateUtil;
import hry.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> CuEnterpriseService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:18:39
 */
@Service("cuEnterpriseService")
public class CuEnterpriseServiceImpl extends BaseServiceImpl<CuEnterprise, Long> implements CuEnterpriseService {

    @Resource(name = "cuEnterpriseDao")
    @Override
    public void setDao(BaseDao<CuEnterprise, Long> dao) {
        super.dao = dao;
    }

    @Autowired
    private CuEnterprisePersonService cuEnterprisePersonService;
    @Autowired
    private CuEnterpriseUserService cuEnterpriseUserService;
    @Autowired
    private CuPersonService cuPersonService;
    @Autowired
    private CuBankService cuBankService;
    @Autowired
    private NewAppUserService newAppUserService;

    @Override
    public PageResult findPageBySql(QueryFilter filter) {
        // ----------------------分页查询头部外壳------------------------------
        Page<CuEnterprise> page = PageFactory.getPage(filter);
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        if (user == null) {
            return null;
        }

        String modified_s = filter.getRequest().getParameter("created_GT");
        String modified_e = filter.getRequest().getParameter("created_LT");
        String status = filter.getRequest().getParameter("status");
        String username = filter.getRequest().getParameter("username");
        String mobile = filter.getRequest().getParameter("mobile");
        String enterpriseName = filter.getRequest().getParameter("enterpriseName");
        String enterpriseType = filter.getRequest().getParameter("enterpriseType");
        String creditCode = filter.getRequest().getParameter("creditCode");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", user.getId());
        if (!StringUtils.isEmpty(enterpriseType)) {
            map.put("enterpriseType", enterpriseType);
        }
        if (!StringUtils.isEmpty(status)) {
            map.put("status", status);
        }
        if (!StringUtils.isEmpty(mobile)) {
            map.put("mobile", "%" + mobile + "%");
        }
        if (!StringUtils.isEmpty(creditCode)) {
            map.put("creditCode", "%" + creditCode + "%");
        }
        if (!StringUtils.isEmpty(enterpriseName)) {
            map.put("enterpriseName", "%" + enterpriseName + "%");
        }
        if (!StringUtils.isEmpty(username)) {
            map.put("username", "%" + username + "%");
        }
        if (!StringUtils.isEmpty(modified_s)) {
            map.put("modified_s", modified_s);
        }
        if (!StringUtils.isEmpty(modified_e)) {
            map.put("modified_e", modified_e);
        }
        ((CuEnterpriseDao) dao).findPageBySql(map);
        return new PageResult(page, filter.getPage(), filter.getPageSize());
    }

    @Override
    public void exportExcelEnterprise(HttpServletRequest request, HttpServletResponse response) {
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        //NewAppUser user = newAppUserService.get(Long.valueOf((String)request.getParameter("userId")));
        if (user == null) {
            return;
        }

        //获取数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", user.getId());
        List<CuEnterprise> list = ((CuEnterpriseDao) dao).findPageBySql(map);

        //excel标题
        String[] title = {"id", "状态", "来源", "企业类型", "公司名称", "注册号",
                "登记机关", "法人名", "成立日期", "吊销日期", "省份", "更新日期",
                "社会统一信用代码", "注册资本", "地址", "经营范围",
                "营业开始日期", "营业结束日期", "发照日期", "是否IPO上市",
                "上市公司代码", "上市类型", "实缴资本",
                "曾用名", "用户名称", "注册手机号"};
        //excel文件名
        String fileName = "企业信息表" + System.currentTimeMillis() + ".xls";
        //sheet名
        String sheetName = "企业信息";

        String[][] content = new String[list.size() + 1][title.length];
        for (int i = 0; i < list.size(); i++) {
            CuEnterprise r = list.get(i);
            System.out.println(r.toString());
            content[i][0] = r.getId() + "";
            content[i][1] = r.getStatus() + "";
            content[i][2] = r.getSource() + "";
            content[i][3] = r.getEnterpriseType() + "";
            content[i][4] = r.getName();
            content[i][5] = r.getNo();
            content[i][6] = r.getBelongOrg();
            content[i][7] = r.getOperName();
            content[i][8] = DateUtil.dateToString(r.getStartDate());
            content[i][9] = DateUtil.dateToString(r.getEndDate());
            content[i][10] = r.getProvince();
            content[i][11] = DateUtil.dateToString(r.getUpdatedDate());
            content[i][12] = r.getCreditCode();
            content[i][13] = r.getRegistCapi();
            content[i][14] = r.getAddress();
            content[i][15] = r.getScope();
            content[i][16] = DateUtil.dateToString(r.getTermStart());
            content[i][17] = DateUtil.dateToString(r.getTeamEnd());
            content[i][18] = DateUtil.dateToString(r.getCheckDate());
            content[i][19] = r.getStockNumber();
            content[i][20] = r.getStockType();
            content[i][21] = r.getRecCap();
            content[i][22] = r.getOriginalName();
            content[i][23] = r.getCustomerName();
            content[i][24] = r.getCustomerMobile();
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JsonResult createEnterprise(CuEnterprise cuEnterprise) {
        JsonResult jsonResult = new JsonResult();
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        if (user != null) {
            cuEnterprise.setUserId(user.getId());
            cuEnterprise.setUserName(user.getUserName());
        } else {
            jsonResult.setSuccess(false).setMsg("登陆用户异常");
        }
        //根据信用代码查询企业是否存在
        QueryFilter filter = new QueryFilter(CuEnterprise.class);
        filter.addFilter("creditCode=", cuEnterprise.getCreditCode());
        CuEnterprise cue = this.get(filter);
        if (cue != null) {
            return jsonResult.setSuccess(false).setMsg("信用代码不能重复");
        }
        cuEnterprise.setStatus(2);
        cuEnterprise.setSource(2);
        this.save(cuEnterprise);
        //添加权限
        CuEnterpriseUser cuEnterpriseUser = new CuEnterpriseUser();
        cuEnterpriseUser.setEnterpriseId(cuEnterprise.getId());
        cuEnterpriseUser.setUserId(user.getId());
        cuEnterpriseUserService.save(cuEnterpriseUser);

        return jsonResult.setSuccess(true).setMsg("成功").setObj(cuEnterprise.getId());
    }

    @Override
    public JsonResult updateEnterprise(CuEnterprise cuEnterprise) {
        JsonResult jsonResult = new JsonResult();
        //根据信用代码查询企业是否存在
        QueryFilter filter = new QueryFilter(CuEnterprise.class);
        filter.addFilter("creditCode=", cuEnterprise.getCreditCode());
        CuEnterprise cue = this.get(filter);
        if (cue != null&&cue.getId()!=cuEnterprise.getId()) {
            return jsonResult.setSuccess(false).setMsg("信用代码不能重复");
        }
        this.update(cuEnterprise);

        //法人信息
        CuPerson legalPerson = JSON.parseObject((String) cuEnterprise.getLegalPerson(), CuPerson.class);
        if (legalPerson != null) {
            //更新法人信息
            this.updateLegalPerson(legalPerson, cuEnterprise.getId());
        }

        return jsonResult.setSuccess(true).setMsg("成功");
    }

    /**
     * @Description:更新法人信息
     * @Date: 2020/5/25 15:03
     */
    private void updateLegalPerson(CuPerson legalPerson, Long enterpriseId) {
        if (!StringUtils.isEmpty(legalPerson.getId())) {
            //id不为空，说明信息是在cu_person表中搜索出来的，直接建立关联
            cuPersonService.update(legalPerson);
            return;
        }
        //先在cu_person表中建立信息，在关联
        cuPersonService.save(legalPerson);
        //查询关联关系
        QueryFilter filterCuEnterprisePerson = new QueryFilter(CuEnterprisePerson.class);
        filterCuEnterprisePerson.addFilter("enterpriseId=", enterpriseId);
        filterCuEnterprisePerson.addFilter("type=", 1);
        CuEnterprisePerson cuEnterprisePerson = cuEnterprisePersonService.get(filterCuEnterprisePerson);
        if (cuEnterprisePerson == null) {
            CuEnterprisePerson cep = new CuEnterprisePerson();
            cep.setEnterpriseId(enterpriseId);
            cep.setPersonId(legalPerson.getId());
            cep.setType(1);//人员类型:1.法人 2.控制人 3.联系人
            cuEnterprisePersonService.save(cep);
        } else {
            cuEnterprisePerson.setPersonId(legalPerson.getId());
            cuEnterprisePersonService.update(cuEnterprisePerson);
        }
    }

    /**
     * @Description: 更新联系人信息
     * @Date: 2020/5/25 15:13
     */
    private void updateContacts(CuPerson contacts, Long enterpriseId) {
        if (!StringUtils.isEmpty(contacts.getId())) {
            //id不为空，说明信息是在cu_person表中搜索出来的，直接建立关联
        } else {
            //先在cu_person表中建立信息，在关联
            cuPersonService.save(contacts);
        }
        //判断是否重复
        QueryFilter filterContacts = new QueryFilter(CuEnterprisePerson.class);
        filterContacts.addFilter("enterpriseId=", enterpriseId);
        filterContacts.addFilter("type=", 3);
        filterContacts.addFilter("personId=", contacts.getId());
        CuEnterprisePerson cuep = cuEnterprisePersonService.get(filterContacts);
        if (cuep == null) {
            CuEnterprisePerson cepc = new CuEnterprisePerson();
            cepc.setEnterpriseId(enterpriseId);
            cepc.setPersonId(contacts.getId());
            cepc.setType(3);//人员类型:1.法人 2.控制人 3.联系人
            cuEnterprisePersonService.save(cepc);
        }
    }


    //发送响应流方法
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
