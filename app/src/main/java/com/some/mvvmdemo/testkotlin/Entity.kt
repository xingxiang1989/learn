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

    fun sum(a:Int, b:Int): Int{
        return a + b
    }

    public fun sumAB(a:Int, b:Int):Int = a+b

    fun printSum(a:Int ,b:Int){
        print(a+b)
    }

    fun varargs(vararg v: Int){
        for (vt in v){
            print(vt)
        }
    }

    fun main(args: Array<String>){
        varargs(1,2,3,4)
    }

    fun getStringLength(obj:Any):Int?{
        if(obj is String){
            // 做过类型判断以后，obj会被系统自动转换为String类型
            return obj.length;
        }
        return null;
    }

    fun getState(x:Int){
        when(x){
            1-> print(x)
            2-> print(x)
            3,4-> print("x==3 or x== 4")
            else ->{
                print("都不存在")
            }
        }
    }

    fun loop(){
        var items = listOf("apple","pear","banana")
        //直接拿到数据
        for (item in items){
            print(item)
        }
        //遍历index
        for(index in items.indices){
            print("item pos is $index and value is $items[index]")
        }
    }



}