/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator.entity;

import java.util.Date;
import java.util.List;

import com.mozi.patch.generator.enums.GenTypeEnum;

/**
 * 类PatchInfo.java的实现描述：补丁配置信息
 * 
 * @author arron 2018年4月12日 下午7:17:53
 */
public class PatchInfo {
    private GenTypeEnum genType;
    private String       startVersion;
    private String       endVersion;
    private List<String> includeRevisions;
    private List<String> excludeRevisions;
    private Date         startTime;
    private Date         endTime;
    private String       checkAuthor;
    private String       patchFileDir;
    private String       patchFile;

    public GenTypeEnum getGenType() {
        return this.genType;
    }

    public void setGenType(final GenTypeEnum genType) {
        this.genType = genType;
    }

    public String getStartVersion() {
        return this.startVersion;
    }

    public void setStartVersion(final String startVersion) {
        this.startVersion = startVersion;
    }

    public String getEndVersion() {
        return this.endVersion;
    }

    public void setEndVersion(final String endVersion) {
        this.endVersion = endVersion;
    }

    public List<String> getIncludeRevisions() {
        return this.includeRevisions;
    }

    public void setIncludeRevisions(final List<String> includeRevisions) {
        this.includeRevisions = includeRevisions;
    }

    public List<String> getExcludeRevisions() {
        return this.excludeRevisions;
    }

    public void setExcludeRevisions(final List<String> excludeRevisions) {
        this.excludeRevisions = excludeRevisions;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    public String getCheckAuthor() {
        return this.checkAuthor;
    }

    public void setCheckAuthor(final String checkAuthor) {
        this.checkAuthor = checkAuthor;
    }

    public String getPatchFileDir() {
        return this.patchFileDir;
    }

    public void setPatchFileDir(final String patchFileDir) {
        this.patchFileDir = patchFileDir;
    }

    public String getPatchFile() {
        return this.patchFile;
    }

    public void setPatchFile(final String patchFile) {
        this.patchFile = patchFile;
    }
}
