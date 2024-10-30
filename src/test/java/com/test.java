package com;

import com.project.jnzk.utils.TimeUtil;

import java.time.*;

public class test {
    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(zonedDateTime);
        System.out.println(TimeUtil.getStrYesterday(Instant.now()));
        LocalDate localDate = LocalDate.now().minusDays(70);
        System.out.println(localDate);
    }


}
