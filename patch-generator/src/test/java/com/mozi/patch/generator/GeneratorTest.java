package com.mozi.patch.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNException;

import com.mozi.patch.generator.entity.*;
import com.mozi.patch.generator.enums.GenTypeEnum;
import com.mozi.patch.generator.enums.ProjectTypeEnum;
import com.mozi.patch.generator.enums.VersionManagerTypeEnum;


public class GeneratorTest {
    @Test
    public void testSvnGenerator() throws SVNException {
        final SvnProjectInfo projectInfo = new SvnProjectInfo();
        projectInfo.setProjectName("ump20170420_chery_pc");
        projectInfo.setProjectType(ProjectTypeEnum.SINGLEMODULE);
        projectInfo.setTargetBaseDir("D:/SpringRooWorkSpace/ump20170420_chery_pc/");
        projectInfo.setSvnPassword("xxxx");
        projectInfo.setSvnUsername("xxxx");
        projectInfo.setVersionManagerTypeEnum(VersionManagerTypeEnum.SVN);
        projectInfo.setSvnUrl("https://xxxxx.xx/svn/ump/tags/ump20170420_chery_pc");
        projectInfo.setReviseMapper(new ProjectReviseMapper("/tags", ""));
        final List<SourceMapper> sourceMappers = new ArrayList<SourceMapper>();
        final SourceMapper javaMapper = new SourceMapper("/src/main/java", "target/classes", "/WEB-INF/classes");
        final SourceMapper resourceMapper = new SourceMapper("/src/main/resources", "target/classes",
                "/WEB-INF/classes");
        final SourceMapper webAppsMapper = new SourceMapper("/src/main/webapp", "src/main/webapp", "");
        sourceMappers.add(javaMapper);
        sourceMappers.add(resourceMapper);
        sourceMappers.add(webAppsMapper);
        projectInfo.setSourceMappers(sourceMappers);
        final PatchInfo patchInfo = new PatchInfo();
        patchInfo.setStartVersion("14431");
        patchInfo.setEndVersion("14434");
        final String oldExcludeRevisions = "14432,14433";
        List<String> excludeRevisions = new ArrayList<String>();
        if (StringUtils.isNotBlank((CharSequence) oldExcludeRevisions)) {
            excludeRevisions = Arrays.asList(oldExcludeRevisions.split(","));
        }
        patchInfo.setExcludeRevisions(excludeRevisions);
        patchInfo.setPatchFileDir("D:/update/ump20170420_chery_pc");
        GeneratePatchExecutor.execute((ProjectInfo) projectInfo, patchInfo);
    }

    @Test
    public void testSvnLogGenerator() throws SVNException {
        final SvnProjectInfo projectInfo = new SvnProjectInfo();
        projectInfo.setProjectName("ump20170420_chery_pc");
        projectInfo.setProjectType(ProjectTypeEnum.SINGLEMODULE);
        projectInfo.setTargetBaseDir("D:/SpringRooWorkSpace/ump20170420_chery_pc/");
        projectInfo.setVersionManagerTypeEnum(VersionManagerTypeEnum.SVN);
        final List<SourceMapper> sourceMappers = new ArrayList<SourceMapper>();
        final SourceMapper javaMapper = new SourceMapper("/src/main/java", "target/classes", "/WEB-INF/classes");
        final SourceMapper resourceMapper = new SourceMapper("/src/main/resources", "target/classes",
                "/WEB-INF/classes");
        final SourceMapper webAppsMapper = new SourceMapper("/src/main/webapp", "src/main/webapp", "");
        sourceMappers.add(javaMapper);
        sourceMappers.add(resourceMapper);
        sourceMappers.add(webAppsMapper);
        projectInfo.setSourceMappers(sourceMappers);
        final PatchInfo patchInfo = new PatchInfo();
        patchInfo.setGenType(GenTypeEnum.LOG);
        patchInfo.setPatchFileDir("D:/update/ump20170420_chery_pc");
        patchInfo.setPatchFile("E:/svn_aaron_update2016-11-10.txt");
        GeneratePatchExecutor.execute((ProjectInfo) projectInfo, patchInfo);
    }

    @Test
    public void testGitGenerator() throws SVNException {
        final GitProjectInfo projectInfo = new GitProjectInfo();
        projectInfo.setProjectName("emsite");
        projectInfo.setProjectType(ProjectTypeEnum.MULTIMODULE);
        projectInfo.setTargetBaseDir("D:/Users/Administrator/git/emsite-parent/");
        projectInfo.setVersionManagerTypeEnum(VersionManagerTypeEnum.GIT);
        projectInfo.setGitRepositoryUrl("D:/Users/Administrator/git/emsite-parent/.git");
        final List<SourceMapper> sourceMappers = new ArrayList<SourceMapper>();
        final SourceMapper javaMapper = new SourceMapper("/src/main/java", "target/classes", "/WEB-INF/classes");
        final SourceMapper resourceMapper = new SourceMapper("/src/main/resources", "target/classes",
                "/WEB-INF/classes");
        final SourceMapper webAppsMapper = new SourceMapper("/src/main/webapp", "src/main/webapp", "");
        sourceMappers.add(javaMapper);
        sourceMappers.add(resourceMapper);
        sourceMappers.add(webAppsMapper);
        projectInfo.setSourceMappers(sourceMappers);
        final PatchInfo patchInfo = new PatchInfo();
        patchInfo.setStartVersion("757212d");
        patchInfo.setEndVersion("544515f");
        patchInfo.setPatchFileDir("D:/update/emsite-parent");
        GeneratePatchExecutor.execute((ProjectInfo) projectInfo, patchInfo);
    }

    @Test
    public void testGitLogGenerator() throws SVNException {
        final GitProjectInfo projectInfo = new GitProjectInfo();
        projectInfo.setProjectName("emsite");
        projectInfo.setProjectType(ProjectTypeEnum.MULTIMODULE);
        projectInfo.setTargetBaseDir("D:/Users/Administrator/git/emsite-parent/");
        projectInfo.setVersionManagerTypeEnum(VersionManagerTypeEnum.GIT);
        final List<SourceMapper> sourceMappers = new ArrayList<SourceMapper>();
        final SourceMapper javaMapper = new SourceMapper("/src/main/java", "target/classes", "/WEB-INF/classes");
        final SourceMapper resourceMapper = new SourceMapper("/src/main/resources", "target/classes",
                "/WEB-INF/classes");
        final SourceMapper webAppsMapper = new SourceMapper("/src/main/webapp", "src/main/webapp", "");
        sourceMappers.add(javaMapper);
        sourceMappers.add(resourceMapper);
        sourceMappers.add(webAppsMapper);
        projectInfo.setSourceMappers(sourceMappers);
        final PatchInfo patchInfo = new PatchInfo();
        patchInfo.setGenType(GenTypeEnum.LOG);
        patchInfo.setPatchFileDir("D:/update/emsite-parent");
        patchInfo.setPatchFile("E:/git-aaron-update.txt");
        GeneratePatchExecutor.execute((ProjectInfo) projectInfo, patchInfo);
    }
}
