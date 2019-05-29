/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-desk">patch-generator-desk</a> All rights reserved.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mozi.patch.generator.desk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间处理工具类
 * @author aaron
 */
public class DateUtil {
    /**
     * 默认时间字符串的格式
     */
    public static final String                   DEFAULT_SECOND  = "yyyy-MM-dd HH:mm:ss";
    public static final String                   DEFAULT         = "yyyy-MM-dd";
    public static final String                   SHORT           = "yyyyMMdd";
    public static final String                   SHORT_SECOND    = "yyyyMMddHHmmss";
    public static final String                   ZH_TO_DAY       = "yyyy年MM月dd日";

    private static ThreadLocal<SimpleDateFormat> dateFormatLocal = new ThreadLocal<SimpleDateFormat>() {
                                                                     protected SimpleDateFormat initialValue() {
                                                                         return new SimpleDateFormat(DEFAULT);
                                                                     }
                                                                 };
    /**
     * 获得日期字符串
     * @param pattern
     * @return 
     */
    public static String formatDateStr(String pattern) {
        SimpleDateFormat format=dateFormatLocal.get();
        format.applyPattern(pattern);
        return format.format(new Date());
    }
//    public static void main(String[] args){
//        System.out.print(DateUtil.formatDateStr(SHORT_SECOND));
//    }
}