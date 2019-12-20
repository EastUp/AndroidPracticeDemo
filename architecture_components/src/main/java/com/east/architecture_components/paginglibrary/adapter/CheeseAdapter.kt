package com.east.architecture_components.paginglibrary.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.east.architecture_components.paginglibrary.entity.Cheese
import com.east.architecture_components.paginglibrary.viewholder.CheeseViewHolder

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-13
 * |---------------------------------------------------------------------------------------------------------------|
 */
class CheeseAdapter:PagedListAdapter<Cheese,CheeseViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder {
        return CheeseViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {

        /**
         *  当新的PagedLists到达时，此diff回调通知PagedListAdapter如何计算列表差异
         *
         *  使用“add”按钮添加Cheese时，PagedListAdapter使用diffCallback来检测和以前对比只有一个item的差异，因此它只需要动画和重新绑定单个视图。
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Cheese>(){
            override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem.id == newItem.id


            //请注意，在kotlin中，==检查数据类会比较所有内容，但在Java中，通常会实现Object.equals，并使用它来比较对象内容。
            override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem == newItem

        }

    }
}