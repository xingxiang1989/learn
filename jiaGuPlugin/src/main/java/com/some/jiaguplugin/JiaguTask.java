package com.some.jiaguplugin;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecSpec;

import java.io.File;

import javax.inject.Inject;

/**
 * @author xiangxing
 */
public class JiaguTask extends DefaultTask {

    File apkFile;
    JiaguExt jiaguExt;

    /**
     * 用于设置分组，比如jiagudebug，jiagurelease 就会放在jiagu文件夹里
     */
    @Inject
    public JiaguTask(File apkFile, JiaguExt jiaguExt){
        setGroup("jiagu");
        this.apkFile = apkFile;
        this.jiaguExt = jiaguExt;
    }

    @TaskAction
    public void abs(){
        //完成加固
        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.commandLine("java","-jar",jiaguExt.getJiaguToolPath(),
                        "-login",jiaguExt.getUserName(),jiaguExt.getUserPwd());
            }
        });

        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.commandLine("java", "-jar", jiaguExt.getJiaguToolPath(),
                        "-importsign", jiaguExt.getKeyStorePath(),
                        jiaguExt.getKeyStorePass(),
                        jiaguExt.getKeyStoreKeyAlias(),
                        jiaguExt.getKeyStoreKeyAliasPwd()
                        );
            }
        });

        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.commandLine("java", "-jar", jiaguExt.getJiaguToolPath(),
                        "-jiagu", apkFile.getAbsolutePath(),
                        apkFile.getParent(),
                        "-autosign");
            }
        });
    }
}
