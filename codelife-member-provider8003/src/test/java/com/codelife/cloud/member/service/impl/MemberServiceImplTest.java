package com.codelife.cloud.member.service.impl;

import com.codelife.cloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MemberServiceImplTest {

    @Autowired
    private static RedisUtil redisUtil;

    private static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println("cl_" + (int)(Math.random() *10000000));

    }

}