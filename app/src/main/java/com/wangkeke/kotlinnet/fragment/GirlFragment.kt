package com.wangkeke.kotlinnet.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.wangkeke.kotlinnet.GankIoService
import com.wangkeke.kotlinnet.R
import com.wangkeke.kotlinnet.adapter.GirlAdapter
import kotlinx.android.synthetic.main.fragment_girl.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 福利---网络请求
 */
class GirlFragment : Fragment() ,GirlAdapter.OnItemClickListener{


    private var mParam1: String? = null

    private var adapter: GirlAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_girl, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = GirlAdapter(activity)
        adapter!!.setOnItemClickListener(this)
        recyclerView.adapter = adapter


        getGirlRequest(1)
    }

    companion object {
        private val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): GirlFragment {
            val fragment = GirlFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }

    private fun getGirlRequest(page: Int) {

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
        toast("点击了第几个："+position)
    }
}