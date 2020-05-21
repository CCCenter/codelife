package com.codelife.cloud.question.controller;

import java.util.Date;

public class test {
    public static void main(String[] args) {
        System.out.println(new Date());
        System.out.println(new Date().getTime() - 60 * 60 * 24 * 7L * 1000);
        System.out.println(1589558677000L > new Date().getTime() - 60 * 60 * 24 * 7L * 1000);
    }
}
