package com.some.jiaguplugin;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariantOutput;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

/**
 * 插件也需要注册
 */
public class JiaGuPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        //因为需要用户填数据，因此要使用插件扩展
        //让使用者配置JiaguExt的参数
        final JiaguExt jiaguExt = project.getExtensions().create("jiagu",JiaguExt.class);

        //注册监听
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(final Project project) {

                AppExtension appExtension =
                        project.getExtensions().getByType(AppExtension.class);

                //得到一个集合，【debug， Release】
                appExtension.getApplicationVariants().all(new Action<ApplicationVariant>() {
                    @Override
                    public void execute(ApplicationVariant applicationVariant) {
                        applicationVariant.getOutputs().all(new Action<BaseVariantOutput>() {
                            @Override
                            public void execute(BaseVariantOutput baseVariantOutput) {
                                File outPutFile = baseVariantOutput.getOutputFile();
                                System.out.println("outPutFile =" + outPutFile.getAbsolutePath());
                                String name = baseVariantOutput.getName();
                                project.getTasks().create("jiagu" + name,
                                        JiaguTask.class, outPutFile, jiaguExt);
                            }
                        });
                    }
                });
            }
        });
    }
}
