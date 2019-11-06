package com.east.recyclerview.itemdecoration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.east.recyclerview.R
import com.east.recyclerview.itemdecoration.adapter.PictureAdapter
import com.east.recyclerview.itemdecoration.divider.RecyclerViewItemDecoration
import com.east.recyclerview.utils.DisplayUtil
import kotlinx.android.synthetic.main.activity_item_decoration.*

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  测试分割线
 *  @author: East
 *  @date: 2019-10-30 16:19
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ItemDecorationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_decoration)

        var layoutmanager = GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false)
//        layoutmanager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                //如果是第一个item 则它占2个位置
//                if(position == 0)
//                    return 2
//                else return 1
//            }
//        }

        rv.layoutManager = layoutmanager
//        rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv.adapter = PictureAdapter()
        rv.addItemDecoration(RecyclerViewItemDecoration.Builder(this)
            .mode(RecyclerViewItemDecoration.MODE_GRID)
//            .mode(RecyclerViewItemDecoration.MODE_HORIZONTAL)
//            .drawableID(R.mipmap.ic_launcher_round)
//            .dashGap(30)
//            .dashWidth(10)
            .gridBorderVisible(true)
//            .firstLineVisible(true)
//            .lastLineVisible(true)
            .thickness(DisplayUtil.dip2px(this,5f))
            .create())

        //当item的布局固定是,可以进行一定得优化
        rv.setHasFixedSize(true)

    }
}
