package com.some.mvvmdemo.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * @author xiangxing
 * @Module 注解标记表明是一个hilt模块
 * 还必须使用 @InstallIn 为 Hilt 模块添加注释
 */
@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @AppHash
    @Provides
    fun provideHash(): String {
        return hashCode().toString()
    }
}