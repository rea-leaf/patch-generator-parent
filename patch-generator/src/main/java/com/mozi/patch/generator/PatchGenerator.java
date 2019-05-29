/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mozi.patch.generator.entity.PatchInfo;
import com.mozi.patch.generator.entity.ProjectInfo;
import com.mozi.patch.generator.entity.ProjectReviseMapper;
import com.mozi.patch.generator.entity.SourceMapper;
import com.mozi.patch.generator.enums.ProjectTypeEnum;
import com.mozi.patch.generator.enums.VersionManagerTypeEnum;

/**
 * 类PatchGenerator.java的实现描述：补丁生存接口
 * @author arron 2018年4月12日 下午7:19:46
 */
public interface PatchGenerator {
    public static final Logger logger = LoggerFactory.getLogger(PatchGenerator.class);

    boolean support(VersionManagerTypeEnum p0);

    void generatePatch(ProjectInfo p0, PatchInfo p1) throws Exception;

    default void copyFileToPatchDir(String fileName, ProjectInfo projectInfo, PatchInfo patchInfo) {
        if (fileName.endsWith("java")) {
            this.copyJavaFileToPatchDir(fileName, projectInfo, patchInfo);
        } else {
            this.copySimpleFileToPatchDir(fileName, projectInfo, patchInfo);
        }
    }

    default void copyJavaFileToPatchDir(String fileName, ProjectInfo projectInfo, PatchInfo patchInfo) {
        List<SourceMapper> sourceMappers;
        //SourceMapper sourceMapper;
        String pureFileName;
        String pureFileParentDir;
        String prefix;
        ProjectReviseMapper reviseMapper;
        String pureFileTargetParentDir;
        String className;
        File parentDir;
        File[] classFiles;
        File targetDir;
        //File classFile;
        //IOException e;
        sourceMappers = projectInfo.getSourceMappers();
        for (final SourceMapper sourceMapper : sourceMappers) {
            if (fileName.contains(sourceMapper.getSourceDir())) {
                pureFileName = fileName.substring(fileName.indexOf(sourceMapper.getSourceDir())
                        + sourceMapper.getSourceDir().length());
                pureFileParentDir = pureFileName.substring(0, pureFileName.lastIndexOf("/"));
                prefix = "";
                if (ProjectTypeEnum.MULTIMODULE.equals(projectInfo.getProjectType())) {
                    prefix = fileName.substring(0, fileName.indexOf(sourceMapper.getSourceDir()) + 1);
                    if (prefix.indexOf(projectInfo.getTargetBaseDir()) != -1) {
                        prefix = prefix.substring(prefix.indexOf(projectInfo.getTargetBaseDir())
                                + projectInfo.getTargetBaseDir().length());
                    }
                    reviseMapper = projectInfo.getReviseMapper();
                    if (reviseMapper != null) {
                        prefix = prefix.replaceFirst(reviseMapper.getOldPrefix(), reviseMapper.getRevisePrefix());
                    }
                }
                pureFileTargetParentDir = projectInfo.getTargetBaseDir() + prefix + sourceMapper.getTargetDir()
                        + pureFileParentDir;
                className = pureFileName.substring(pureFileName.lastIndexOf("/") + 1, pureFileName.lastIndexOf("."));
                parentDir = new File(pureFileTargetParentDir);
                if (!parentDir.exists()) {
                    PatchGenerator.logger.error(String.format("文件夹:%s 不存在", pureFileTargetParentDir));
                }
                classFiles = parentDir.listFiles(new JavaClassFileNameFilter(className));
                if (StringUtils.isNotBlank((CharSequence) prefix) && prefix.charAt(0) != '/') {
                    prefix = "/" + prefix;
                }
                targetDir = new File(patchInfo.getPatchFileDir() + prefix + sourceMapper.getPatchDir()
                        + pureFileParentDir);
                if (classFiles != null) {
                    for (final File classFile : classFiles) {
                        try {
                            FileUtils.copyFileToDirectory(classFile, targetDir);
                        } catch (IOException e) {
                            PatchGenerator.logger.error("复制文件错误", (Throwable) e);
                        }
                    }
                    break;
                }
                break;
            }
        }
    }

    default void copySimpleFileToPatchDir(String fileName, ProjectInfo projectInfo, PatchInfo patchInfo) {
        List<SourceMapper> sourceMappers;
        //SourceMapper sourceMapper;
        String pureFileName;
        String prefix;
        ProjectReviseMapper reviseMapper;
        String filePath;
        File file;
        File targetFile;
        //IOException e;
        sourceMappers = projectInfo.getSourceMappers();
        for (final SourceMapper sourceMapper : sourceMappers) {
            if (fileName.contains(sourceMapper.getSourceDir())) {
                pureFileName = fileName.substring(fileName.indexOf(sourceMapper.getSourceDir())
                        + sourceMapper.getSourceDir().length());
                prefix = "";
                if (ProjectTypeEnum.MULTIMODULE.equals(projectInfo.getProjectType())) {
                    prefix = fileName.substring(0, fileName.indexOf(sourceMapper.getSourceDir()) + 1);
                    if (prefix.indexOf(projectInfo.getTargetBaseDir()) != -1) {
                        prefix = prefix.substring(prefix.indexOf(projectInfo.getTargetBaseDir())
                                + projectInfo.getTargetBaseDir().length());
                    }
                    reviseMapper = projectInfo.getReviseMapper();
                    if (reviseMapper != null) {
                        prefix = prefix.replaceFirst(reviseMapper.getOldPrefix(), reviseMapper.getRevisePrefix());
                    }
                }
                filePath = projectInfo.getTargetBaseDir() + prefix + sourceMapper.getTargetDir() + pureFileName;
                file = new File(filePath);
                if (!file.exists()) {
                    PatchGenerator.logger.error(String.format("文件:%s 不存在", filePath));
                }
                if (StringUtils.isNotBlank((CharSequence) prefix)) {
                    prefix = "/" + prefix;
                }
                targetFile = new File(patchInfo.getPatchFileDir() + prefix + sourceMapper.getPatchDir() + pureFileName);
                try {
                    if (file.isFile()) {
                        FileUtils.copyFile(file, targetFile);
                    }
                    if (file.isDirectory()) {
                        FileUtils.copyDirectory(file, targetFile);
                    }
                } catch (IOException e) {
                    PatchGenerator.logger.error("复制文件错误", (Throwable) e);
                }
                break;
            }
        }
    }

    public static class JavaClassFileNameFilter implements FilenameFilter {
        private String className;

        public JavaClassFileNameFilter() {
        }

        public JavaClassFileNameFilter(String className) {
            this.className = className;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.equals(this.className + ".class") || name.startsWith(this.className + "$");
        }
    }
}
