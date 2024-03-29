package com.gh.common.service.impl;

import com.gh.common.service.DateUtils;
import com.gh.common.toolsclass.FinalProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期时间
 */
public class DatetimeUtilsImpl<T> implements DateUtils<T> {

    /**
     * 获取指定格式的SimpleDateFormat对象
     * @param format 格式
     * @return
     */
    public SimpleDateFormat getSimpleDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 获取指定格式的当前日期时间
     *
     * @param format 格式，例：yyyy-MM-dd
     * @return 2020-12-31
     */
    public String getDateTime(String format) {
        return getSimpleDateFormat(format).format(new Date());
    }

    /**
     * 根据指定日期时间和指定格式获取日期时间
     *
     * @param datetime 指定日期时间，支持Date、String、Long类型
     * @param format   日期格式 例：yyyy-MM-dd HH:mm:ss
     * @return 2020-12-31 00:00:00
     * @throws ParseException 日期格式转换异常
     */
    public String getFormattedDateTime(T datetime, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = "";
        if (datetime instanceof Date) {
            result = getFormattedDateTime((Date) datetime, format);
        } else if (datetime instanceof String) {
            result = sdf.format(sdf.parse(datetime.toString()));
        } else if (datetime instanceof Long) {
            result = sdf.format(new Date(Long.parseLong(datetime.toString())));
        }
        return result;
    }

    /**
     * 根据指定日期时间和指定格式获取日期时间
     *
     * @param datetime 指定日期时间，仅支持Date
     * @param format   日期格式 例：yyyy-MM-dd HH:mm:ss
     * @return 2020-12-31 00:00:00
     * @throws ParseException 日期格式转换异常
     */
    private String getFormattedDateTime(Date datetime, String format) {
        return getSimpleDateFormat(format).format(datetime);
    }

    /**
     * 获取当前日期
     *
     * @return 2020-12-31
     */
    public String getDate() {
        return getDateTime("yyyy-MM-dd");
    }

    /**
     * 获取当前时间
     *
     * @return 12:00:00
     */
    public String getTime() {
        return getDateTime("HH:mm:ss");
    }

    /**
     * 获取当前日期时间
     *
     * @return 2020-12-31 00:00:00
     */
    public String getDateTime() {
        return getDateTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前年份
     *
     * @return 2020
     */
    public String getYear() {
        return getDateTime("yyyy");
    }

    /**
     * 获取指定日期所在的年份
     *
     * @param datetime 指定日期，支持Date、String、Long类型
     * @return 2020
     * @throws ParseException 日期格式转换异常
     */
    public String getYear(T datetime) throws ParseException {
        return getFormattedDateTime(datetime, "yyyy");
    }

    /**
     * 获取当前月份
     *
     * @return 12
     */
    public String getMonth() {
        return getDateTime("MM");
    }

    /**
     * 获取指定日期所在的月份
     *
     * @param datetime 指定日期，支持Date、String、Long类型
     * @return 12
     * @throws ParseException 日期格式转换异常
     */
    public String getMonth(T datetime) throws ParseException {
        return getFormattedDateTime(datetime, "MM");
    }

    /**
     * 获取当前日
     *
     * @return 31
     */
    public String getDay() {
        return getDateTime("dd");
    }

    /**
     * 获取指定日期为多少号
     *
     * @param datetime 指定日期，支持Date、String、Long类型
     * @return 31
     * @throws ParseException 日期格式转换异常
     */
    public String getDay(T datetime) throws ParseException {
        return getFormattedDateTime(datetime, "dd");
    }

    /**
     * 获取当前日期为星期几
     *
     * @return 星期一
     */
    public String getWeek() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return getWeek(c);
    }

    /**
     * 获取指定日期为星期几
     *
     * @param datetime 指定日期，支持Date、String、Long类型
     * @return 星期一
     */
    public String getWeek(T datetime) throws ParseException {
        Calendar c = Calendar.getInstance();
        String formattedDateTime = getFormattedDateTime(datetime, FinalProperties.FORMAT_DATE);
        c.setTime(getSimpleDateFormat(FinalProperties.FORMAT_DATE).parse(formattedDateTime));
        return getWeek(c);
    }

    private String getWeek(Calendar c) {
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        String weekDay = "";
        switch (dayForWeek) {
            case 1:
                weekDay = "星期一";
                break;
            case 2:
                weekDay = "星期二";
                break;
            case 3:
                weekDay = "星期三";
                break;
            case 4:
                weekDay = "星期四";
                break;
            case 5:
                weekDay = "星期五";
                break;
            case 6:
                weekDay = "星期六";
                break;
            case 7:
                weekDay = "星期日";
                break;
        }
        return weekDay;
    }

    /**
     * 获取当前月第一天的日期
     *
     * @return 2020-12-01
     */
    public String getMonthBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        return getFormattedDateTime(date, FinalProperties.FORMAT_DATE);
    }

    /**
     * 获取当月最后一天的日期
     *
     * @return 2020-12-31
     */
    public String getMonthEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = calendar.getTime();
        return getFormattedDateTime(date, FinalProperties.FORMAT_DATE);
    }

    /**
     * 获取某月第一天的日期
     *
     * @param amount 当amount为0时，为本月，-1时为上月，1时为下月
     * @return 例：2020-12-01
     */
    public String getMonthBegin(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, amount);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        return getFormattedDateTime(date, FinalProperties.FORMAT_DATE);
    }

    /**
     * 获取某月最后一天的日期
     *
     * @param amount 当amount为0时，为本月，-1时为上月，1时为下月
     * @return 例：2020-12-31
     */
    public String getMonthEnd(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, amount);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = calendar.getTime();
        return getFormattedDateTime(date, FinalProperties.FORMAT_DATE);
    }

    /**
     * 获取指定日期所在月份的最后一天的日期
     * @param datetime 指定日期时间，支持Date、String、Long类型
     * @param format 日期格式 例：yyyy-MM-dd HH:mm:ss
     * @return 2021-01-26
     * @throws ParseException
     */
    public String getMonthEndByDateTime(T datetime, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = "";
        if (datetime instanceof Date) {
            result = getFormattedDateTime((Date) datetime, format);
        } else if (datetime instanceof String) {
            result = sdf.format(sdf.parse(datetime.toString()));
        } else if (datetime instanceof Long) {
            result = sdf.format(new Date(Long.parseLong(datetime.toString())));
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getStringToDate(result, format));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = calendar.getTime();
        return getFormattedDateTime(date, format);
    }

    /**
     * 获取指定日期所在月份上月、当月或下月的最后一天的日期
     * @param datetime 指定日期时间，支持Date
     * @param format 日期格式 例：yyyy-MM-dd HH:mm:ss
     * @param amount 当amount为0时，为当月，-1时为上月，1时为下月
     * @return
     * @throws ParseException
     */
    public String getMonthEndByDateTime(T datetime, String format, int amount) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = "";
        if (datetime instanceof Date) {
            result = getFormattedDateTime((Date) datetime, format);
        } else if (datetime instanceof String) {
            result = sdf.format(sdf.parse(datetime.toString()));
        } else if (datetime instanceof Long) {
            result = sdf.format(new Date(Long.parseLong(datetime.toString())));
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getStringToDate(result, format));
        calendar.add(Calendar.MONTH, amount);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date date = calendar.getTime();
        return getFormattedDateTime(date, format);
    }

    /**
     * 获取指定日期所在月份的第一天的日期
     * @param datetime 指定日期时间，支持Date、String、Long类型
     * @param format 日期格式 例：yyyy-MM-dd HH:mm:ss
     * @return 2021-01-01
     * @throws ParseException
     */
    public String getMonthBeginByDateTime(T datetime, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = "";
        if (datetime instanceof Date) {
            result = getFormattedDateTime((Date) datetime, format);
        } else if (datetime instanceof String) {
            result = sdf.format(sdf.parse(datetime.toString()));
        } else if (datetime instanceof Long) {
            result = sdf.format(new Date(Long.parseLong(datetime.toString())));
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getStringToDate(result, format));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        return getFormattedDateTime(date, format);
    }

    /**
     * 获取指定日期所在月份上月、当月或下月的第一天的日期
     * @param datetime 指定日期时间，支持Date、String、Long类型
     * @param format 日期格式 例：yyyy-MM-dd HH:mm:ss
     * @param amount 当amount为0时，为当月，-1时为上月，1时为下月
     * @return 2021-01-01
     * @throws ParseException
     */
    public String getMonthBeginByDateTime(T datetime, String format, int amount) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = "";
        if (datetime instanceof Date) {
            result = getFormattedDateTime((Date) datetime, format);
        } else if (datetime instanceof String) {
            result = sdf.format(sdf.parse(datetime.toString()));
        } else if (datetime instanceof Long) {
            result = sdf.format(new Date(Long.parseLong(datetime.toString())));
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getStringToDate(result, format));
        calendar.add(Calendar.MONTH, amount);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        return getFormattedDateTime(date, format);
    }

    /**
     * 获取几几年的第几周的第一天日期和最后一天日期
     *
     * @param year 几几年，例：2020
     * @param week 第几个星期，例：1
     * @return 例：{
     * "beginDate": "2019-12-30",
     * "endDate": "2020-01-05"
     * }
     */
    public Map<String, String> getWeekDatesByYearAndWeek(Integer year, Integer week) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        String beginDate = getFormattedDateTime(cal.getTime(), FinalProperties.FORMAT_DATE);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        String endDate = getFormattedDateTime(cal.getTime(), FinalProperties.FORMAT_DATE);
        Map<String, String> map = new HashMap<>();
        map.put("beginDate", beginDate);
        map.put("endDate", endDate);
        return map;
    }

    /**
     * 获取指定日期日期是几几年的第几周,及该周的第一天日期和最后一天日期
     *
     * @param datetime 指定日期时间，支持Date、String、Long类型
     * @return 例：{
     * "beginDate": "2020-12-28",
     * "week": 1,
     * "year": 2021,
     * "endDate": "2021-01-03"
     * }
     */
    public Map<String, Object> getWeekDatesByDatetime(T datetime) throws ParseException {
        String formattedDateTime = getFormattedDateTime(datetime, FinalProperties.FORMAT_DATE);
        Date date = new SimpleDateFormat(FinalProperties.FORMAT_DATE).parse(formattedDateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);    //设置周一为一周的第一天
        calendar.setTime(date);
        Calendar cal = (Calendar) calendar.clone();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String beginDate = getFormattedDateTime(cal.getTime(), FinalProperties.FORMAT_DATE);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        String str = getFormattedDateTime(date, FinalProperties.FORMAT_DATE);
        String[] arr = str.split("-");
        if (arr[1].equalsIgnoreCase("12") && week == 1) {
            year++;
        }
        cal.add(Calendar.DATE, 6);
        String endDate = getFormattedDateTime(cal.getTime(), FinalProperties.FORMAT_DATE);
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("week", week);
        map.put("beginDate", beginDate);
        map.put("endDate", endDate);
        return map;
    }

    /**
     * 获取指定日期处于所在年份的第几周
     *
     * @param datetime 指定日期时间，支持Date、String、Long类型
     * @return 例：1
     */
    public int getWeekNumberByDatetime(T datetime) throws ParseException {
        String formattedDateTime = getFormattedDateTime(datetime, FinalProperties.FORMAT_DATE);
        Date date = new SimpleDateFormat(FinalProperties.FORMAT_DATE).parse(formattedDateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 数字日期转中文日期
     *
     * @param datetime datetime 指定日期时间，支持Date、String、Long类型
     * @return
     * @throws ParseException
     */
    public String dateToCNDate(T datetime) throws ParseException {
        String date = getFormattedDateTime(datetime, FinalProperties.FORMAT_DATE);
        String[] str = date.split("-");
        String[][] s = new String[3][4];
        String text = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < str[i].length(); j++) {
                s[i][j] = str[i].substring(j, j + 1);
                if (i == 0) {
                    text += numberCN(s[i][j]);
                }
            }
        }
        text += "年";
        if (Integer.parseInt(s[1][0]) == 0) {
            text += numberCN(s[1][1]);
        } else {
            if (Integer.parseInt(s[1][0]) == 1) {
                if (Integer.parseInt(s[1][1]) == 0) {
                    text += "十";
                } else {
                    text += "十" + numberCN(s[1][1]);
                }
            } else {
                text += numberCN(s[1][0]) + "十" + numberCN(s[1][1]);
            }
        }
        text += "月";

        if (Integer.parseInt(s[2][0]) == 0) {
            text += numberCN(s[2][1]);
        } else {
            if (Integer.parseInt(s[2][0]) == 1) {
                if (Integer.parseInt(s[2][1]) == 0) {
                    text += "十";
                } else {
                    text += "十" + numberCN(s[2][1]);
                }
            } else {
                text += numberCN(s[2][0]) + "十" + numberCN(s[2][1]);
            }
        }
        text += "日";
        return text;
    }

    private String numberCN(String str) {
        int num = Integer.parseInt(str);
        String s = "";
        switch (num) {
            case 0:
                s = "零";
                break;
            case 1:
                s = "一";
                break;
            case 2:
                s = "二";
                break;
            case 3:
                s = "三";
                break;
            case 4:
                s = "四";
                break;
            case 5:
                s = "五";
                break;
            case 6:
                s = "六";
                break;
            case 7:
                s = "七";
                break;
            case 8:
                s = "八";
                break;
            case 9:
                s = "九";
                break;
        }
        return s;
    }

    /**
     * 计算两个日期之间的相差多长时间
     * @param beginDatetime 开始时间 例：1609837425000l
     * @param endDatetime 结束时间 例：1609923825000l
     * @param unit 计算结果单位，0：年，1：月，2：天，3：小时，4：分钟，5：秒，6：毫秒
     * @return 1.0（天）
     */
    public float datetimeDiff(long beginDatetime, long endDatetime, int unit) {
        return diff(endDatetime - beginDatetime, unit);
    }

    private float diff(long time, int unit) {
        float result = 0f;
        switch (unit) {
            case 0:
                // 年
                result = time / 1000f / 60 / 60 / 24 / 365;
                break;
            case 1:
                // 月
                result = time / 1000f / 60 / 60 / 24 / 30;
                break;
            case 2:
                // 天
                result = time / 1000f / 60 / 60 / 24;
                break;
            case 3:
                // 小时
                result = time / 1000f / 60 / 60;
                break;
            case 4:
                // 分钟
                result = time / 1000f / 60;
                break;
            case 5:
                // 秒
                result = time / 1000f;
                break;
            case 6:
                // 毫秒
                result = time;
                break;
        }
        return result;
    }

    /**
     * 计算日期加减几天之后的日期
     *
     * @param date 开始时间 2021-01-10 22:15:43
     * @param day 几天之前或几天之后，-1为一天前，1为一天后
     * @param returnValueFormat 返回日期格式
     * @return 例：2021-01-11 22:15:43
     */
    public String getDatetimeAddOrMinusByDay(Date date, int day, String returnValueFormat) {
        return datetimeAddOrMinus(date, Calendar.DATE, day, returnValueFormat);
    }

    /**
     * 计算日期加减几月之后的日期
     *
     * @param date 开始时间 2021-01-10 22:15:43
     * @param month 几月之前或几月之后，-1为一月前，1为一月后
     * @param returnValueFormat 返回日期格式
     * @return 例：2021-01-11 22:15:43
     */
    public String getDatetimeAddOrMinusByMonth(Date date, int month, String returnValueFormat) {
        return datetimeAddOrMinus(date, Calendar.MONTH, month, returnValueFormat);
    }

    private String datetimeAddOrMinus(Date date, int unit, int amount, String returnValueFormat){
        Calendar calendar = Calendar.getInstance(); // new一个Calendar类,把Date放进去
        calendar.setTime(date);
        calendar.add(unit, amount);
        return getFormattedDateTime(calendar.getTime(), returnValueFormat);
    }

    /**
     * 将时间戳转为指定日期格式的字符串
     *
     * @param timestamp 需要转换的时间戳 例：1611241077000
     * @param returnValueFormat 返回日期格式 例："yyyy-MM-dd"或"yyyy-MM-dd HH:mm:ss"等
     * @return 2021-01-21 22:57:57
     */
    public String getTimeStampToString(long timestamp, String returnValueFormat) {
        return getSimpleDateFormat(returnValueFormat).format(new Date(timestamp));
    }

    /**
     * 将指定日期格式的字符串转为时间戳
     *
     * @param dateTime 需要转换的时间戳 例：2021-01-21 22:57:57
     * @param paramsValueFormat 返回日期格式 例："yyyy-MM-dd"或"yyyy-MM-dd HH:mm:ss"等
     * @return 1611241077000
     */
    public long getStringToTimeStamp(String dateTime, String paramsValueFormat) throws ParseException {
        return getSimpleDateFormat(paramsValueFormat).parse(dateTime).getTime();
    }

    /**
     * 将指定日期格式的字符串转为Date
     * @param dateTime 日期
     * @param paramsValueFormat 格式
     * @throws ParseException
     */
    public Date getStringToDate(String dateTime, String paramsValueFormat) throws ParseException {
        return getSimpleDateFormat(paramsValueFormat).parse(dateTime);
    }

}
