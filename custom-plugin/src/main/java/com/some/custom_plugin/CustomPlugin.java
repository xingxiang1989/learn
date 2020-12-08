package com.some.custom_plugin;

import com.android.build.gradle.AppExtension;
import com.some.custom.PageTransform;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CustomPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        System.out.println("CustomPlugin 执行了");

        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
//        appExtension.registerTransform(new AsmTransform2());

        project.beforeEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                System.out.println("CustomPlugin 执行了 beforeEvaluate");
            }
        });

        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                System.out.println("CustomPlugin 执行了 afterEvaluate");

            }
        });
    }
}
