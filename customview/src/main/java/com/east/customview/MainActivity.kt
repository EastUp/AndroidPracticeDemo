package com.east.customview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import com.east.customview.custom_changecolor.ChangeColorActivity
import com.east.customview.custom_changecolor.ViewPagerActivity
import com.east.customview.custom_letterside.LetterSideActivity
import com.east.customview.custom_qqarc.QQArcActivity
import com.east.customview.custom_ratingbar.CustomRatingBarActivity
import com.east.customview.customtextview.CustomTextViewActivity
import com.east.customview.practice_paint.PracticePaintActivity
import com.east.customview.view_draw_process.ViewDrawProcessActivity
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
            //画笔练习(炫酷进度条,仿58同城数据加载)
            practice_paint -> startActivity(Intent(this,PracticePaintActivity::class.java))
            //仿淘宝评价 RatingBar
            custom_ratingbar -> startActivity(Intent(this,CustomRatingBarActivity::class.java))
            //字母索引
            custom_letterSide -> startActivity(Intent(this, LetterSideActivity::class.java))
            //View的绘制流程
            view_draw_process -> startActivity(Intent(this,ViewDrawProcessActivity::class.java))
        }
    }
}
