/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-14 14:18:11 
 */
package hry.platform.newuser.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.AppLoginLog;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AppLoginLogService </p>
 *
 * @author: zhouming
 * @Date: 2020-08-14 14:18:11 
 */
public interface AppLoginLogService extends BaseService<AppLoginLog, Long> {
    /**
     * 数据添加至ES
     * */
    public void saveToEs(AppLoginLog log);
    /**
     * 修改es数据
     * */
    public void updateToEs(AppLoginLog log);
    /**
     * 查询登录日志
     * */
    PageResult findLoginLogPageToES(QueryFilter filter);
    /**
     * 定时器修改登录日志时长
     * */
    void updateLoginLogTimer();

}
