/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator.entity;

/**
 * 类SourceMapper.java的实现描述：源码文件补丁映射
 * 
 * @author arron 2018年4月12日 下午7:18:20
 */
public class SourceMapper {
    private String sourceDir;
    private String targetDir;
    private String patchDir;

    public SourceMapper() {
    }

    public SourceMapper(final String sourceDir, final String targetDir, final String patchDir) {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
        this.patchDir = patchDir;
    }

    public String getSourceDir() {
        return this.sourceDir;
    }

    public void setSourceDir(final String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getTargetDir() {
        return this.targetDir;
    }

    public void setTargetDir(final String targetDir) {
        this.targetDir = targetDir;
    }

    public String getPatchDir() {
        return this.patchDir;
    }

    public void setPatchDir(final String patchDir) {
        this.patchDir = patchDir;
    }
}
