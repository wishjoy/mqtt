package com.project.collection.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {
    /**
     * 获取当前时间，东八区
     * @return
     */
    public static Instant getInstant() {
        return Instant.now().atZone(ZoneId.of("Asia/Shanghai")).toInstant();
    }

    public static String getStrYesterday(Instant instant){
        return  instant
                .atZone(ZoneId.of("Asia/Shanghai"))
                .toLocalDate().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
