/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator.enums;

/**
 * 类PathModifyTypeEnum.java的实现描述：SVN路径变更枚举类
 * 
 * @author arron 2018年4月12日 下午7:18:42
 */
public enum PathModifyTypeEnum {
    M('M'),
    A('A'),
    D('D'),
    R('R');

    private char value;

    private PathModifyTypeEnum(final char value) {
        this.value = value;
    }

    public char getValue() {
        return this.value;
    }
}
