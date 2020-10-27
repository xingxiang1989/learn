package com.some.jiaguplugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * 插件也需要注册
 */
public class JiaGuPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        //因为需要用户填数据，因此要使用插件扩展
        //让使用者配置JiaguExt的参数
        final JiaguExt jiagu = project.getExtensions().create("jiagu",JiaguExt.class);

        //注册监听
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                System.out.println(jiagu.getUserName());
            }
        });
    }
}
