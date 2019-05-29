/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-desk">patch-generator-desk</a> All rights reserved.
 */
package com.mozi.patch.generator.desk.appender;

import com.mozi.patch.generator.desk.utils.MainFrameConsoleUtil;

import java.io.Serializable;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
/**
 * 控制台实现
 * @author aaron
 */
@Plugin(name = "guiConsoleAppender", category = "Core", elementType = "appender", printObject = true)
public class GUIConsoleAppender extends AbstractAppender {

    /* 构造函数 */
    public GUIConsoleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions
    ) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent event) {
        final byte[] bytes = getLayout().toByteArray(event);
        writerConsole(bytes);

    }

    /* 接收配置文件中的参数 */
    @PluginFactory
    public static GUIConsoleAppender createAppender(@PluginAttribute("name") String name,
            @PluginElement("Filter") final Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("no name defined in conf.");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new GUIConsoleAppender(name, filter, layout, ignoreExceptions);
    }

    private void writerConsole(byte[] bytes) {
        if (MainFrameConsoleUtil.getConsole() != null) {
            MainFrameConsoleUtil.println(new String(bytes));
        } else {
            LOGGER.error("console控制台初始化失败！");
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
