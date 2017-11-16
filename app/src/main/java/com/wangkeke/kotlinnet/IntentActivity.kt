package com.wangkeke.kotlinnet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import org.jetbrains.anko.toast

class IntentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        initView()
    }

    private fun initView() {

        val data = intent.getStringExtra("data")
        if(!TextUtils.isEmpty(data)){
            toast(data)
        }
    }
}
