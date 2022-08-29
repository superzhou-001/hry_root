/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Yuan Zhicheng
 * @version:      V1.0
 * @Date:        2015年9月16日 上午11:04:39
 */
package hry.util.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 */
public class DateUtil {

	private static final String[] PARSEPATTERNS = new String[] {
			"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss",
			"yyyy.MM.dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm",
			"yyyy.MM.dd HH:mm", "yyyy-MM-dd HH", "yyyy/MM/dd HH",
			"yyyy.MM.dd HH", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd" };
	private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将字符串转换成日期类型,自动匹配格式
	 *
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String date) {
		try {
			return DateUtils.parseDate(date, PARSEPATTERNS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串格式转日期
	 *
	 * @param date
	 * @param parsePatterns
	 * @return
	 */
	public static Date stringToDate(String date, String... parsePatterns) {
		try {
			return DateUtils.parseDate(date, parsePatterns);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期转字符串 根据给定日期格式，格式化日期
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 日期转字符串 yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, PATTERN);
	}

	/**
	 * 增加n天后的日期
	 *
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, n);// 增加n天
		return calendar.getTime();
	}

	/**
	 * 增加n个月后的日期
	 *
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, n);// 增加n个月
		return calendar.getTime();
	}

	/**
	 * 获取当前月第一天
	 *
	 * @return
	 */
	public static Date firstDayOfMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return c.getTime();
	}


	/**
	 * 在日期上加分钟数，得到新的日期
	 *
	 * @return
	 */
	public final static Date addMinToDate(Date date, int min) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, min);
		return c.getTime();
	}

	/**
	 * 在日期上加days天，得到新的日期
	 *
	 * @return
	 */
	public final static Date addDaysToDate(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return c.getTime();
	}

	/**
	 * 在日期上加months月，得到新的日期
	 *
	 * @return
	 */
	public final static Date addMonthsToDate(Date date, int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}
	/**
	 * 比较两个日期的大小,type =1加时分秒判断。=0不加时分秒
	 * <p> TODO</p>
	 * @author:         Zeng Hao
	 * @param:    @param date1
	 * @param:    @param date2
	 * @param:    @param type =1加时分秒判断。=0不加时分秒
	 * @param:    @return 1大于。-1小于。0等于
	 * @return: int
	 * @Date :          2016年11月30日 下午12:05:20
	 * @throws:
	 */
	public final static int compareDate(Date date1,Date date2,int type){
		SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1=Calendar.getInstance();
	    Calendar c2=Calendar.getInstance();
		Date newDate1= null;
		Date newDate2= null;
		try {
			if(type==0){
				newDate1 = sd1.parse(sd1.format(date1));
				newDate2 = sd1.parse(sd1.format(date2));
			}else if(type==1){
				newDate1 = sd2.parse(sd2.format(date1));
				newDate2 = sd2.parse(sd2.format(date2));
			}else{
				newDate1 = date1;
				newDate2 = date2;
			}
	        c1.setTime(newDate1);
	        c2.setTime(newDate2);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return c1.compareTo(c2);
	}
	/**
	 * map转json
	 * <p> TODO</p>
	 * @author:         Zeng Hao
	 * @param:    @param map
	 * @param:    @return
	 * @return: String
	 * @Date :          2017年2月4日 上午11:19:41
	 * @throws:
	 */
	public final static String simpleMapToJsonStr(Map<String ,String > map){
        if(map==null||map.isEmpty()){
            return "null";
        }
        String jsonStr = "{";
        Set<?> keySet = map.keySet();
        for (Object key : keySet) {
            jsonStr += "\""+key+"\":\""+map.get(key)+"\",";
        }
        jsonStr = jsonStr.substring(1,jsonStr.length()-2);
        jsonStr += "}";
        return jsonStr;
    }
	/**
	 * 计算两日期之间的天数
	 *
	 * @return
	 */
	public final static int getDaysBetweenDate(String date1, String date2) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try {
			d1 = sd.parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2 = null;
		try {
			d2 = sd.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c1 = Calendar.getInstance();

		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		long diff = (c2.getTimeInMillis() - c1.getTimeInMillis())
				/ (1000 * 60 * 60 * 24);
		return ((Long) diff).intValue();
	}

	/**
	 * 计算两日期之间的天数
	 *
	 * @return
	 */
	public final static Integer getDaysBetweenDate(Date date1, Date date2) {
		Date d1 = date1;
		Date d2 = date2;
		Calendar c1 = Calendar.getInstance();

		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		long diff = (c2.getTimeInMillis() - c1.getTimeInMillis())
				/ (1000 * 60 * 60 * 24);
		return ((Long) diff).intValue();
	}

	/**
	 *
	 * @param dateString
	 *            日期字符串 如2011-01-03
	 * @param dateFormate
	 *            日期格式 如yyyy-MM-dd
	 * @return 根据传入的日期字符串和日期格式返回指定格式的日期
	 */
	public final static Date parseDate(String dateString, String dateFormate) {
		SimpleDateFormat sd = new SimpleDateFormat(dateFormate);
		Date date = null;
		try {
			date = sd.parse(dateString);
		} catch (Exception ex) {

		}
		return date;
	}

	/**
	 * 计算两日期之间相隔月份和天数
	 *
	 * @return
	 */
	public static Map<Integer, Integer> getMonthAndDaysBetweenDate(
			String date1, String date2) {
		Map<Integer, Integer> map = new HashMap();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try {
			d1 = sd.parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2 = null;
		try {
			d2 = sd.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int months = 0;// 相差月份
		int days = 0;
		int y1 = d1.getYear();
		int y2 = d2.getYear();
		int dm1 = d2.getMonth();// 起始日期月份
		int dm2 = d2.getMonth();// 结束日期月份
		int dd1 = d1.getDate(); // 起始日期天
		int dd2 = d2.getDate(); // 结束日期天
		if (d1.getTime() < d2.getTime()) {
			months = d2.getMonth() - d1.getMonth() + (y2 - y1) * 12;
			if (dd2 < dd1) {
				months = months - 1;
			}
			days = getDaysBetweenDate(
					getFormatDateTime(
							addMonthsToDate(
									DateUtil.parseDate(date1, "yyyy-MM-dd"),
									months), "yyyy-MM-dd"), date2);
			map.put(1, months);
			map.put(2, days);
		}
		return map;
	}

	/**
	 * @function 得到自定义 日期格式
	 * @param dateFormat
	 * @return
	 */
	public final static String getFormatDateTime(Date date, String dateFormat) {

		SimpleDateFormat sf = null;
		try {
			sf = new SimpleDateFormat(dateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return sf.format(date);
	}


	/**
	 * @function 得到自定义 日期格式
	 * @param dateFormat
	 * @return
	 */
	public final static String getFormatDateTime(String date, String dateFormat) {

		SimpleDateFormat sf = null;
		try {
			sf = new SimpleDateFormat(dateFormat);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return sf.format(date);
	}

	/**
	 * 返回字符串形式----当前时间的年月日时分秒
	 *
	 * <p>
	 * TODO
	 * </p>
	 *
	 * @author: Wu Shuiming
	 * @param: @return
	 * @return: String
	 * @Date : 2016年4月7日 上午10:51:18
	 * @throws:
	 */
	public static String getNewDate() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}


	/**
	 * 时间按分钟加减
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @param date      时间
	 * @param:    @param minute    分钟数  加传正数  -传负数
	 * @param:    @return
	 * @return: Date
	 * @Date :          2016年4月20日 上午10:03:37
	 * @throws:
	 */
	public static Date dateAddMinute(Date date,int minute){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}
	public static Date dateAddSecond(Date date,int second){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND,second);
		return cal.getTime();
	}

	/**
	 * 比较两个时间相差多少分钟
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @param date1  较大时间
	 * @param:    @param date2  较小时间
	 * @param:    @return
	 * @return: int
	 * @Date :          2016年4月20日 上午10:29:49
	 * @throws:
	 */
	public static int compareDateMinute(Date date1,Date date2){
		Calendar dateOne=Calendar.getInstance();
		dateOne.setTime(date1);	//设置date1
		Calendar dateTwo=Calendar.getInstance();
		dateTwo.setTime(date2);	//设置date2
		long timeOne=dateOne.getTimeInMillis();
		long timeTwo=dateTwo.getTimeInMillis();
		long minute=(timeOne-timeTwo)/(1000*60);//转化minute
		return Long.valueOf(minute).intValue();
	}

	/**
	 * 比较两个时间相差多少个space分钟
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @param date1  较大时间
	 * @param:    @param date2  较小时间
	 * @param:    @param space  时间间隔
	 * @param:    @return
	 * @return: int
	 * @Date :          2016年4月20日 上午10:29:49
	 * @throws:
	 */
	public static int compareDateMinuteSpace(Date date1,Date date2,int space){
		Calendar dateOne=Calendar.getInstance();
		dateOne.setTime(date1);	//设置date1
		Calendar dateTwo=Calendar.getInstance();
		dateTwo.setTime(date2);	//设置date2
		long timeOne=dateOne.getTimeInMillis();
		long timeTwo=dateTwo.getTimeInMillis();
		long minute=(timeOne-timeTwo)/(1000*60*space);//转化minute
		return Long.valueOf(minute).intValue();
	}

	/**
	 * 获得所有的时间区间
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:  startDate,endDate  传的时间不能有时分秒
	 * @return: Map<String,List<String[]>>
	 * 			数据格式
	 * 			1min ,[2016-09-14 12:00:00,2016-09-14 12:01:00],[2016-09-14 12:01:00,2016-09-14 12:02:00],  .....
	 * 			5min ,[2016-09-14 12:00:00,2016-09-14 12:05:00],[2016-09-14 12:05:00,2016-09-14 12:10:00],  .....
	 * 			15min
	 * 			...
	 * 			...
	 *
	 *
	 * @Date :          2016年9月14日 下午2:45:21
	 * @throws:
	 */
	public static Map<String,List<String[]>> GetTimeInterval(Date startDate,Date endDate){
		Map<String,List<String[]>>  map = new HashMap<String, List<String[]>>();

		long startTime = startDate.getTime();
		long endTime = endDate.getTime();

		/**--------------------------获得一分钟区间--------------------------------------**/
		List<String[]> min1List = new ArrayList<String[]>();
		long tempTime = startTime;
		while (tempTime< endTime) {
			String[] arr = new String[2];
			//开始时间
			arr[0] = dateToString(new Date(tempTime), "yyyy-MM-dd HH:mm:ss");
			//增加一个区间
			tempTime = addMinToDate(new Date(tempTime), 1).getTime();
			//结束时间
			arr[1] = dateToString(new Date(tempTime), "yyyy-MM-dd HH:mm:ss");
			min1List.add(arr);
		}
		//put 1分钟
		map.put("1min", min1List);

		/**--------------------------获得五分钟区间--------------------------------------**/
		List<String[]> min5List = new ArrayList<String[]>();
		long tempTime5 = startTime;
		while (tempTime5< endTime) {
			String[] arr = new String[2];
			//开始时间
			arr[0] = dateToString(new Date(tempTime5), "yyyy-MM-dd HH:mm:ss");
			//增加一个区间
			tempTime5 = addMinToDate(new Date(tempTime5), 5).getTime();
			//结束时间
			arr[1] = dateToString(new Date(tempTime5), "yyyy-MM-dd HH:mm:ss");
			min5List.add(arr);
		}
		//put 1分钟
		map.put("5min", min5List);

		/**--------------------------获得十五分钟区间--------------------------------------**/
		List<String[]> min15List = new ArrayList<String[]>();
		long tempTime15 = startTime;
		while (tempTime15< endTime) {
			String[] arr = new String[2];
			//开始时间
			arr[0] = dateToString(new Date(tempTime15), "yyyy-MM-dd HH:mm:ss");
			//增加一个区间
			tempTime15 = addMinToDate(new Date(tempTime15), 15).getTime();
			//结束时间
			arr[1] = dateToString(new Date(tempTime15), "yyyy-MM-dd HH:mm:ss");
			min15List.add(arr);
		}
		//put 1分钟
		map.put("15min", min15List);

		/**--------------------------获得三十分钟区间--------------------------------------**/
		List<String[]> min30List = new ArrayList<String[]>();
		long tempTime30 = startTime;
		while (tempTime30< endTime) {
			String[] arr = new String[2];
			//开始时间
			arr[0] = dateToString(new Date(tempTime30), "yyyy-MM-dd HH:mm:ss");
			//增加一个区间
			tempTime30 = addMinToDate(new Date(tempTime30), 30).getTime();
			//结束时间
			arr[1] = dateToString(new Date(tempTime30), "yyyy-MM-dd HH:mm:ss");
			min30List.add(arr);
		}
		//put 1分钟
		map.put("30min", min30List);

		/**--------------------------获得六十分钟区间--------------------------------------**/
		List<String[]> min60List = new ArrayList<String[]>();
		long tempTime60 = startTime;
		while (tempTime60< endTime) {
			String[] arr = new String[2];
			//开始时间
			arr[0] = dateToString(new Date(tempTime60), "yyyy-MM-dd HH:mm:ss");
			//增加一个区间
			tempTime60 = addMinToDate(new Date(tempTime60), 60).getTime();
			//结束时间
			arr[1] = dateToString(new Date(tempTime60), "yyyy-MM-dd HH:mm:ss");
			min60List.add(arr);
		}
		//put 1分钟
		map.put("60min", min60List);


		return map;
	}

	public static void main(String[] args) throws ParseException{
		System.out.println(timestamp2Date(new Date().getTime()-DateUtil.addMonth(new Date(),1).getTime()));
	}


	/**
	 * 获得当前时间的8个区间时间值    前区间   时间定位
	 * @param date
	 * @return
	 */
	public static Map<String,Date> getPeriodDate(Date date){
		Map<String,Date> map = new HashMap<String, Date>();
		//"1min","5min","15min","30min","60min","1day","1week","1mon"
		map.put("1min", stringToDate(getFormatDateTime(date, "yyyy-MM-dd HH:mm")));
		//当前时间的所在5分区间
		map.put("5min", getPeriodMin(date,5));
		//当前时间的所在15分区间
		map.put("15min", getPeriodMin(date,15));
		//当前时间的所在30分区间
		map.put("30min", getPeriodMin(date,30));
		//当前时间的所在的小时
		map.put("60min", stringToDate(getFormatDateTime(date, "yyyy-MM-dd HH")));
		//当前时间的所在天
		map.put("1day", stringToDate(getFormatDateTime(date, "yyyy-MM-dd")));
		//当前时间
		Calendar cweek = Calendar.getInstance(Locale.CHINA);
		cweek.setTime(date);
		cweek.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		cweek.set(Calendar.HOUR_OF_DAY, 0);
		cweek.set(Calendar.MINUTE, 0);
		cweek.set(Calendar.SECOND, 0);
		map.put("1week", cweek.getTime());
		//当前时间所在月的第一天
		Calendar cmon = Calendar.getInstance(Locale.CHINA);
		cmon.setTime(date);
		cmon.set(Calendar.DAY_OF_MONTH, 1);
		cmon.set(Calendar.HOUR_OF_DAY, 0);
		cmon.set(Calendar.MINUTE, 0);
		cweek.set(Calendar.SECOND, 0);
		map.put("1mon", cmon.getTime());
		return map;
	}

	public static Map<String,Date> getPeriodDate2(Date date){
		Map<String,Date> map = new HashMap<String, Date>();
		//"1min","5min","15min","30min","60min","1day","1week","1mon"
		map.put("1min", stringToDate(getFormatDateTime(date, "yyyy-MM-dd HH:mm")));
		//当前时间的所在5分区间
		map.put("5min", getPeriodMin(date,5));
		//当前时间的所在15分区间
		map.put("15min", getPeriodMin(date,15));
		//当前时间的所在30分区间
		map.put("30min", getPeriodMin(date,30));
		//当前时间的所在的小时
		map.put("60min", stringToDate(getFormatDateTime(date, "yyyy-MM-dd HH")));
		//当前时间的所在的4小时
		map.put("240min", getPeriodHour(date, 4));
		//当前时间的所在天
		map.put("1day", stringToDate(getFormatDateTime(date, "yyyy-MM-dd")));
		//当前时间
		Calendar cweek = Calendar.getInstance(Locale.CHINA);
		cweek.setTime(date);
		cweek.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		cweek.set(Calendar.HOUR_OF_DAY, 0);
		cweek.set(Calendar.MINUTE, 0);
		cweek.set(Calendar.SECOND, 0);
		map.put("1week", cweek.getTime());
		//当前时间所在月的第一天
		Calendar cmon = Calendar.getInstance(Locale.CHINA);
		cmon.setTime(date);
		cmon.set(Calendar.DAY_OF_MONTH, 1);
		cmon.set(Calendar.HOUR_OF_DAY, 0);
		cmon.set(Calendar.MINUTE, 0);
		cmon.set(Calendar.SECOND, 0);
		map.put("1mon", cmon.getTime());
		return map;
	}

	public static Date getPeriodMin(Date date , int index){
		int num =  Integer.valueOf(getFormatDateTime(date, "mm"))/index;
		if(num == 0 ){
			return stringToDate(getFormatDateTime(date, "yyyy-MM-dd HH"));
		}else{
			return stringToDate(getFormatDateTime(date, "yyyy-MM-dd HH")+":"+index*num, "yyyy-MM-dd HH:mm");
		}
	}

	public static Date getPeriodHour(Date date , int index){
		int num =  Integer.valueOf(getFormatDateTime(date, "HH"))/index;
		if(num == 0 ){
			return stringToDate(getFormatDateTime(date, "yyyy-MM-dd HH"));
		}else{
			return stringToDate(getFormatDateTime(date, "yyyy-MM-dd ")+index*num, "yyyy-MM-dd HH");
		}
	}

	  /**
	   *
	   * <p>比较两个时间的大小</p>
	   * @author:         Zhang Xiaofang
	   * @param:    @param dt1
	   * @param:    @param dt2
	   * @param:    @return
	   * @return: int
	   * @Date :          2016年7月30日 下午12:53:26
	   * @throws:
	   */
	   public static int compare_date(Date dt1, Date dt2) {


	        try {

	            if (dt1.getTime() > dt2.getTime()) {
	                return 1;
	            } else if (dt1.getTime() < dt2.getTime()) {

	                return -1;
	            } else {
	                return 0;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return 0;
	    }

	   /**
	    * Wed Jul 06 2016 00:00:00 GMT+0800 (中国标准时间)  带时区的时间格式转换
	    * <p> 日期格式转换</p>
	    * @author:         Zhang Xiaofang
	    * @param:    @param date
	    * @param:    @param index
	    * @param:    @return
	    * @return: Date
	    * @Date :          2016年8月9日 下午3:17:05
	    * @throws:
	    */
		public static String  conversion(String date , String format){

			Date d=null;
			try {
				date=date.split("GMT")[0];
				d = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z",Locale.US).parse(date+"GMT+08:00");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
            String  dd=sf2.format(d);
			return dd;

		}


		/**
		 *  Sun Jan 18 09:22:12 CST 1970
		 * <p> TODO</p>
		 * @author:         Zhang Xiaofang
		 * @param:    @param str
		 * @param:    @return
		 * @return: String
		 * @Date :          2016年9月13日 下午10:35:55
		 * @throws:
		 */
	   public static String  formatByUS(String str) {
			Date date;
			try {
				date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(str);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dd = format.format(date);
				return dd;

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";

	}

	   /**
	    * 将时间戳转换为时间
	    * <p> TODO</p>
	    * @author:         Zhang Xiaofang
	    * @param:    @param s
	    * @param:    @return
	    * @return: String
	    * @Date :          2016年9月28日 上午11:30:09
	    * @throws:
	    */
	    public static String stampToDate(String s){
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        long lt = new Long(s);
	        Date date = new Date(lt);
	        res = simpleDateFormat.format(date);
	        return res;
	    }



	/**
	 *
	 * <p> TODO</p>
	 * @author:         Zhang Lei
	 * @param:    @param s
	 * @param:    @return
	 * @return: String
	 * @Date :          2017年4月6日 下午5:18:44
	 * @throws:
	 */
    public static String getLastAmonthDay(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }
	/**
	 *
	 * <p> TODO</p>
	 * @author:         Shangxl
	 * @param:    @param date '2016'
	 * @return: void
	 * @Date :          2017年5月9日 下午7:30:45
	 * @throws:
	 */
	public static Integer  getBetweenYear(String date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    	Date now=new Date();
    	String today=sdf.format(now);
    	Integer int_today=Integer.valueOf(today);
    	Integer int_date=Integer.valueOf(date);
    	return (int_today-int_date);
	}

	/**
	 * 秒转换为指定格式的日期
	 * @author: denghf
	 * @param second
	 * @param patten
	 * @return
	 */
	public static String secondToDate(long second, String patten) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(second * 1000);//转换为毫秒
		Date date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat(patten);
		String dateString = format.format(date);
		return dateString;
	}



	public static String  timestamp2Date(long time){

		time = time/1000;
		time = Math.abs(time);

		long day = time / 86400 ;
		long hh = time % 86400 / 3600 ;
		long mm = time / 60 % 60;
		long ss = time % 60;

		return day + "天" + hh + "时" + mm
				+ "分" + ss + "秒";
	}

	/**
	 * 获取时间区间中的周六 周日
	 * */
	public static List<String> findWeekDates(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> lDate = new ArrayList();
		try {
			Date dBegin = sdf.parse(startTime);
			Date dEnd = sdf.parse(endTime);
			Calendar calBegin = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calBegin.setTime(dBegin);
			Calendar calEnd = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calEnd.setTime(dEnd);
			// 此日期是否在指定日期之后
			while (dEnd.after(calBegin.getTime())) {
				int week=calBegin.get(Calendar.DAY_OF_WEEK);
				if(week == 1 || week == 7){
//                lDate.add(new Timestamp(calBegin.getTimeInMillis()));//返回Timestamp
					lDate.add(sdf.format(calBegin.getTime()));
				}
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
			}
			//判断最后一天
			int week=calEnd.get(Calendar.DAY_OF_WEEK);
			if(week == 1 || week == 7){
//            lDate.add(new Timestamp(calEnd.getTimeInMillis()));//返回Timestamp
				lDate.add(sdf.format(calEnd.getTime()));
			}
		} catch (Exception e) {
		}
		return lDate;
	}

	/**
	 * 获取区间中天数，包含头和尾
	 * */
	public static List<String> findDates(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> lDate = new ArrayList();
		try {
			Date dBegin = sdf.parse(startTime);
			Date dEnd = sdf.parse(endTime);
			Calendar calBegin = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calBegin.setTime(dBegin);
			Calendar calEnd = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calEnd.setTime(dEnd);
			// 此日期是否在指定日期之后
			while (dEnd.after(calBegin.getTime())) {
				lDate.add(sdf.format(calBegin.getTime()));
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
			}
			lDate.add(sdf.format(calEnd.getTime()));
		} catch (Exception e) {
		}
		return lDate;
	}
}
