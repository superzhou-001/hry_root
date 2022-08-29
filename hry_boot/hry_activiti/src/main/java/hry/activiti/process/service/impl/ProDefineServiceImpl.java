/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:35:04
 */
package hry.activiti.process.service.impl;

import hry.activiti.process.model.ProDefine;
import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.service.ActivitiService;
import hry.activiti.process.service.ProDefineNodeService;
import hry.activiti.process.service.ProDefineService;
import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;

/**
 * <p> ProDefineService </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:35:04
 */
@Service("proDefineService")
@Slf4j
public class ProDefineServiceImpl extends BaseServiceImpl<ProDefine, Long> implements ProDefineService {

    @Resource(name = "proDefineDao")
    @Override
    public void setDao(BaseDao<ProDefine, Long> dao) {
        super.dao = dao;
    }

    @Autowired
    private ProDefineNodeService proDefineNodeService;

    @Autowired
    private ActivitiService activitiService;


    /**
     * 是否为会签节点
     * 编辑bpmn.xml
     */
    private boolean hasMulti(ProDefine proDefine,String id) {


        //taks转userTask
        String flowText = proDefine.getDefineText();

        // xml解析
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(new ByteArrayInputStream(flowText.getBytes()));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // 根节点设置命名空间
        Element rootElement = doc.getRootElement();
        Element process = rootElement.element("process");

        List<Element> userTasks = process.elements("userTask");
        if (userTasks != null && userTasks.size() > 0) {
            for (Element userTask : userTasks) {
                if(id.equals( userTask.attributeValue("id"))) {
                    //获取会签节点
                    Element multiInstanceLoopCharacteristics = userTask.element("multiInstanceLoopCharacteristics");
                    if (multiInstanceLoopCharacteristics != null) {
                        return true;
                    }
                }
            }
        }

        return false;

    }


    /**
     * 自定义修改
     * 编辑bpmn.xml
     */
    private String editXml(ProDefine proDefine) {


        //taks转userTask
        String flowText = HtmlUtils.htmlUnescape(proDefine.getDefineText()).replaceAll("<task", "<userTask").replaceAll("task>", "userTask>");

        // xml解析
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(new ByteArrayInputStream(flowText.getBytes()));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // 根节点设置命名空间
        Element rootElement = doc.getRootElement();
        if (!"http://flowable.org/bpmn20".equals(rootElement.attributeValue("targetNamespace"))) {
            rootElement.addAttribute("targetNamespace", "http://flowable.org/bpmn20");
        }
        if (!"http://flowable.org/bpmn".equals(rootElement.attributeValue("flowable"))) {
            rootElement.addAttribute("xmlns:flowable", "http://flowable.org/bpmn");
        }

        // process节点设置id=defineKey
        Element process = rootElement.element("process");
        process.addAttribute("id", proDefine.getDefineKey());

        List<Element> userTasks = process.elements("userTask");
        if (userTasks != null && userTasks.size() > 0) {
            for (Element userTask : userTasks) {

                //获取会签节点
                Element multiInstanceLoopCharacteristics = userTask.element("multiInstanceLoopCharacteristics");
                if (multiInstanceLoopCharacteristics != null) {
                    if (!"${assignee}".equals(userTask.attributeValue("assignee"))) {
                        userTask.addAttribute("flowable:assignee", "${assignee}");
                    }
                    if (!"true".equals(multiInstanceLoopCharacteristics.attributeValue("isSequential"))) {
                        multiInstanceLoopCharacteristics.addAttribute("isSequential", "false");//并行
                    }
                    if (!"assigneeList".equals(multiInstanceLoopCharacteristics.attributeValue("collection"))) {
                        multiInstanceLoopCharacteristics.addAttribute("flowable:collection", "assigneeList");
                    }
                    if (!"assignee".equals(multiInstanceLoopCharacteristics.attributeValue("elementVariable"))) {
                        multiInstanceLoopCharacteristics.addAttribute("flowable:elementVariable", "assignee");
                    }
                }

            }
        }


        // 格式化输出xml
        StringWriter sw = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        try {
            XMLWriter xmlWriter = new XMLWriter(sw, format);
            xmlWriter.write(doc);
        } catch (IOException ex) {
        } finally {
            try {
                sw.close();
            } catch (IOException ex) {
            }
        }
        log.info(sw.toString());


        return sw.toString();

    }


    /**
     * 构建model
     *
     * @return
     */
    private BpmnModel buildBpmnModel(ProDefine proDefine) {

        System.out.println(proDefine.getDefineText());

        // 解析xml
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = new ByteArrayInputStream(proDefine.getDefineText().getBytes());
        XMLStreamReader xmlStreamReader = null;
        try {
            xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream, "UTF-8");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        // 转换成bpmnModel
        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlStreamReader);

        return bpmnModel;
    }

    /**
     * 检查xml正确性
     *
     * @return
     */
    private boolean checkXml(BpmnModel bpmnModel) {

        //验证bpmnModel 是否是正确的bpmn xml文件
        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
        ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
        //验证失败信息的封装ValidationError
        List<ValidationError> validate = defaultProcessValidator.validate(bpmnModel);

        if (validate != null && validate.size() == 0) {
            return true;
        }
        return false;
    }


    @Override
    public JsonResult saveAndNode(ProDefine proDefine) {
        try {

            ProDefine define = get(new QueryFilter(ProDefine.class).addFilter("defineKey=", proDefine.getDefineKey()));
            if (define != null) {
                return new JsonResult().setMsg("流程key不能重复");
            }

            //自定义编辑xml
            proDefine.setDefineText(editXml(proDefine));
            // 构建流程对象
            BpmnModel bpmnModel = buildBpmnModel(proDefine);

            // 检查流程结构
            if (checkXml(bpmnModel)) {
                //新添加默认为0版本
                proDefine.setVersion(0);
                save(proDefine);
                return new JsonResult().setSuccess(true);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JsonResult().setMsg("流程格式不正确");

    }

    @Override
    public JsonResult updateAndNode(ProDefine proDefine, boolean isDelete) {

        ProDefine proDefineDO = get(proDefine.getId());
        if (proDefineDO == null) {
            return new JsonResult().setMsg("流程不存在");
        }
        proDefineDO.setPdId(proDefine.getPdId());
        proDefineDO.setDefineText(proDefine.getDefineText());
        proDefineDO.setName(proDefine.getName());
        proDefineDO.setBusTableId(proDefine.getBusTableId());


        //自定义编辑xml
        proDefineDO.setDefineText(editXml(proDefineDO));
        // 构建流程对象
        BpmnModel bpmnModel = buildBpmnModel(proDefineDO);

        // 检查流程结构
        if (checkXml(bpmnModel)) {
            update(proDefineDO);
            return new JsonResult().setSuccess(true);
        }


        return new JsonResult().setMsg("流程格式不正确");
    }

    @Override
    public ProDefine getByDefineKey(String defineKey) {
        return get(new QueryFilter(ProDefine.class).addFilter("defineKey=", defineKey));
    }

    @Override
    public JsonResult deploy(ProDefine proDefine) {
        ProcessDefinition pd = activitiService.deploy(proDefine);
        if (pd != null) {
            proDefine.setVersion(pd.getVersion());
            proDefine.setPdId(pd.getId());
            proDefine.setPdKey(pd.getKey());
            proDefine.setIsDeploy(1);//标记已部署
            update(proDefine);

            BpmnModel bpmnModel = buildBpmnModel(proDefine);
            //发布后保存流程节点
            if (bpmnModel != null) {
                // 创建流程节点
                Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
                for (FlowElement e : flowElements) {
                    ProDefineNode node = new ProDefineNode();
                    node.setDefineKey(proDefine.getDefineKey());
                    node.setNodeKey(e.getId());
                    node.setName(e.getName());
                    node.setVersion(proDefine.getVersion());
                    //判断是否会签节点
                    node.setHasMulti(hasMulti(proDefine,e.getId())?1:0);
                    proDefineNodeService.save(node);
                }
            }


            return new JsonResult().setSuccess(true);
        }

        return new JsonResult();
    }
}
