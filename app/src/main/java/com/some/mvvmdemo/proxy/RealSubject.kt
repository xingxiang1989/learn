package com.some.mvvmdemo.proxy

import com.blankj.utilcode.util.LogUtils

/**
 * @author xiangxing
 */
class RealSubject: IHello {

    override fun sayHello(message: String) {
        LogUtils.d("sayHello  message = $message --> ")
    }

    override fun sayBye(message: String, type: Int) {
        LogUtils.d("sayBye  message = $message , type = $type --> ")
    }
}