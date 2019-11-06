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
 *  @description: 互换位置的swap
 *  @author: East
 *  @date: 2019-11-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
class SwapAdapter :RecyclerView.Adapter<SwapAdapter.ViewHolder>,OnItemCallbackListener{
    private var picList: MutableList<String> ?= null
    lateinit var activity: Activity
    constructor()

    constructor(activity:Activity,picList: MutableList<String>){
        this.picList = picList
        this.activity = activity
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
        holder.itemView.setOnTouchListener { v, event ->
            val itemTouchHelper = (activity as SwapActivity).itemTouchHelper
            itemTouchHelper.startDrag(holder)
            true
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

    override fun move(fromPosition: Int, toPosition: Int) {
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
}