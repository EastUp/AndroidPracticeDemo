package com.east.recyclerview.swap.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.east.recyclerview.R
import com.east.recyclerview.swap.OnItemCallbackListener
import com.east.recyclerview.swap.SwapActivity
import java.util.*

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 互换位置,滑动删除的Adapter
 *  @author: East
 *  @date: 2019-11-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
class SwapAdapter :RecyclerView.Adapter<SwapAdapter.ViewHolder>,OnItemCallbackListener{
    private var picList: MutableList<String> ?= null
    lateinit var onTouchListener: OnTouchListener
    constructor()

    constructor(picList: MutableList<String>,onTouchListener: OnTouchListener){
        this.picList = picList
        this.onTouchListener = onTouchListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_swap_rv,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv!!.text = picList!![position]

        /**
         * 在onBindViewHolder()方法中设置触摸监听，然后调用startDrag()方法进行移动。
         */
        holder.itemView.setOnTouchListener { v, event ->
            onTouchListener.onTouchListener(holder)
        }
    }

    override fun getItemCount(): Int {
        if (picList.isNullOrEmpty()) {
            return 0
        }
        return picList!!.size
    }

    class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        var tv: TextView? = null

        init {
            initView()
        }

        private fun initView() {
            tv = mView.findViewById(R.id.tv)
        }
    }

    override fun onMove(fromPosition: Int, toPosition: Int) {
        /**
         * 在这里进行给原数组数据的移动
         * 第一个参数为数据源
         */
        Collections.swap(picList,fromPosition,toPosition)

        /**
         *  通知数据移动
         */
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onSwipeLeft(position: Int) {
        /**
         *  左滑删除
         */
        picList!!.removeAt(position)
        notifyItemRemoved(position)
        onTouchListener.onSwipeLeft()
    }

    override fun onSwipeRight(position: Int) {
        //右滑删除
        picList!!.removeAt(position)
        notifyItemRemoved(position)
        onTouchListener.onSwipeRight()
    }



    interface OnTouchListener{
        fun onTouchListener(holder: ViewHolder):Boolean
        fun onSwipeLeft()
        fun onSwipeRight()
    }



}