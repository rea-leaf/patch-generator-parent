/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator.entity;

/**
 * 类SvnProjectInfo.java的实现描述：SVN项目配置信息
 * 
 * @author arron 2018年4月12日 下午7:18:27
 */
public class SvnProjectInfo extends ProjectInfo {
    private String svnUrl;
    private String svnUsername;
    private String svnPassword;

    public String getSvnUrl() {
        return this.svnUrl;
    }

    public void setSvnUrl(final String svnUrl) {
        this.svnUrl = svnUrl;
    }

    public String getSvnUsername() {
        return this.svnUsername;
    }

    public void setSvnUsername(final String svnUsername) {
        this.svnUsername = svnUsername;
    }

    public String getSvnPassword() {
        return this.svnPassword;
    }

    public void setSvnPassword(final String svnPassword) {
        this.svnPassword = svnPassword;
    }
}
