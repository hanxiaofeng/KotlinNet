package com.wangkeke.kotlinnet.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.wangkeke.kotlinnet.model.Girl
import com.wangkeke.kotlinnet.R
import org.jetbrains.anko.imageResource

/**
 * Created on 2017/11/3 16:44.
 * @author by 王可可
 * @version 1.0
 */
class GirlAdapter(private var context:Context) : RecyclerView.Adapter<GirlAdapter.ViewHolder>() {

    var listData :List<Girl.Person>? = ArrayList()
    var listener:OnItemClickListener? = null

    fun setData(listData :List<Girl.Person>){
        this.listData = listData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: GirlAdapter.ViewHolder, position: Int) {
        holder.author.text = "来自："+listData!![position].who

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

        holder.cardView.setOnClickListener({
            listener!!.onItemClick(position)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GirlAdapter.ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.girl_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var author: TextView = view.findViewById(R.id.author)
        var image: ImageView = view.findViewById(R.id.image)
        var cardView: CardView = view.findViewById(R.id.cardView)

    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }
}