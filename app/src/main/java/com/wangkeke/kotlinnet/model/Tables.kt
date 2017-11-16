package com.wangkeke.kotlinnet.model

/**
 * Created on 2017/11/15 14:59.
 * @author by 王可可
 * @version 1.0
 */
class User(val map: MutableMap<String, Any?>) {
    var id: Int by map
    var name: String by map
    var email: String by map

    constructor(id: Int, name: String, email: String
                )
            : this(HashMap()) {
        this.name = name
        this.email = email
    }
}