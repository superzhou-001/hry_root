package hry.activiti.process.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HryTask implements Serializable {

    private String pdId; //流程定义ID

    private String piId;  //流程实例id

    private String taskId;  //任务id

    private Long processId; //流程ID

    private Long projectId; //业务流程ID

    private String flowName;//流程名称

    private String taskName; //任务名称

    private Date createTime;  //任务创建时间

    private String executionId; //执行对象ID

    private String assigneeId; //办理人

    private String assigneeName;//办理人名称

    private String nodeKey;//节点key

    private Date dueDate; //过期时间

    private String  countDownTime;//倒计时

    private boolean hasDue;//是否过期


}
