/**
 * Copyright:   北京互融时代软件有限公司
 *
 * @author: Gao Mimi
 * @version: V1.0
 * @Date: 2015年10月9日 下午12:04:20
 */
package hry.util.date;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

    public static Calendar cal = Calendar.getInstance();

    public static int getDay () {
        return cal.get(Calendar.DATE);
    }

    public static int getMonth () {
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getYear () {
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取今天起点时间
     *
     * @return
     */
    public static Date getNowStartTime () {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取今天终点时间
     * @return
     */
    public static Date getNowEndTime () {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    public static void main (String[] args) {
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            System.out.println(111);
        } finally {
            System.out.println(222);
        }

    }


}
