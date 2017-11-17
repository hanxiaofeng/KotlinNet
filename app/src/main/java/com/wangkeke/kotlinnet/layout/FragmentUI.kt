package com.wangkeke.kotlinnet.layout

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.google.gson.GsonBuilder
import com.wangkeke.kotlinnet.GankIoService
import com.wangkeke.kotlinnet.R
import com.wangkeke.kotlinnet.adapter.GirlAdapter
import com.wangkeke.kotlinnet.fragment.AnkoLayoutFragment
import com.wangkeke.kotlinnet.model.Girl
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created on 2017/11/17 09:58.
 * @author by 王可可
 * @version 1.0
 */
class FragmentUI<T> : AnkoComponent<T> {

    var myAdapter: MyRecyclerAdapter? = null

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        verticalLayout {
            val name = editText{
                hint = "我不是xml布局呦"
            }
            button("Say Hello") {
                onClick { ctx.toast("Hello, ${name.text}!") }
            }

            recyclerView {
                backgroundColor = Color.GRAY

                layoutManager = LinearLayoutManager(ui.ctx)
                myAdapter = MyRecyclerAdapter(ui.ctx)
                myAdapter!!.setOnItemClickListener(object : MyRecyclerAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        toast("position = " + position)
                    }
                })

                adapter = myAdapter
                getGirlRequest(1)

            }.lparams(width = matchParent) {
                height = matchParent
            }

        }
    }

    fun getGirlRequest(page: Int) {

        doAsync {
            val retrofit = Builder()
                    .baseUrl("http://gank.io/api/data/")
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
            val service = retrofit.create(GankIoService::class.java)
            var observable = service.getSearchAndroid(page)

            observable.subscribe({
                var listData = it.results
                uiThread {
                    myAdapter!!.setData(listData)
                }
            }, {
                Log.e("tag", "error = " + it.stackTrace)
            })
        }
    }

}

val item_image = 1001

class AdapterItemUI<T> : AnkoComponent<T> {
    override fun createView(ui: AnkoContext<T>) = with(ui) {


        relativeLayout {

            imageView {
                id = item_image
                scaleType = ImageView.ScaleType.FIT_XY
            }.lparams(width = matchParent, height = dip(450))
        }
    }

}


class MyRecyclerAdapter(private var context: Context) : RecyclerView.Adapter<MyRecyclerAdapter.ViewMyHolder>() {

    var listData: List<Girl.Person>? = ArrayList()
    var listener: OnItemClickListener? = null

    fun setData(listData: List<Girl.Person>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyRecyclerAdapter.ViewMyHolder, position: Int) {
//        holder.author.text = "来自："+listData!![position].who

        Glide.with(context).load(listData!![position].url).asBitmap().centerCrop().into(object : BitmapImageViewTarget(holder.image) {

            override fun onLoadStarted(placeholder: Drawable?) {
                holder.image.imageResource = R.mipmap.ic_launcher
            }

            override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                holder.image.imageResource = R.mipmap.ic_launcher
            }

            override fun setResource(resource: Bitmap) {
                holder.image.setImageBitmap(resource)
            }
        })

        holder.image.setOnClickListener({
            listener!!.onItemClick(position)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyRecyclerAdapter.ViewMyHolder {

        return ViewMyHolder(AdapterItemUI<MyRecyclerAdapter>().createView(AnkoContext.create(context.ctx, this)))
    }

    override fun getItemCount(): Int {
        return listData!!.size
    }

    class ViewMyHolder(view: View) : RecyclerView.ViewHolder(view) {
        //        var author: TextView = view.findViewById(item_author)
        var image: ImageView = view.findViewById(item_image)
//        var cardView: CardView = view.findViewById(R.id.cardView)

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}