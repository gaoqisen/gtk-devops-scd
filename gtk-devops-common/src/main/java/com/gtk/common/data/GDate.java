package com.gtk.common.data;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName GDate
 
 * @Date 2019-12-04
 * @Version 1.0
 */
public class GDate {
    /**
     
     * @Description //TODO 时间格式化
     * @Date 2019-12-04 09:25
     * @Param [pattern, object]
     * @return com.gtoolkit.data.GString
     **/
    public static String toFormatDate(String pattern, Object object) {
        if(StringUtils.isEmpty(pattern)){
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(object);
    }
    /**
     
     * @Description //TODO 时间格式化
     * @Date 2019-12-04 09:28 
     * @Param [pattern, d]
     * @return java.lang.String
     **/
    public static String toFormatDate(String pattern, Date d) {
        if (d == null){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(d);
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     
     * @Date 2019-12-04 13:13
     * @Param [nowTime, startTime, endTime]
     * @return boolean
     **/
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断传入时间是否为null或者是1900
     
     * @Date 2019-12-04 13:28
     * @Param [date]
     * @return boolean
     **/
    public boolean checkDate(Date date) {
        boolean flag = true;

        if (date == null || date.toString().indexOf("1900") == 0) {
            flag = false;
        }

        return flag;
    }

    /**
     * 日期按照天数增加
     
     * @Date 2019-12-04 13:28
     * @Param [oldDay, dayCount]
     * @return java.util.Date
     **/
    public static Date addDay(Date oldDay, int dayCount) {
        Calendar ctime = Calendar.getInstance();
        ctime.setTime(oldDay);
        ctime.add(Calendar.DAY_OF_MONTH, dayCount);
        return new Date(ctime.getTimeInMillis());
    }

    /**
     * 日期按照分钟数增加
     
     * @Date 2019-12-04 13:29
     * @Param [oldDay, minuteCount]
     * @return java.util.Date
     **/
    public static Date addMinute(Date oldDay, int minuteCount) {
        Calendar ctime = Calendar.getInstance();
        ctime.setTime(oldDay);
        ctime.add(Calendar.MINUTE, minuteCount);
        return new Date(ctime.getTimeInMillis());
    }

    /**
     * 日期按照秒数增加
     
     * @Date 2019-12-04 13:30
     * @Param [oldDay, Second]
     * @return java.util.Date
     **/
    public static Date addSecond(Date oldDay, int Second) {
        Calendar ctime = Calendar.getInstance();
        ctime.setTime(oldDay);
        ctime.add(Calendar.SECOND, Second);
        return new Date(ctime.getTimeInMillis());
    }

    /**
     * 日期按照豪秒数增加
     
     * @Date 2019-12-04 13:30
     * @Param [oldDay, Millisecond]
     * @return java.util.Date
     **/
    public static Date addMillisecond(Date oldDay, int Millisecond) {
        Calendar ctime = Calendar.getInstance();
        ctime.setTime(oldDay);
        ctime.add(Calendar.MILLISECOND, Millisecond);
        return new Date(ctime.getTimeInMillis());
    }

    /**
     * 格式化字符串时间为Date
     
     * @Date 2019-12-04 13:33 
     * @Param [formatStr, date]
     * @return java.util.Date
     **/
    public static Date formatDate(String formatStr, Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.parse(format.format(date));

    }
    
    /**
     * 格式化字符串时间为String
     
     * @Date 2019-12-04 13:34 
     * @Param [formatStr, date]
     * @return java.lang.String
     **/
    public static String dateFormatString(String formatStr, Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    /**
     * 格式化字符串时间为Date
     
     * @Date 2019-12-04 13:38 
     * @Param [formatStr, date]
     * @return java.util.Date
     **/
    public static Date formatDate(String formatStr, String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.parse(date);

    }

    /**
     * 格式化日期到天
     
     * @Date 2019-12-04 13:35 
     * @Param [date]
     * @return java.util.Date
     **/
    public static Date formatDate(Date date) {
        try {
            date = formatDate("yyyyMMdd", date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 格式化日期到天
     
     * @Date 2019-12-04 13:35 
     * @Param [date]
     * @return java.util.Date
     **/
    public Date formatDate(String dateStr) {
        Date date = new Date();
        try {
            date = formatDate("yyyyMMdd", dateStr);
        } catch (ParseException e) {
            // yyyyMMdd是没问题的,为防止因特殊原因报错查找写上打印函数
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 比较格式化过的时间Date
     * @Date 2019-12-04 13:39 
     * @Param [formatStr, beginDate, endDate]
     * @return 0:相等,小于0:beginDate<endDate,大于0:beginDate>endDate
     **/
    public int compareDate(String formatStr, Date beginDate, Date endDate) throws ParseException {
        return formatDate(formatStr, beginDate).compareTo(formatDate(formatStr, endDate));
    }

    /**
     * 比较经过格式化到天的时间
     * @Date 2019-12-04 13:39 
     * @Param [beginDate,endDate]
     * @return 0:相等,小于0:beginDate<endDate,大于0:beginDate>endDate
     */
    public int compareDate(Date beginDate, Date endDate) {
        int result = 0;
        try {
            result = compareDate("yyyyMMdd", beginDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
