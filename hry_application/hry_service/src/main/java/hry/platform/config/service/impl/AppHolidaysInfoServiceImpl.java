/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-19 11:26:51 
 */
package hry.platform.config.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.config.model.AppHolidaysInfo;
import hry.platform.config.model.AppHolidaysYear;
import hry.platform.config.service.AppHolidaysInfoService;
import hry.platform.config.service.AppHolidaysYearService;
import hry.redis.RedisService;
import hry.util.StringUtil;
import hry.util.date.DateUtil;
import hry.util.httpRequest.HttpConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p> AppHolidaysInfoService </p>
 *
 * @author: zhouming
 * @Date: 2020-08-19 11:26:51 
 */
@Service("appHolidaysInfoService")
public class AppHolidaysInfoServiceImpl extends BaseServiceImpl<AppHolidaysInfo, Long> implements AppHolidaysInfoService {

	public static final String URL = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php";

	@Autowired
	private AppHolidaysYearService appHolidaysYearService;

	@Autowired
	private RedisService redisService;

	@Resource(name = "appHolidaysInfoDao")
	@Override
	public void setDao (BaseDao<AppHolidaysInfo, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult networkSyncDate(Long yearId) {
		// 同步先删除已存在节假日
		QueryFilter filter = new QueryFilter(AppHolidaysInfo.class);
		filter.addFilter("yearId=", yearId);
		super.delete(filter);
		AppHolidaysYear year = appHolidaysYearService.get(yearId);
		List<AppHolidaysInfo> infoList = getHolidays(year.getYear());
		if (infoList != null && infoList.size() > 0) {
			// 保存节假日明细
			infoList.forEach(info -> {
				info.setYearId(yearId);
				info.setYear(year.getYear());
				super.save(info);
			});
			return new JsonResult(true).setMsg("网络同步成功");
		}
		return new JsonResult(false).setMsg("网络同步失败");
	}

	@Override
	public JsonResult findHolidayInterval(Long yearId) {
		// 获取节假日
		QueryFilter filter = new QueryFilter(AppHolidaysInfo.class);
		filter.addFilter("yearId=", yearId);
		filter.addFilter("dateType=", 1);
		filter.setOrderby("holidayDate ASC");
		List<AppHolidaysInfo> infos = super.find(filter);
		List<AppHolidaysInfo> newInfos = groubByHoliday(infos);

		// 获取上班日
		QueryFilter filter2 = new QueryFilter(AppHolidaysInfo.class);
		filter2.addFilter("yearId=", yearId);
		filter2.addFilter("dateType=", 2);
		filter2.setOrderby("holidayDate ASC");
		List<AppHolidaysInfo> infos2 = super.find(filter2);
		List<AppHolidaysInfo> newInfos2 = groubByHoliday(infos2);
		newInfos.addAll(newInfos2);

		// 集合排序
		List<AppHolidaysInfo> list = newInfos.stream().sorted((a, b) ->
				a.getHolidayDate().compareTo(b.getHolidayDate()))
				.collect(Collectors.toList());
		// 获取完整节假日
		QueryFilter filter3 = new QueryFilter(AppHolidaysInfo.class);
		filter3.addFilter("yearId=", yearId);
		filter3.setOrderby("holidayDate ASC");
		List<AppHolidaysInfo> infos3 = super.find(filter3);

		Map<String, List<AppHolidaysInfo>> resultmap = new HashMap<>();
		resultmap.put("holidayInterval", list);
		resultmap.put("holidays", infos3);
		return new JsonResult(true).setObj(resultmap);
	}

	@Override
	public JsonResult addHoliday(Map<String, String> map) {
		String yearId = map.get("yearId");
		// 1 节假日 2 工作日
		String dateType = map.get("dateType");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		String dateName = map.get("dateName");
		// 获取年份
		AppHolidaysYear year = appHolidaysYearService.get(Long.parseLong(yearId));
		// 查询已设置的节假日
		QueryFilter filter = new QueryFilter(AppHolidaysInfo.class);
		filter.addFilter("yearId=", yearId);
		List<AppHolidaysInfo> infoList = super.find(filter);
		List<String> dateList = DateUtil.findDates(startTime, endTime);
		// 是否使用前台传过来的名称
		boolean isDateName = true;
		for (int i = 0; i < dateList.size(); i++) {
			String date = dateList.get(i);
			boolean isAdd = true;
			for (AppHolidaysInfo info : infoList) {
				// 区间时间和已存在时间比较，
				// 存在并且日期类型不同修改，不存在则添加对应类型日期
				String infoDate = DateUtil.getFormatDateTime(info.getHolidayDate(), "yyyy-MM-dd");
				if (date.equals(infoDate)) {
					if (!dateType.equals(info.getDateType())) {
						if (!isDateName) {
							dateName = "1".equals(dateType) ? "休":"班";
						}
						info.setDateName(dateName);
						info.setDateType(Integer.parseInt(dateType));
						super.update(info);
						isDateName = false;
					}
					isAdd = false;
				}
			}
			if (isAdd) {
				if (!isDateName) {
					dateName = "1".equals(dateType) ? "休":"班";
				}
				AppHolidaysInfo newInfo = new AppHolidaysInfo();
				newInfo.setYearId(Long.parseLong(yearId));
				newInfo.setYear(year.getYear());
				newInfo.setDateName(dateName);
				newInfo.setHolidayDate(DateUtil.stringToDate(date));
				newInfo.setDateType(Integer.parseInt(dateType));
				super.save(newInfo);
				isDateName = false;
			}
		}
		dateList.forEach(date -> {

		});
		return new JsonResult(true);
	}

	@Override
	public JsonResult delHoliday(Map<String, String> map) {
		String yearId = map.get("yearId");
		String startTime = map.get("startTime");
		String endTime = map.get("endTime");
		// 获取区间日期
		List<String> dateList = DateUtil.findDates(startTime, endTime);
		dateList.forEach(date -> {
			QueryFilter filter = new QueryFilter(AppHolidaysInfo.class);
			filter.addFilter("yearId=", yearId);
			filter.addFilter("holidayDate=", date);
			super.delete(filter);
		});
		return new JsonResult(true);
	}

	/**
	 * 节假日区间分组
	 * */
	private List<AppHolidaysInfo> groubByHoliday(List<AppHolidaysInfo> infos) {
		List<AppHolidaysInfo> newInfos = new ArrayList<>();
		if (infos == null || infos.size() == 0) {
			return newInfos;
		}
		// 开始时间
		Date startDate = infos.get(0).getHolidayDate();
		String dateName = infos.get(0).getDateName();
		// 结束时间
		Date endDate;
		// 是否进行下一组
		boolean isflag = false;
		for (int i = 0; i < infos.size(); i++) {
			if (isflag) {
				startDate = infos.get(i).getHolidayDate();
				dateName = infos.get(i).getDateName();
				isflag = false;
			}
			// 当前日期
			Date date = infos.get(i).getHolidayDate();
			// 下一个时间
			Date nextDate;
			if (i+1 == infos.size()) {
				nextDate = infos.get(i).getHolidayDate();
			} else {
				nextDate = infos.get(i+1).getHolidayDate();
			}
			// 日期比较
			Date realityNextDate = DateUtil.addDay(date, 1);
			if (nextDate.compareTo(realityNextDate) != 0 || (i+1) == infos.size()) {
				//如果循环到最后一个
				if ((i+1) == infos.size()) {
					endDate = nextDate;
				}else{
					endDate = date;
				}
				AppHolidaysInfo info = new AppHolidaysInfo();
				info.setYearId(infos.get(i).getYearId());
				info.setYear(infos.get(i).getYear());
				info.setHolidayDate(startDate);
				info.setDateName(dateName);
				info.setDateStartTime(DateUtil.dateToString(startDate, "yyyy-MM-dd"));
				info.setDateEndTime(DateUtil.dateToString(endDate, "yyyy-MM-dd"));
				info.setDateType(infos.get(i).getDateType());
				newInfos.add(info);
				startDate = nextDate ;
				isflag = true;
			}
		}
		return newInfos;
	}


	private List<AppHolidaysInfo> getHolidays(String year) {
		// 获取网络法定节假日
		/*String holidayStr = redisService.hget("appconfig:holidaysConfig",year);
		if (!StringUtil.isNull(holidayStr)) {
			String param = "resource_id=6018&query="+year;
			holidayStr = HttpConnectionUtil.getSend(URL, param, "GBK");
			redisService.hset("appconfig:holidaysConfig", year, holidayStr);
		}*/
		String param = "resource_id=6018&query="+year;
		String holidayStr = HttpConnectionUtil.getSend(URL, param, "GBK");
		JSONObject objJson = JSON.parseObject(holidayStr);
		JSONArray dataArr = objJson.getJSONArray("data");
		JSONObject dateJson = dataArr.getJSONObject(0);
		// 获取全年详情假期
		JSONArray holidays = dateJson.getJSONArray("holiday");
		List<Map<String, String>> result = new ArrayList<>();
		// 无重复日期
		Map<String, String> one = new HashMap<>();
		holidays.forEach(holiday -> {
			JSONObject json = JSON.parseObject(holiday.toString());
			// 节日具体天数
			JSONArray dayList = json.getJSONArray("list");
			dayList.forEach(day -> {
				Map<String, String> map = new HashMap<>();
				JSONObject cjson = JSON.parseObject(day.toString());
				String dateName = "";
				// 日期
				String date = cjson.getString("date");
				// 日期类型 1 节假日 2 工作日
				String dateType = cjson.getString("status");
				one.put(date, dateType);
			});
		});
		// 获取指定天数节假日
		Map<String, String> two = new HashMap<>();
		JSONArray holidaysDate = dateJson.getJSONArray("holidaylist");
		holidaysDate.forEach(holiday ->{
			JSONObject json = JSON.parseObject(holiday.toString());
			String dateName = json.getString("name");
			String holidayDate = json.getString("startday");
			two.put(dateName, holidayDate);
		});
		List<AppHolidaysInfo> infoList = new ArrayList<>();
		one.forEach((key,vlaue) -> {
			String dateName = "";
			AppHolidaysInfo info = new AppHolidaysInfo();
			AtomicReference<String> name = new AtomicReference<>("");
			two.forEach((key1, vlaue1) -> {
				if (key.equals(vlaue1)) {
					name.set(key1);
				}
			});
			if (!StringUtil.isNull(name.toString())) {
				dateName = "1".equals(vlaue) ? "休" : "班";
			} else {
				dateName = name.toString();
			}
			info.setDateName(dateName);
			info.setDateType(Integer.parseInt(vlaue));
			info.setHolidayDate(DateUtil.stringToDate(key));
			infoList.add(info);
		});
		// 集合排序
		List<AppHolidaysInfo> list = infoList.stream().sorted((a, b) ->
												a.getHolidayDate().compareTo(b.getHolidayDate()))
												.collect(Collectors.toList());
		return list;
	}
}
