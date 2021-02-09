package com.some.http.entity

/**
 * @author xiangxing
 */
class BaseEntity<T> {

    var msg: String ? = null
    var result: Int = 0
    var info: T ?= null
}