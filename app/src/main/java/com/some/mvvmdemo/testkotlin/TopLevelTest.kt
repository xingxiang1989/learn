package com.some.mvvmdemo.testkotlin

//标准格式声明并初始化变量，可以省略类型，会自动推导,基础数据类型，仅推导Int和Double类型
var a:Int = 7
var b = 8;
var data:Double = 1.3

/*
*延迟初始化，使用lateinit关键字，但是
*不能用于Int、Short等基础数据类型上，
*且不能是可空的
*不能为val，只能是lateinit var
*/

lateinit var str:String

const val PI = "3.14"
