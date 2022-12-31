package com.gaoqisen.feature;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class LocalDateTimeg {

    public static void main(String[] args) {
        System.out.println("当前时间" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        System.out.println("当前时间戳: " + LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        System.out.println("当前时间戳: " + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println("当前时间戳: " + new Date().getTime());

        LocalDateTime localDateTime = LocalDateTime.now();
        // 年月日T时分秒 2020-09-21T14:39:36.162
        System.out.println(localDateTime);
        // 年月日 2020-09-21
        System.out.println(LocalDate.now());
        // 2020-09-21
        System.out.println(localDateTime.toLocalDate());
        // 时分秒 14:39:36.164
        System.out.println(LocalTime.now());
        // 14:41:41.465
        System.out.println(localDateTime.toLocalTime());
        // 增加2天
        System.out.println(localDateTime.plusDays(2).toString());


        // 时间格式化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // LocalDateTime转字符串
        System.out.println(dateTimeFormatter.format(localDateTime));
        // 时间字符串转LocalDateTime
        System.out.println(LocalDateTime.parse("2020-09-20 10:00:00", dateTimeFormatter));

        // 调整年月为指定数字
        System.out.println(localDateTime.withYear(1990).withMonth(11));
        // 本月最后一天
        // TemporalAdjusters.firstDayOfNextMonth() 下月第1天
        // TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY) 本月第1个周一
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));

        // Date转LocalDateTime
        System.out.println(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        // LocalDateTime转Date
        System.out.println(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }
}
