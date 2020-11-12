package com.some.mvvmdemo.proxy

import com.blankj.utilcode.util.LogUtils

/**
 * @author xiangxing
 */
class RealSubject: IHello {
    override fun sayHello() {
        LogUtils.d("sayHello --> ")
    }

    override fun sayBye() {
        LogUtils.d("sayBye --> ")
    }
}