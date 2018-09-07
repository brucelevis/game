package com.nemo.test;


import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) {
        double a = 100;
        int b = 12929;
        int c = (int) (b * (a / 10000));
        System.out.println(c);

        String s = "Hello World";
        String ss = StringUtils.uncapitalize(s);
        System.out.println(ss);


    }

}
