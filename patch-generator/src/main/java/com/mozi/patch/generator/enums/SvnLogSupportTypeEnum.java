/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator.enums;

/**
 * 类SvnLogSupportTypeEnum.java的实现描述：SVN日志处理支持的类型
 * 
 * @author arron 2018年4月12日 下午7:19:08
 */
public enum SvnLogSupportTypeEnum {
    ADD(" Adding  "),
    SEND(" Sending  "),
    DELET(" Deleting  ");

    private String type;

    private SvnLogSupportTypeEnum(final String type) {
        this.type = type;
    }

    public static SvnLogSupportTypeEnum getSvnLogSupportTypeEnumByType(final String type) {
        for (final SvnLogSupportTypeEnum item : values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }

    public String getType() {
        return this.type;
    }
}
