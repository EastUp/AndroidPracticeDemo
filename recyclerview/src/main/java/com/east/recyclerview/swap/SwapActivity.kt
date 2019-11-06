package com.east.recyclerview.swap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import com.east.recyclerview.R
import com.east.recyclerview.itemdecoration.divider.RecyclerViewItemDecoration
import com.east.recyclerview.swap.adapter.SwapAdapter
import com.east.recyclerview.utils.DisplayUtil
import kotlinx.android.synthetic.main.activity_swap.*
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  不用长按拖动,左滑,右滑
 *  @author: East
 *  @date: 2019-11-06 17:45
 * |---------------------------------------------------------------------------------------------------------------|
 */
class SwapActivity : AppCompatActivity() {

    lateinit var itemTouchHelper : ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap)

        //添加分割线
        rv.addItemDecoration(RecyclerViewItemDecoration.Builder(this)
            .color("#ffffff")
            .thickness(DisplayUtil.dip2px(this,2f))
            .create())
        var list = listOf<String>("1","2","3","4","5","6","7","8","9","10").toMutableList()
        val adapter = SwapAdapter(list,object : SwapAdapter.OnTouchListener {
            override fun onTouchListener(holder: SwapAdapter.ViewHolder): Boolean {
                /**
                 * 设置触摸监听，然后调用startDrag()方法进行移动。
                 */
//                itemTouchHelper.startDrag(holder)
                return true
            }

            override fun onSwipeLeft() {
                Toast.makeText(this@SwapActivity,"左滑",Toast.LENGTH_SHORT).show()
            }

            override fun onSwipeRight() {
                Toast.makeText(this@SwapActivity,"右滑",Toast.LENGTH_SHORT).show()
            }
        })
        rv.adapter = adapter


        //先实例化Callback
        var dragItemTouchHelpCallback =  DragItemTouchHelpCallback(adapter)
        //用Callback构造ItemtouchHelper
        itemTouchHelper = ItemTouchHelper(dragItemTouchHelpCallback)
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        itemTouchHelper.attachToRecyclerView(rv)
//        itemTouchHelper.startDrag()

    }
}
