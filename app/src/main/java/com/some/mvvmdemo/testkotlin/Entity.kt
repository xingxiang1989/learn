package com.some.mvvmdemo.testkotlin

import android.util.Log

class Entity {

    var name: String = ""
    var url: String = ""
    val a : Int = 23;
    var b = 1.5
    //可空变量的声明，必须有?符号，与lateinit不能共存
    var f : String ? = null
    lateinit var e : String

    var list = arrayOf(1,2,4,"33")

    var emptyList = arrayOfNulls<Int>(3)

    //kotlin中系统自带的，每个class都会调用的初始化的函数
    init{
        e = "string e"
        f = "string g"
    }

    constructor(name: String, url: String) {
        this.name = name
        this.url = url
    }


    fun foo(){
        Log.d("Entity","foo")
    }

    companion object{
        const val PI = "3.14"
    }

}