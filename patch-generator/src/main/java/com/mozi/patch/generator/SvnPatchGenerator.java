/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.*;

import com.mozi.patch.generator.entity.PatchInfo;
import com.mozi.patch.generator.entity.ProjectInfo;
import com.mozi.patch.generator.entity.SourceMapper;
import com.mozi.patch.generator.entity.SvnProjectInfo;
import com.mozi.patch.generator.enums.GenTypeEnum;
import com.mozi.patch.generator.enums.PathModifyTypeEnum;
import com.mozi.patch.generator.enums.SvnLogSupportTypeEnum;
import com.mozi.patch.generator.enums.VersionManagerTypeEnum;
import com.mozi.patch.generator.utils.IoUtil;
import com.mozi.patch.generator.utils.ZipUtil;

/**
 * 类SvnPatchGenerator.java的实现描述：svn项目补丁生存器
 * @author arron 2018年4月12日 下午7:19:56
 */
public class SvnPatchGenerator implements PatchGenerator {
    private static final Logger logger;

    @Override
    public boolean support(final VersionManagerTypeEnum manageType) {
        return manageType.equals(VersionManagerTypeEnum.SVN);
    }

    @Override
    public void generatePatch(final ProjectInfo projectInfo, final PatchInfo patchInfo) throws SVNException {
        if (projectInfo instanceof SvnProjectInfo) {
            final SvnProjectInfo svnProjectInfo = (SvnProjectInfo) projectInfo;
            final SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
            patchInfo.setPatchFileDir(patchInfo.getPatchFileDir() + "/" + "版本号【" + patchInfo.getStartVersion() + "-" + patchInfo.getEndVersion() + "】"
                    + DateFormatUtils.format(new Date(), "yyyy-MM-dd-hh-mm-ss"));
            final Set<String> changeFiles = new HashSet<String>();
            final Set<String> delFiles = new HashSet<String>();
            if (GenTypeEnum.LOG.equals(patchInfo.getGenType())) {
                final List<String> old = getPatchFileList(patchInfo.getPatchFile());
                doCleanDealUpdate(old, projectInfo.getSourceMappers(), changeFiles, delFiles);
            } else {
                final SVNURL url = SVNURL.parseURIEncoded(svnProjectInfo.getSvnUrl());
                svnOperationFactory.setAuthenticationManager((ISVNAuthenticationManager) BasicAuthenticationManager
                        .newInstance(svnProjectInfo.getSvnUsername(), svnProjectInfo.getSvnPassword().toCharArray()));
                final SvnLog log = svnOperationFactory.createLog();
                log.addRange(SvnRevisionRange.create(SVNRevision.create(Long.parseLong(patchInfo.getStartVersion())),
                        SVNRevision.create(Long.parseLong(patchInfo.getEndVersion()))));
                log.setDiscoverChangedPaths(true);
                log.setSingleTarget(SvnTarget.fromURL(url));
                final ISvnObjectReceiver<SVNLogEntry> svnLogReceiver = (ISvnObjectReceiver<SVNLogEntry>) new SvnPatchObjectReceiver(
                        patchInfo, changeFiles, delFiles);
                log.setReceiver(svnLogReceiver);
                log.run();
            }
            writePatchToPatchFile(changeFiles, patchInfo.getPatchFileDir(), null);
            writePatchToPatchFile(delFiles, patchInfo.getPatchFileDir(), PathModifyTypeEnum.D);
            for (final String changeFile : changeFiles) {
                this.copyFileToPatchDir(changeFile, projectInfo, patchInfo);
            }
            final File zipFile = ZipUtil.zip(patchInfo.getPatchFileDir());
            SvnPatchGenerator.logger.info("生成补丁包:{}", (Object) zipFile.getPath());
            return;
        }
        throw new RuntimeException("ProjectInfo只支持SvnProjectInfo实例");
    }

    private static String writePatchToPatchFile(final Set<String> patchFileList, final String configPatchPath,
                                                final PathModifyTypeEnum pathModifyType) {
        final Iterator<String> iterator = patchFileList.iterator();
        String svnPath;
        if (PathModifyTypeEnum.D.equals(pathModifyType)) {
            svnPath = configPatchPath + "-del.txt";
        } else {
            svnPath = configPatchPath + ".txt";
        }
        final String desFilePathStr = svnPath.substring(0, svnPath.lastIndexOf("/"));
        final File desFilePath = new File(desFilePathStr);
        if (!desFilePath.exists()) {
            desFilePath.mkdirs();
        }
        final File file = new File(svnPath);
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
            while (iterator.hasNext()) {
                writer.write(iterator.next().toString());
                writer.newLine();
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            SvnPatchGenerator.logger.error("writePatchToPatchFile()", (Throwable) e);
        } catch (IOException e2) {
            SvnPatchGenerator.logger.error("writePatchToPatchFile()", (Throwable) e2);
        } catch (Exception e3) {
            SvnPatchGenerator.logger.error("writePatchToPatchFile()", (Throwable) e3);
        } finally {
            IoUtil.closeQuietly(writer, fw);
        }
        return svnPath;
    }

    private static List<String> getPatchFileList(final String file) {
        final List<String> fileList = new ArrayList<String>();
        FileInputStream f = null;
        BufferedReader dr = null;
        try {
            f = new FileInputStream(file);
            dr = new BufferedReader(new InputStreamReader(f, "UTF-8"));
            String line;
            while ((line = dr.readLine()) != null) {
                fileList.add(line);
            }
        } catch (IOException e) {
            SvnPatchGenerator.logger.error("getPatchFileList()", (Throwable) e);
        } catch (Exception e2) {
            SvnPatchGenerator.logger.error("getPatchFileList()", (Throwable) e2);
        } finally {
            IoUtil.closeQuietly(f, dr);
            IoUtil.closeQuietly(dr);
        }
        return fileList;
    }

    private static void doCleanDealUpdate(final List<String> svnList, final List<SourceMapper> sourceMappers,
                                          final Set<String> changeFiles, final Set<String> delFiles) {
        for (int i = 0, j = svnList.size(); i < j; ++i) {
            String curr = svnList.get(i);
            final String add = SvnLogSupportTypeEnum.ADD.getType();
            final String send = SvnLogSupportTypeEnum.SEND.getType();
            final String delete = SvnLogSupportTypeEnum.DELET.getType();
            if (curr.contains(add) || curr.contains(send)) {
                if (curr.contains(add)) {
                    curr = curr.substring(curr.indexOf(add.trim()) + add.trim().length());
                } else {
                    curr = curr.substring(curr.indexOf(send.trim()) + send.trim().length());
                }
                curr = trimInnerSpaceStr(curr);
                final File checkFile = new File(curr);
                if (checkFile == null || !checkFile.exists() || !checkFile.isDirectory()) {
                    for (int k = 0, ks = sourceMappers.size(); k < ks; ++k) {
                        final int start = curr.indexOf(sourceMappers.get(k).getSourceDir());
                        if (start != -1) {
                            changeFiles.add(curr);
                        }
                    }
                }
            } else if (curr.contains(delete)) {
                curr = curr.substring(curr.indexOf(delete.trim()) + delete.trim().length());
                curr = trimInnerSpaceStr(curr);
                delFiles.add(curr);
            }
        }
    }

    private static String trimInnerSpaceStr(String str) {
        for (str = str.trim(); str.startsWith(" "); str = str.substring(1, str.length()).trim()) {
        }
        while (str.endsWith(" ")) {
            str = str.substring(0, str.length() - 1).trim();
        }
        return str;
    }

    static {
        logger = LoggerFactory.getLogger(SvnPatchGenerator.class);
    }
}
