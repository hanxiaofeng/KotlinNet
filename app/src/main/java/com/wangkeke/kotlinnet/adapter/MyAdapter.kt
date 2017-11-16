package com.wangkeke.kotlinnet.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wangkeke.kotlinnet.model.Girl
import com.wangkeke.kotlinnet.R


/**
 * Created on 2017/11/3 16:44.
 * @author by 王可可
 * @version 1.0
 */
class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var listData :List<Girl.Person>? = ArrayList()
    var listener:OnItemClickListener? = null

    fun setData(listData :List<Girl.Person>){
        this.listData = listData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.mTextView.text = listData!![position].who
        holder.mTextView.setOnClickListener({
            listener!!.onItemClick(position)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTextView: TextView = view.findViewById(R.id.textView)

    }

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onItemClick(position:Int)
    }
}