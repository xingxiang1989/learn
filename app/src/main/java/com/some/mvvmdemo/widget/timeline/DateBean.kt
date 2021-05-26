package com.some.mvvmdemo.widget.timeline

/**
 * @author xiangxing
 */
data class DateBean(val index: Int){

    fun getText():String{
        return index.toString() + "日"
    }
}


