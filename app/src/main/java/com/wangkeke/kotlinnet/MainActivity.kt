package com.wangkeke.kotlinnet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.AdapterView
import com.google.gson.GsonBuilder
import com.wangkeke.kotlinnet.adapter.MyAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() ,MyAdapter.OnItemClickListener{

    var adapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        getGirlDataRequest(1)
    }

    private fun initView() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter()
        adapter!!.setOnItemClickListener(this)
        recyclerView.adapter = adapter
    }


    private fun getGirlDataRequest(page: Int) {

        doAsync {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://gank.io/api/data/")
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            val service = retrofit.create(GankIoService::class.java)
            var observable = service.getSearchAndroid(page)

            observable.subscribe({
                var listData = it.results
                uiThread {
                    adapter!!.setData(listData)
                }
            }, {
                Log.e("tag", "error = " + it.stackTrace)
            })
        }
    }

    override fun onItemClick(position: Int) {
        toast("position = "+position)
    }

}


