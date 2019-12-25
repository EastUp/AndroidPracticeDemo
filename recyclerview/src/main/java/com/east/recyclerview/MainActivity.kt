package com.east.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.recyclerview.itemanimator.ItemAnimatorActivity
import com.east.recyclerview.itemdecoration.ItemDecorationActivity
import com.east.recyclerview.recycleranalysis.RecyclerAnalysisActivity
import com.east.recyclerview.swap.SwapActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v:View){
        when(v){
            //
            itemDecoration -> startActivity(Intent(this,ItemDecorationActivity::class.java))
            //动画
            animator -> startActivity(Intent(this,ItemAnimatorActivity::class.java))
            //交换位置
            swap -> startActivity(Intent(this,SwapActivity::class.java))
            //辉哥的RecyclerView封装
            btn_hui -> startActivity(Intent(this,RecyclerAnalysisActivity::class.java))
        }
    }
}
