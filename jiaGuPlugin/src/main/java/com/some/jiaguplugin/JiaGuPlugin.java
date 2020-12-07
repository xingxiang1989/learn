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

        /**
         *在所有build.gradle解析完成后，开始执行task之前，
         *此时所有的脚本已经解析完成，task，plugins等所有信息可以获取，task的依赖关系也已经生成，
         *如果此时需要做一些事情，可以写在afterEvaluate
         */
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(final Project project) {

                System.out.println("JiaGuPlugin 执行了  afterEvaluate ---- > ");


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
                                System.out.println("JiaGuPlugin outPutFile =" + outPutFile.getAbsolutePath());
                                String name = baseVariantOutput.getName();
                                project.getTasks().create("jiagu" + name,
                                        JiaguTask.class, outPutFile, jiaguExt);
                            }
                        });
                    }
                });
            }
        });

        /**
         * 在解析setting.gradle之后，开始解析build.gradle之前，
         * 这里如果要干些事情（更改build.gradle校本内容），可以写在beforeEvaluate
         */
        project.beforeEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                System.out.println("JiaGuPlugin 执行了  beforeEvaluate ---- > ");

            }
        });
    }
}
