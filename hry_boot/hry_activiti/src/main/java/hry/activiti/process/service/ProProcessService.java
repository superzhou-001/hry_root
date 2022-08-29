/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:55:47
 */
package hry.activiti.process.service;

import hry.activiti.process.model.ProProcess;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.util.flowModel.FlowParams;

/**
 * <p> ProProcessService </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:55:47
 */
public interface ProProcessService extends BaseService<ProProcess, Long> {

    /**
     * 启动流程
     * @param FlowParams  流程启动参数
     * @return
     */
    JsonResult start(FlowParams flowParams);

    /**
     * 我的任务
     * @param page
     * @param pageSize
     * @param userId
     * @return
     */
    PageResult findMyTask(Integer page, Integer pageSize, Long userId);

    /**
     * 全部任务
     * @param page
     * @param pageSize
     * @return
     */
    PageResult findAllTask(Integer page, Integer pageSize);

    /**
     * 完成一个任务
     * @param taskId 任务ID
     * @param btnId  按钮ID
     * @return
     */
    JsonResult completeTask(String taskId, Long btnId, FlowParams flowParams);

    /**
     * 查询我的历史任务
     * @param page
     * @param pageSize
     * @param userId
     * @return
     */
    PageResult findMyHisTask(Integer page, Integer pageSize, Long userId);

    /**
     * 获取流程任务配置
     * @return
     */
    JsonResult getTaskConfig(String taskId,String nodeKey,Long processId);
}
