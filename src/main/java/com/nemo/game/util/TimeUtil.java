package com.nemo.game.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class TimeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeUnit.class);

    //1分钟的秒时间
    public static final long ONE_MINUTE_IN_SECONDS = 60L;
    //1分钟毫秒时长
    public static final long ONE_MINUTE_IN_MILLISECONDS = 60L * 1000;
    //1小时的毫秒时长
    public static final long ONE_HOUR_IN_MILLISECONDS = 60L * ONE_MINUTE_IN_MILLISECONDS;
    //1天的毫秒时长
    public static final long ONE_DAY_IN_MILLISECONDS = 24L * ONE_HOUR_IN_MILLISECONDS;
    //1天的秒时长
    public static final long ONE_DAY_IN_SECONDS = 24L * 60 * 60;
    //1天的分钟长度
    public static final int ONE_DAY_IN_MIN = (int)(24L * 60);
    //1秒的时长（毫秒）
    public static final long ONE_MILLS = 1000L;

    //格式
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //获取time日零点毫秒时间
    public static long dayZeroMillsFromTime(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime dt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return dt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }




}
