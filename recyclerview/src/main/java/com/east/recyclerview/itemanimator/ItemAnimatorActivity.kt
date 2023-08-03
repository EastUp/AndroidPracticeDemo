package com.east.recyclerview.itemanimator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.recyclerview.R
import com.east.recyclerview.itemanimator.adapter.PictureAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.activity_item_animator.*
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  测试item动画
 *  @author: East
 *  @date: 2019-11-05 14:23
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ItemAnimatorActivity : AppCompatActivity() {

    lateinit var adapter : PictureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_animator)
        val list = listOf<String>(
            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
            "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"
        ).toMutableList()

        adapter = PictureAdapter(list)
        rv.adapter = adapter
        rv.itemAnimator = SlideInLeftAnimator()
    }


    fun onClick(v: View){
        when(v){
            btn_add -> adapter.insertTest(1)
            btn_delete -> adapter.removeTest(1)
        }
    }

}
