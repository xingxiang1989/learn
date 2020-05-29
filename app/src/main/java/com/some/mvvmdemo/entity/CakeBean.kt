package com.some.mvvmdemo.entity

/**
 * @author xiangxing
 */
class CakeBean {

    var name: String? = null
    var color: Int = 0
    var amount: Float = 0f
    var degree: Float = 0f

    constructor(name: String?, color: Int, amount: Float) {
        this.name = name
        this.color = color
        this.amount = amount
    }

    override fun toString(): String {
        return "CakeBean(name=$name, color=$color, amount=$amount, degree=$degree)"
    }


}