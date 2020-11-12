package com.some.mvvmdemo.reflect

import com.some.mvvmdemo.proxy.IHello
import okhttp3.internal.Internal.instance
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * @author xiangxing
 */
class SubjectInvocationHandler(instance: IHello): InvocationHandler {


    @Throws(Throwable::class)
    override fun invoke(`object`: Any, method: Method, args: Array<Any>): Any? {
        //在代理真实对象前我们可以添加一些自己的操作
        println("before Method invoke")
        println("Method:$method")
        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(instance, args)
        //在代理真实对象后我们也可以添加一些自己的操作
        println("after Method invoke")
        return null
    }
}