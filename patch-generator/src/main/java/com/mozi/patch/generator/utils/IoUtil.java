/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator.utils;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类IoUtil.java的实现描述：io 工具类
 * 
 * @author arron 2018年4月12日 下午7:11:39
 */
public class IoUtil {
    private static final Logger logger;

    /**
     * 关闭一个或多个流对象
     * 
     * @param closeables 可关闭的流对象列表
     * @throws IOException
     */
    private static void close(final Closeable... closeables) {
        if (closeables != null) {
            for (final Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        IoUtil.logger.error("close()", (Throwable) e);
                    }
                }
            }
        }
    }

    /**
     * 关闭一个或多个流对象
     * 
     * @param closeables 可关闭的流对象列表
     */
    public static void closeQuietly(final Closeable... closeables) {
        close(closeables);
    }

    static {
        logger = LoggerFactory.getLogger(IoUtil.class);
    }
}
