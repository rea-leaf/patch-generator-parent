/**
 * Copyright &copy; 2018 <a href="https://gitee.com/hackempire/patch-generator-parent">emsite</a> All rights reserved.
 */
package com.mozi.patch.generator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mozi.patch.generator.entity.PatchInfo;
import com.mozi.patch.generator.entity.ProjectInfo;

/**
 * 类GeneratePatchExecutor.java的实现描述：生成补丁-执行器
 * 
 * @author arron 2018年4月12日 下午7:16:33
 */
public class GeneratePatchExecutor {
    private static List<PatchGenerator> generators;
    private static final Logger         logger = LoggerFactory.getLogger(GeneratePatchExecutor.class); ;
    static {
        // 添加生成器
        generators = new ArrayList<PatchGenerator>();
        generators.add(new SvnPatchGenerator());
        generators.add(new GitPatchGenerator());
    }

    public static void execute(ProjectInfo projectInfo, PatchInfo patchInfo) {
        for (PatchGenerator patchGenerator : GeneratePatchExecutor.generators) {
            if (patchGenerator.support(projectInfo.getVersionManagerTypeEnum())) {
                try {
                    patchGenerator.generatePatch(projectInfo, patchInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                logger.info("生成补丁成功");
                break;
            }
        }
    }

}
