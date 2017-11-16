package com.wangkeke.kotlinnet.model

/**
 * Created on 2017/11/3 14:57.
 * @author by 王可可
 * @version 1.0
 */

data class Girl(var error: Boolean, var results: ArrayList<Person>) {
    inner class Person(
            val _id: String,
            var createdAt: String,
            var desc: String,
            var publishedAt: String,
            var source: String,
            var type: String,
            var url: String,
            var used: Boolean,
            var who: String
    )
}