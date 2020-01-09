package com.east.customview.besier3_huajiao_praise

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_huajiao_praise.*

class HuajiaoPraiseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huajiao_praise)
    }

    fun onClick(v:View){
        love_layout.addLove()
//        for(i in 0..10){
//            love_layout.addLove()
//        }
    }
}
