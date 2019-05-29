/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-desk">patch-generator-desk</a> All rights reserved.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mozi.patch.generator.desk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 文件处理工具类
 * @author aaron
 */
public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
  
    /**
     * 使用文件通道的方式复制文件
     * 
     * @param s 源文件
     * @param t 复制到的新文件
     * @throws IOException
     */

    public static void fileChannelCopy(File s, File t) throws IOException {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
           LOGGER.error("使用文件通道的方式复制文件报错");
        } finally {
            if(fi!=null){
                fi.close();
            }
            if(in!=null){
                in.close();
            }
            if(fo!=null){
                fo.close();
            }
            if(out!=null){
                out.close();
            }
            //s.delete();
        }

    }
}
