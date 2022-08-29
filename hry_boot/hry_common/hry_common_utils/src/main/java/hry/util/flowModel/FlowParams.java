package hry.util.flowModel;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FlowParams implements Serializable {

    private final String PARAMSLIST = "paramsList";

    private Boolean success = false;

    private String msg;

    private Long startUserId; //启动人ID

    private Long projectId; //项目流程Id

    private String projectName; //项目流程名称

    private String defineName; //流程名称

    private String defineKey; //流程定义key

    private Integer defineVersion; //流程定义版本

    private String taskId; //任务ID

    private String piId; //流程实例id

    private Long processId; //流程管理Id

    private String nodeKey; //节点key

    private String controlName;//控制器名称  示例:appFlowService.start

    private String backNode; //打回节点

    private String sonForm; //子表单key 可包含多个

    //其它参数集合
    @JsonProperty("paramsList")
    @JSONField(name = "paramsList")
    private Map<String, Object> attribute = new HashMap<>();

    public Map<String, Object> getAttribute() {
        return attribute;
    }

    public void setAttribute(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    public Long getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(Long startUserId) {
        this.startUserId = startUserId;
    }

    public String getDefineKey() {
        return defineKey;
    }

    public void setDefineKey(String defineKey) {
        this.defineKey = defineKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getBackNode() {
        return backNode;
    }

    public void setBackNode(String backNode) {
        this.backNode = backNode;
    }

    public String getPiId() {
        return piId;
    }

    public void setPiId(String piId) {
        this.piId = piId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Object getAttribute(String key) {
        return attribute.get(key);
    }

    public void setAttribute(String key, Object object) {
        this.attribute.put(key, object);
    }

    /**
     * 设置回显对象
     * @param object
     */
    public void setViewAttribute(Object object){
        this.attribute.put(PARAMSLIST,object);
    }

    public void setAttributeMap(Map<String, Object> map) {
        this.attribute.putAll(map);
    }

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public String getAttributeString(String key) {
        return attribute.get(key).toString();
    }

    public Long getAttributeLong(String key) {
        Object value = attribute.get(key);
        if (value != null) {
            return Long.valueOf(value.toString());
        }
        return null;
    }

    public Integer getAttributeInteger(String key) {
        Object value = attribute.get(key);
        if (value != null) {
            return Integer.valueOf(value.toString());
        }
        return null;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public Integer getDefineVersion() {
        return defineVersion;
    }

    public void setDefineVersion(Integer defineVersion) {
        this.defineVersion = defineVersion;
    }

    public String getSonForm() {
        return sonForm;
    }

    public void setSonForm(String sonForm) {
        this.sonForm = sonForm;
    }

    public Boolean getSuccess() {
        return success;
    }

    public FlowParams setSuccess(Boolean success) {
        this.success = success;
        return  this;
    }

    public String getMsg() {
        return msg;
    }

    public FlowParams setMsg(String msg) {
        this.msg = msg;
        return  this;
    }

    /**
     * 获取全部属性,包括常用属性
     *
     * @return
     */
    @JSONField(serialize=false)
    @JsonIgnore
    public Map<String, Object> getAllAttribute() {
        Map<String, Object> map = new HashMap<>();
        map.putAll(attribute);

        if (this.startUserId != null) {
            map.put("startUserId", this.startUserId);
        }
        if (this.projectId != null) {
            map.put("projectId", this.projectId);
        }
        if (this.projectName != null) {
            map.put("projectName", this.projectName);
        }
        if (this.defineKey != null) {
            map.put("defineKey", this.defineKey);
        }
        if (this.defineVersion != null) {
            map.put("defineVersion", this.defineVersion);
        }
        if (this.taskId != null) {
            map.put("taskId", this.taskId);
        }
        if (this.piId != null) {
            map.put("piId", this.piId);
        }
        if (this.processId != null) {
            map.put("processId", this.processId);
        }
        if (this.nodeKey != null) {
            map.put("nodeKey", this.nodeKey);
        }
        if (this.controlName != null) {
            map.put("controlName", this.controlName);
        }

        return map;
    }




}
