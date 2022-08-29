/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:50:03
 */
package hry.business.ct.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.ct.dao.CtContractTemplateDao;
import hry.business.ct.model.CtContractElement;
import hry.business.ct.model.CtContractTemplateElement;
import hry.business.ct.model.CtContractTemplateSeal;
import hry.business.ct.service.CtContractTemplateElementService;
import hry.business.ct.service.CtContractTemplateSealService;
import hry.business.cu.dao.CuEnterpriseDao;
import hry.business.cu.model.CuEnterprise;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.ct.model.CtContractTemplate;
import hry.business.ct.service.CtContractTemplateService;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.security.jwt.JWTContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> CtContractTemplateService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:50:03
 */
@Service("ctContractTemplateService")
public class CtContractTemplateServiceImpl extends BaseServiceImpl<CtContractTemplate, Long> implements CtContractTemplateService {

    @Resource(name = "ctContractTemplateDao")
    @Override
    public void setDao(BaseDao<CtContractTemplate, Long> dao) {
        super.dao = dao;
    }

    private static final double pdfWidth = 595.3;
    private static final double pdfHeight = 841.9;
    private static final int dpi = 100;

    @Autowired
    private CtContractTemplateElementService ctContractTemplateElementService;
    @Autowired
    private CtContractTemplateSealService ctContractTemplateSealService;
    @Autowired
    private CtContractElementServiceImpl ctContractElementService;


    @Override
    public CtContractTemplate getContractTemplateById(Long id) {
        return ((CtContractTemplateDao) dao).getContractTemplateById(id);
    }

    @Override
    public PageResult findPageBySql(QueryFilter filter) {
        // ----------------------分页查询头部外壳------------------------------
        Page<CtContractTemplate> page = PageFactory.getPage(filter);

        String contractTypeId = filter.getRequest().getParameter("contractTypeId");
        String templateName = filter.getRequest().getParameter("templateName");

        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(contractTypeId)) {
            map.put("contractTypeId", contractTypeId);
        }
        if (!StringUtils.isEmpty(templateName)) {
            map.put("templateName", "%" + templateName + "%");
        }
        ((CtContractTemplateDao) dao).findContractTemplate(map);
        return new PageResult(page, filter.getPage(), filter.getPageSize());
    }

    @Override
    public JsonResult updateTemplateAndElement(CtContractTemplate ctContractTemplate) {
        //查询数据库中模板url
        JsonResult jsonResult = new JsonResult();
        try {
            CtContractTemplate ctContractTemplateDB = this.get(ctContractTemplate.getId());
            if (ctContractTemplate.getFileUrl() != null && !ctContractTemplate.getFileUrl().equals("") && !ctContractTemplate.getFileUrl().equals(ctContractTemplateDB.getFileUrl())) {
                //如果两个url不同，说明文件被修改，需要更新
                CtContractTemplate ctC = new CtContractTemplate();
                ctC.setId(ctContractTemplate.getId());
                ctC.setFileUrl(ctContractTemplate.getFileUrl());
                super.update(ctC);
            }
            //根据类型判断修改内容  1.模板元素 2.模板印章
            if(ctContractTemplate.getUpdateType()==1){
                //删除原有元素
                QueryFilter filter = new QueryFilter(CtContractTemplateElement.class);
                filter.addFilter("contractTemplateId=", ctContractTemplate.getId());
                ctContractTemplateElementService.delete(filter);
                //添加模板元素
                if(!StringUtils.isEmpty(ctContractTemplate.getObjectList())){
                    List<CtContractTemplateElement> list = JSON.parseArray(ctContractTemplate.getObjectList().toString(), CtContractTemplateElement.class);
                    if (list != null && list.size() > 0) {
                        //设置元素坐标
                        setElementsCoordinate(list);
                        //增加新元素
                        ctContractTemplateElementService.saveAll(list);
                    }
                }
            }
            //根据类型判断修改内容  1.模板元素 2.模板印章
            if (ctContractTemplate.getUpdateType()==2) {
                //删除原有印章
                QueryFilter filter = new QueryFilter(CtContractTemplateSeal.class);
                filter.addFilter("contractTemplateId=", ctContractTemplate.getId());
                ctContractTemplateSealService.delete(filter);
                //添加印章
                if (!StringUtils.isEmpty(ctContractTemplate.getObjectSealList())) {
                    List<CtContractTemplateSeal> sealList = JSON.parseArray(ctContractTemplate.getObjectSealList().toString(), CtContractTemplateSeal.class);
                    if (sealList != null && sealList.size() > 0) {
                        //设置元素坐标
                        setSealCoordinate(sealList);
                        //添加新印章
                        ctContractTemplateSealService.saveAll(sealList);
                    }
                }
            }

            return jsonResult.setSuccess(true).setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult.setSuccess(false).setMsg("失败");
    }

    private void setElementsCoordinate(List<CtContractTemplateElement> list) {
        for (CtContractTemplateElement ctContractTemplateElement : list) {
            //判断元素是否是新添加的元素
            if (StringUtils.isEmpty(ctContractTemplateElement.getContractElementId())) {
                //元素表中新增元素
                CtContractElement ctContractElement = new CtContractElement();
                ctContractElement.setElementName(ctContractTemplateElement.getElementName());
                ctContractElementService.save(ctContractElement);
                ctContractTemplateElement.setContractElementId(ctContractElement.getId());
            }

            //此时此处ctContractTemplateElement.getWidth()中的值时宽度比例，
            // ctContractTemplateElement.getHeight()时高度比例
            //计算元素在pdf中的高宽
            Map<String, Object> map = calculation(ctContractTemplateElement.getWidth(),
                    ctContractTemplateElement.getHeight());
            ctContractTemplateElement.setWidth(String.valueOf(map.get("width")));
            ctContractTemplateElement.setHeight(String.valueOf(map.get("height")));
            //ctContractTemplateElement.setPageNum((Integer) map.get("pageNum"));

        }
    }

    private void setSealCoordinate(List<CtContractTemplateSeal> sealList ){

        for (CtContractTemplateSeal ctContractTemplateSeal : sealList) {
            //ctContractTemplateSeal.getWidth()中的值时宽度比例，
            // ctContractTemplateSeal.getHeight()时高度比例
            //计算元素在pdf中的高宽
            Map<String, Object> map = calculation(ctContractTemplateSeal.getWidth(),
                    ctContractTemplateSeal.getHeight());
            ctContractTemplateSeal.setWidth(String.valueOf(map.get("width")));
            ctContractTemplateSeal.setHeight(String.valueOf(map.get("height")));
        }


    }


    /**
     * 计算元素在pdf坐标
     *
     * @param xProportionStr 元素在浏览器img X轴的坐标比例
     * @param yProportionStr 元素在浏览器img Y轴的坐标比例
     * @return
     */
    private Map<String, Object> calculation(String xProportionStr, String yProportionStr) {
        double xProportion = Double.valueOf(xProportionStr);
        double yProportion = Double.valueOf(yProportionStr);
        /**width */
        //width = xProportion*pdfWidth
        double width = xProportion * pdfWidth;
        /**计算high */
        //先计算总高度
        double imgHeight = (1 - yProportion) * pdfHeight;
        //计算坐标在第几页 imgHeight/pdfHigh
        //int pageNum = (int) (imgHeight / pdfHeight) + 1;
        //对pdf高度取余
        double height = pdfHeight - pdfHeight % imgHeight;
        Map<String, Object> map = new HashMap<>(16);
        map.put("width", Math.round(width * 10000) / 10000.0);
        //map.put("pageNum", pageNum);
        map.put("height", Math.round(height * 10000) / 10000.0);
        return map;
    }

    public static void main(String[] args) {

        double d = 0.12;
        double v = (1 - d) * pdfHeight;
        System.out.println(v);
        double height = pdfHeight % v;

        System.out.println(height);
        System.out.println(pdfHeight - height);

    }

}
