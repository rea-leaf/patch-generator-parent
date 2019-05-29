/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator.entity;

/**
 * 类GitProjectInfo.java的实现描述：git项目配置信息
 * 
 * @author arron 2018年4月12日 下午7:17:44
 */
public class GitProjectInfo extends ProjectInfo {
    private String gitRepositoryUrl;

    public String getGitRepositoryUrl() {
        return this.gitRepositoryUrl;
    }

    public void setGitRepositoryUrl(final String gitRepositoryUrl) {
        this.gitRepositoryUrl = gitRepositoryUrl;
    }
}
