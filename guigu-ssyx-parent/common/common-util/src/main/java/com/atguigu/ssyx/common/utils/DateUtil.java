package com.atguigu.ssyx.common.utils;

import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作工具类
 *
 */
public class DateUtil {

    private static final String dateFormat = "yyyy-MM-dd";
    private static final String timeFormat = "HH:mm:ss";

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);

    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        return sdf.format(date);

    }

    public static Date parseTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 截取比较断两个日期对象的field处的值 。
     * 如果第一个日期小于、等于、大于第二个，则对应返回负整数、0、正整数
     *
     * @param date1 第一个日期对象，非null
     * @param date2 第二个日期对象，非null
     * @param field Calendar中的阈值
     *              <p>
     *              date1 > date2  返回：1
     *              date1 = date2  返回：0
     *              date1 < date2  返回：-1
     */
    public static int truncatedCompareTo(final Date date1, final Date date2, final int field) {
        return DateUtils.truncatedCompareTo(date1, date2, field);
    }

    /**
     * 比对日期与时间大小
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean dateCompare(Date beginDate, Date endDate) {
        // endDate > beginDate
        if (DateUtil.truncatedCompareTo(beginDate, endDate, Calendar.SECOND) == 1) {
            return false;
        }
        return true;
    }

    /**
     * 比对日期与时间大小
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean timeCompare(Date beginDate, Date endDate) {
        Calendar instance1 = Calendar.getInstance();
        instance1.setTime(beginDate); //设置时间为当前时间
        instance1.set(Calendar.YEAR, 0);
        instance1.set(Calendar.MONTH, 0);
        instance1.set(Calendar.DAY_OF_MONTH, 0);

        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(endDate); //设置时间为当前时间
        instance2.set(Calendar.YEAR, 0);
        instance2.set(Calendar.MONTH, 0);
        instance2.set(Calendar.DAY_OF_MONTH, 0);
        // endDate > beginDate
        if (DateUtil.truncatedCompareTo(instance1.getTime(), instance2.getTime(), Calendar.SECOND) == 1) {
            return false;
        }
        return true;
    }

    /**
     * 获取当前时间到晚上23点59分59秒的时间间隔，单位：秒
     * @return
     */
    public static Long getCurrentExpireTimes() {
        //过期截止时间
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date()); //设置时间为当前时间
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);
        Date endTime = instance.getTime();
        //当前时间与截止时间间隔，单位：秒
        long interval = (endTime.getTime() - new Date().getTime())/1000;
        return 100*60*60*24*365L;
    }
}
