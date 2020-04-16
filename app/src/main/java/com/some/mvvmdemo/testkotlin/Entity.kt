package com.some.mvvmdemo.testkotlin

import android.util.Log

class Entity {

    var name: String = ""
    var url: String = ""

    constructor(name: String, url: String) {
        this.name = name
        this.url = url
    }


    fun foo(){
        Log.d("Entity","foo")
    }


}