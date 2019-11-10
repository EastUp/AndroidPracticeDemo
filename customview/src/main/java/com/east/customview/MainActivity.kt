package com.east.customview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.customview.custom_changecolor.ChangeColorActivity
import com.east.customview.custom_changecolor.ViewPagerActivity
import com.east.customview.custom_qqarc.QQArcActivity
import com.east.customview.customtextview.CustomTextViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View){
        when(v){
            //自定义TextView
            custom_textview -> startActivity(Intent(this,CustomTextViewActivity::class.java))
            //仿QQ运动步数(画圆弧)
            cutom_qqarc -> startActivity(Intent(this,QQArcActivity::class.java))
            //玩转字体变色
            custom_changeColor -> startActivity(Intent(this,ViewPagerActivity::class.java))
        }
    }
}
