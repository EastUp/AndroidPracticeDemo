package com.east.recyclerview.swap

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 作为ItemTouchHelper的构造参数传入,再调用ItemTouchHelper.attachToRecyclerView方法,使得他们可以拖拽
 *  @author: East
 *  @date: 2019-11-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
class DragItemTouchHelpCallback(private val onItemCallbackListener: OnItemCallbackListener): ItemTouchHelper.Callback() {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        //设置为可上下拖拽
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        //设置侧滑方向为左右
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags,swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        onItemCallbackListener.move(viewHolder.adapterPosition,target.adapterPosition)
        return true
    }

    /**
     *  是否长按拖拽
     */
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }
}