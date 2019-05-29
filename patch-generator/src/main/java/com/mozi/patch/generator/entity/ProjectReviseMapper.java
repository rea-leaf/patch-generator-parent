/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator.entity;

/**
 * 类ProjectReviseMapper.java的实现描述：TODO 类实现描述
 * 
 * @author arron 2018年4月12日 下午7:18:12
 */
public class ProjectReviseMapper {
    private String oldPrefix;
    private String revisePrefix;

    public ProjectReviseMapper() {
    }

    public ProjectReviseMapper(final String oldPrefix, final String revisePrefix) {
        this.oldPrefix = oldPrefix;
        this.revisePrefix = revisePrefix;
    }

    public String getOldPrefix() {
        return this.oldPrefix;
    }

    public void setOldPrefix(final String oldPrefix) {
        this.oldPrefix = oldPrefix;
    }

    public String getRevisePrefix() {
        return this.revisePrefix;
    }

    public void setRevisePrefix(final String revisePrefix) {
        this.revisePrefix = revisePrefix;
    }
}
