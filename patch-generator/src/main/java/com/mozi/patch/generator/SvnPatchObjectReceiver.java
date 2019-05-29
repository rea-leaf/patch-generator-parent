/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.wc2.ISvnObjectReceiver;
import org.tmatesoft.svn.core.wc2.SvnTarget;

import com.mozi.patch.generator.entity.PatchInfo;
import com.mozi.patch.generator.enums.PathModifyTypeEnum;

/**
 * 类SvnPatchObjectReceiver.java的实现描述：svn日志处理器-观察者模式
 * 
 * @author arron 2018年4月12日 下午7:20:02
 */
public class SvnPatchObjectReceiver implements ISvnObjectReceiver<SVNLogEntry> {
    private static final Logger logger;
    private PatchInfo           patchInfo;
    private Set<String>         changeFiles;
    private Set<String>         delFiles;

    public SvnPatchObjectReceiver() {
    }

    public SvnPatchObjectReceiver(final PatchInfo patchInfo, final Set<String> changeFiles, final Set<String> delFiles) {
        if (changeFiles != null) {
            this.changeFiles = changeFiles;
        } else {
            this.changeFiles = new HashSet<String>();
        }
        if (delFiles != null) {
            this.delFiles = delFiles;
        } else {
            this.delFiles = new HashSet<String>();
        }
        this.patchInfo = patchInfo;
    }

    public void receive(final SvnTarget target, final SVNLogEntry svnLogEntry) throws SVNException {
        SvnPatchObjectReceiver.logger.info("版本:{},作者:{},时间:{}",
                new Object[] { svnLogEntry.getRevision(), svnLogEntry.getAuthor(), svnLogEntry.getDate() });
        SvnPatchObjectReceiver.logger.info("提交消息:{}", (Object) svnLogEntry.getMessage());
        final Map<String, SVNLogEntryPath> map = (Map<String, SVNLogEntryPath>) svnLogEntry.getChangedPaths();
        final String author = this.patchInfo.getCheckAuthor();
        final boolean hasCheckAuthor = StringUtils.isBlank((CharSequence) author)
                || (StringUtils.isNotBlank((CharSequence) author) && author.equals(svnLogEntry.getAuthor()));
        final Date startTime = this.patchInfo.getStartTime();
        final boolean hasCheckStartTime = startTime == null
                || (startTime != null && startTime.before(svnLogEntry.getDate()));
        final Date endTime = this.patchInfo.getEndTime();
        final boolean hasCheckEndTime = endTime == null || (endTime != null && endTime.after(svnLogEntry.getDate()));
        final List<String> includeRevisions = this.patchInfo.getIncludeRevisions();
        final boolean hasIncludeRevisions = includeRevisions == null
                || includeRevisions.size() == 0
                || (includeRevisions.size() > 0 && includeRevisions.contains(String.valueOf(svnLogEntry.getRevision())));
        final List<String> excludeRevisions = this.patchInfo.getExcludeRevisions();
        final boolean hasExcludeRevisions = excludeRevisions == null
                || excludeRevisions.size() == 0
                || (excludeRevisions.size() > 0 && !excludeRevisions
                        .contains(String.valueOf(svnLogEntry.getRevision())));
        if (hasCheckAuthor && hasCheckStartTime && hasCheckEndTime && hasIncludeRevisions && hasExcludeRevisions
                && map.size() > 0) {
            final Set<String> set = map.keySet();
            for (final String fileName : set) {
                final SVNLogEntryPath path = map.get(fileName);
                SvnPatchObjectReceiver.logger.info("{}:{}", (Object) path.getType(), (Object) fileName);
                if (path.getType() != PathModifyTypeEnum.D.getValue()) {
                    this.changeFiles.add(fileName);
                } else {
                    this.delFiles.add(fileName);
                }
            }
        }
    }

    public PatchInfo getPatchInfo() {
        return this.patchInfo;
    }

    public void setPatchInfo(final PatchInfo patchInfo) {
        this.patchInfo = patchInfo;
    }

    public Set<String> getChangeFiles() {
        return this.changeFiles;
    }

    public void setChangeFiles(final Set<String> changeFiles) {
        this.changeFiles = changeFiles;
    }

    public Set<String> getDelFiles() {
        return this.delFiles;
    }

    public void setDelFiles(final Set<String> delFiles) {
        this.delFiles = delFiles;
    }

    static {
        logger = LoggerFactory.getLogger(SvnPatchObjectReceiver.class);
    }
}
