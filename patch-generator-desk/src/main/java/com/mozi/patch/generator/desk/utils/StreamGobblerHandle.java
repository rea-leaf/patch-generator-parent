/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-desk">patch-generator-desk</a> All rights reserved.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mozi.patch.generator.desk.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author aaron
 */
public class StreamGobblerHandle implements Callable {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamGobblerHandle.class);
    InputStream is;
    String type;

    public StreamGobblerHandle(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    @Override
    public Object call() throws Exception {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (type.equals("Error")) {
                    LOGGER.error("Error   :" + line);
                } else {
                    LOGGER.info("Debug:" + line);
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("Error:", ioe);
        } catch (Exception e) {
            LOGGER.error("Error:", e);
        }
        return new Object();
    }
}
