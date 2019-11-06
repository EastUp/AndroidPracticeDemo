package com.east.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.recyclerview.itemanimator.ItemAnimatorActivity
import com.east.recyclerview.itemdecoration.ItemDecorationActivity
import com.east.recyclerview.swap.SwapActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v:View){
        when(v){
            itemDecoration -> startActivity(Intent(this,ItemDecorationActivity::class.java))
            animator -> startActivity(Intent(this,ItemAnimatorActivity::class.java))
            swap -> startActivity(Intent(this,SwapActivity::class.java))
        }
    }
}
