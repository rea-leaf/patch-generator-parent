/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator.entity;

import java.util.List;

import com.mozi.patch.generator.enums.ProjectTypeEnum;
import com.mozi.patch.generator.enums.VersionManagerTypeEnum;

/**
 * 类ProjectInfo.java的实现描述：项目配置信息
 * 
 * @author arron 2018年4月12日 下午7:18:03
 */
public class ProjectInfo {
    private String                 projectName;
    private String                 targetBaseDir;
    private VersionManagerTypeEnum versionManagerTypeEnum;
    private List<SourceMapper>     sourceMappers;
    private ProjectTypeEnum projectType;
    private ProjectReviseMapper    reviseMapper;

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public String getTargetBaseDir() {
        return this.targetBaseDir;
    }

    public void setTargetBaseDir(final String targetBaseDir) {
        this.targetBaseDir = targetBaseDir;
    }

    public List<SourceMapper> getSourceMappers() {
        return this.sourceMappers;
    }

    public void setSourceMappers(final List<SourceMapper> sourceMappers) {
        this.sourceMappers = sourceMappers;
    }

    public VersionManagerTypeEnum getVersionManagerTypeEnum() {
        return this.versionManagerTypeEnum;
    }

    public void setVersionManagerTypeEnum(final VersionManagerTypeEnum versionManagerTypeEnum) {
        this.versionManagerTypeEnum = versionManagerTypeEnum;
    }

    public ProjectTypeEnum getProjectType() {
        return this.projectType;
    }

    public void setProjectType(final ProjectTypeEnum projectType) {
        this.projectType = projectType;
    }

    public ProjectReviseMapper getReviseMapper() {
        return this.reviseMapper;
    }

    public void setReviseMapper(final ProjectReviseMapper reviseMapper) {
        this.reviseMapper = reviseMapper;
    }
}
