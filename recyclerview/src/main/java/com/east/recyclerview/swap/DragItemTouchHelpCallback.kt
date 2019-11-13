package com.east.recyclerview.swap

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 作为ItemTouchHelper的构造参数传入,再调用ItemTouchHelper.attachToRecyclerView方法,使得他们可以拖拽
 *  @author: East
 *  @date: 2019-11-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
class DragItemTouchHelpCallback(private val onItemCallbackListener: OnItemCallbackListener): ItemTouchHelper.Callback() {

    /**
     * 当用户左右滑动Item达到删除条件时，会调用该方法,一般手指触摸滑动的距离达到RecyclerView宽度的一半时，再松开手指，
     * 此时该Item会继续向原先滑动方向滑过去并且调用onSwiped方法进行删除，否则会反向滑回原来的位置。
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if(direction == ItemTouchHelper.END){
            //item滑动方向为向右
            onItemCallbackListener.onSwipeRight(viewHolder.adapterPosition)
        }else if(direction == ItemTouchHelper.START){
            //item滑动方向为向左
            onItemCallbackListener.onSwipeLeft(viewHolder.adapterPosition)
        }
    }

    /**
     * 定义了每个状态下启用的移动方向
     */
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

    /**
     * 当用户拖动一个Item进行上下移动从旧的位置到新的位置的时候会调用该方法
     * @return true 表示被拖动的ViewHolder已经移动到了目的位置。
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        onItemCallbackListener.onMove(viewHolder.adapterPosition,target.adapterPosition)
        return true
    }

    /**
     *  是否长按拖拽
     */
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    /**
     * 如果你想要定制你的视图对用户交互的响应，这是一个可以重写的好方法。
     */
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            //滑动时改变Item的透明度
            var alpha = 1 - abs(dX) /viewHolder.itemView.width
            viewHolder.itemView.alpha = alpha
        }
    }

}