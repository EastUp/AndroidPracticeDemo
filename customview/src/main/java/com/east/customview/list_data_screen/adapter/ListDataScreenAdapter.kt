package com.east.customview.list_data_screen.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 多条目筛选的Adapter
 *  @author: East
 *  @date: 2019-12-28
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ListDataScreenAdapter: BaseMenuAdapter() {

    private var mToast : Toast?= null
    private var datas = arrayOf("类型","品牌","价格","更多")
    override fun getItemCount() = datas.size

    override fun getTabView(position: Int, parent: ViewGroup): View {
        val tabView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tabview, parent, false) as TextView
        tabView.setTextColor(Color.WHITE)
        tabView.text = datas[position]
        return tabView
    }

    override fun getContentView(position: Int, parent: ViewGroup): View {
        val contentView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false) as TextView
        contentView.text = datas[position]
        contentView.setOnClickListener {
            mObservers.forEach {
                //点击完后需要关闭菜单
                it.closeMenuContent()
                show(parent.context,datas[position],Toast.LENGTH_SHORT)
            }

        }
        return contentView
    }

    override fun openMenu(view:View) {
        val textView = view as TextView
        textView.setTextColor(Color.RED)
    }

    override fun closeMenu(view:View) {
        val textView = view as TextView
        textView.setTextColor(Color.WHITE)
    }

    fun show(context: Context, msg:String, timer:Int){
        if(mToast == null)
            mToast =
                Toast.makeText(context,"关闭菜单",timer)
        mToast!!.setText(msg)
        mToast!!.show()
    }

}