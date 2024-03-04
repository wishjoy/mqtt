package com;

import com.project.jnzk.utils.TimeUtil;

import java.time.Instant;
import java.time.LocalDate;

public class test {
    public static void main(String[] args) {
        System.out.println(TimeUtil.getStrYesterday(Instant.now()));
        LocalDate localDate = LocalDate.now().minusDays(70);
        System.out.println(localDate);
    }


}
