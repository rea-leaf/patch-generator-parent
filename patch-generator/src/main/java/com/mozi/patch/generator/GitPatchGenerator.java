/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mozi.patch.generator.entity.GitProjectInfo;
import com.mozi.patch.generator.entity.PatchInfo;
import com.mozi.patch.generator.entity.ProjectInfo;
import com.mozi.patch.generator.entity.SourceMapper;
import com.mozi.patch.generator.enums.GenTypeEnum;
import com.mozi.patch.generator.enums.VersionManagerTypeEnum;
import com.mozi.patch.generator.utils.IoUtil;
import com.mozi.patch.generator.utils.ZipUtil;

/**
 * 类GitPatchGenerator.java的实现描述：git项目补丁生成器
 * @author arron 2018年4月12日 下午7:19:32
 */
public class GitPatchGenerator implements PatchGenerator {
    private static final Logger logger;

    @Override
    public boolean support(final VersionManagerTypeEnum manageType) {
        return manageType.equals(VersionManagerTypeEnum.GIT);
    }

    @Override
    public void generatePatch(final ProjectInfo projectInfo, final PatchInfo patchInfo) throws Exception {
        if (projectInfo instanceof GitProjectInfo) {
            final Set<String> changeFiles = new HashSet<String>();
            final Set<String> delFiles = new HashSet<String>();
            if (GenTypeEnum.LOG.equals(patchInfo.getGenType())) {
                final List<String> old = getPatchFileList(patchInfo.getPatchFile());
                doCleanDealUpdate(old, projectInfo.getSourceMappers(), changeFiles);
            } else {
                final GitProjectInfo gitProjectInfo = (GitProjectInfo) projectInfo;
                patchInfo.setPatchFileDir(patchInfo.getPatchFileDir() + "/" + "版本号【" + patchInfo.getStartVersion() + "-" + patchInfo.getEndVersion() + "】"
                        + DateFormatUtils.format(new Date(), "yyyy-MM-dd-hh-mm-ss"));
                final Repository repository = ((RepositoryBuilder) new RepositoryBuilder().setGitDir(new File(
                        gitProjectInfo.getGitRepositoryUrl()))).build();
                this.diffTree(repository, patchInfo.getStartVersion(), patchInfo.getEndVersion(), changeFiles, delFiles);
            }
            writePatchToPatchFile(changeFiles, patchInfo.getPatchFileDir(), null);
            writePatchToPatchFile(delFiles, patchInfo.getPatchFileDir(), DiffEntry.ChangeType.DELETE);
            for (final String changeFile : changeFiles) {
                this.copyFileToPatchDir(changeFile, projectInfo, patchInfo);
            }
            final File zipFile = ZipUtil.zip(patchInfo.getPatchFileDir());
            GitPatchGenerator.logger.info("生成补丁包:{}", (Object) zipFile.getPath());
            return;
        }
        throw new RuntimeException("ProjectInfo只支持GitProjectInfo实例");
    }

    public Set<String> diffTree(final Repository repository, final String startVersion, final String endVersion,
                                final Set<String> changeFiles, final Set<String> delFiles)
            throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException,
            GitAPIException {
        final ObjectReader reader = repository.newObjectReader();
        final CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        final Git git = new Git(repository);
        final ObjectId old = repository.resolve(startVersion + "^{tree}");
        final ObjectId head = repository.resolve(endVersion + "^{tree}");
        oldTreeIter.reset(reader, (AnyObjectId) old);
        final CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        newTreeIter.reset(reader, (AnyObjectId) head);
        final List<DiffEntry> diffs = (List<DiffEntry>) git.diff().setNewTree((AbstractTreeIterator) newTreeIter)
                .setOldTree((AbstractTreeIterator) oldTreeIter).call();
        for (final DiffEntry diffEntry : diffs) {
            final DiffEntry.ChangeType changeType = diffEntry.getChangeType();
            GitPatchGenerator.logger.info("old:{},oper:{},new:{}",
                    new Object[]{diffEntry.getOldPath(), changeType.name(), diffEntry.getNewPath()});
            if (!changeType.equals((Object) DiffEntry.ChangeType.DELETE)) {
                changeFiles.add(diffEntry.getNewPath());
            } else {
                delFiles.add(diffEntry.getOldPath());
            }
        }
        try {
            git.close();
        } catch (Exception e) {
            GitPatchGenerator.logger.error("diffTree()", (Throwable) e);
        }
        return changeFiles;
    }

    private static void doCleanDealUpdate(final List<String> svnList, final List<SourceMapper> sourceMappers,
                                          final Set<String> changeFiles) {
        for (int i = 0, j = svnList.size(); i < j; ++i) {
            String curr = svnList.get(i);
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
        }
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
            GitPatchGenerator.logger.error("getPatchFileList()", (Throwable) e);
        } catch (Exception e2) {
            GitPatchGenerator.logger.error("getPatchFileList()", (Throwable) e2);
        } finally {
            IoUtil.closeQuietly(f, dr);
        }
        return fileList;
    }

    private static String writePatchToPatchFile(final Set<String> patchFileList, final String configPatchPath,
                                                final DiffEntry.ChangeType pathModifyType) {
        final Iterator<String> iterator = patchFileList.iterator();
        String path;
        if (DiffEntry.ChangeType.DELETE.equals((Object) pathModifyType)) {
            path = configPatchPath + "-del.txt";
        } else {
            path = configPatchPath + ".txt";
        }
        final String desFilePathStr = path.substring(0, path.lastIndexOf("/"));
        final File desFilePath = new File(desFilePathStr);
        if (!desFilePath.exists()) {
            desFilePath.mkdirs();
        }
        final File file = new File(path);
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
            GitPatchGenerator.logger.error("writePatchToPatchFile()", (Throwable) e);
        } catch (IOException e2) {
            GitPatchGenerator.logger.error("writePatchToPatchFile()", (Throwable) e2);
        } catch (Exception e3) {
            GitPatchGenerator.logger.error("writePatchToPatchFile()", (Throwable) e3);
        } finally {
            if (writer != null) {
                IoUtil.closeQuietly(writer);
            }
            if (fw != null) {
                IoUtil.closeQuietly(fw);
            }
        }
        return path;
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
        logger = LoggerFactory.getLogger(GitPatchGenerator.class);
    }
}
