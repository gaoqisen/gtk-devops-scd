package com.gaoqisen.test;

import org.openjdk.jol.info.ClassLayout;

import java.math.BigDecimal;

public class Sync {
    public static void main(String[] args) {

        System.out.println(new BigDecimal("100.000").stripTrailingZeros().toPlainString());
        Object o = new Object();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

}
