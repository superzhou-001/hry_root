/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:03:52 
 */
package hry.scm.project.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.scm.project.model.MortgageProject;

import java.util.Map;

/**
 * <p> MortgageProjectService </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:03:52 
 */
public interface MortgageProjectService extends BaseService<MortgageProject, Long> {

    /**
     * 查询订单流程信息
     * @param projectId
     * @param type:apply-申请,aduit-审核
     * @return
     */
    Map<String,Object> getProjectProcessInfo(Long projectId,String type);

    public PageResult listByStatusAndRole(QueryFilter filter, String role, Integer projectStatus);
    /**
     * 生成项目编号
     * @return
     */
    public String createNumber();

    /**
     * 融资申请处理方法
     * @param map
     * @return
     */
    public JsonResult apply(Map<String, String> map);

    /**
     * 融资申请订单处理下一步
     * @param map
     * @return
     */
    public JsonResult nextStep(Map<String, String> map);

    /**
     * 确认质押审批-保存质押物明细
     * @param map
     * @return
     */
    public JsonResult confirmMortgage(Map<String, String> map);

    /**
     * 修改质押物价格
     * @param map
     * @return
     */
    public JsonResult changePrice(Map<String, String> map);

    /**
     * 定时器修改项目状态
     * @return
     */
    public void updateMortgageStatus();


    /**
     * 生成补款记录编号
     * @return
     */
    public String createIncreaseNumber(Long projectId);




}
