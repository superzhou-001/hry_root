/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-28 12:05:59
 */
package hry.platform.flow.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.flow.model.AppFlow;
import hry.platform.flow.service.AppFlowService;
import hry.platform.flow.service.CommonFlowService;
import hry.util.UUIDUtil;
import hry.util.flowModel.FlowParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AppFlowService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-28 12:05:59
 */
@Service("appFlowService")
public class AppFlowServiceImpl extends BaseServiceImpl<AppFlow, Long> implements AppFlowService, CommonFlowService {

	@Resource(name = "appFlowDao")
	@Override
	public void setDao (BaseDao<AppFlow, Long> dao) {
		super.dao = dao;
	}


	@Override
	public FlowParams start(FlowParams flowParams) {

		//创建流程
		AppFlow appFlow = new AppFlow();
		appFlow.setName(UUIDUtil.getUUID());
		save(appFlow);

		//设置流程参数
		flowParams.setProjectId(appFlow.getId());
		flowParams.setProjectName(appFlow.getName());
		flowParams.setSuccess(true);
		return flowParams;

	}

	@Override
	public FlowParams view(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}


	@Override
	public FlowParams oneFlowQc(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}

	@Override
	public FlowParams oneFlowXl(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}

	@Override
	public FlowParams oneFlowCf(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}

	@Override
	public FlowParams oneFlowSb(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}

	@Override
	public FlowParams oneFlowDdm(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}

	@Override
	public FlowParams oneFlowXb(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}

	@Override
	public FlowParams oneFlowZc(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}

	@Override
	public FlowParams oneFlowZfj(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}

	@Override
	public FlowParams oneFlowZtc(FlowParams flowParams) {

		flowParams.setSuccess(true);

		return flowParams;
	}
}
