package com.east.customview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.besier1_messagebubbleview.MessageBubbleActivity
import com.east.customview.besier2_messagebubbleview.DragBombActivity
import com.east.customview.besier3_huajiao_praise.HuajiaoPraiseActivity
import com.east.customview.custom_behavior.CustomBehaviorActivity
import com.east.customview.custom_car_home.CarHomeActivity
import com.east.customview.custom_changecolor.ViewPagerActivity
import com.east.customview.custom_kugou.KugouSlidingMenuActivity
import com.east.customview.custom_letterside.LetterSideActivity
import com.east.customview.custom_lockpattern.LockPatternActivity
import com.east.customview.custom_qq.QQSlidingMenuActivity
import com.east.customview.custom_qqarc.QQArcActivity
import com.east.customview.custom_ratingbar.CustomRatingBarActivity
import com.east.customview.custom_tablayout.CustomTabLayoutActivity
import com.east.customview.customtextview.CustomTextViewActivity
import com.east.customview.fang58.Fang58Activity
import com.east.customview.huashu_loading.HuaShuLoadingActivity
import com.east.customview.list_data_screen.ListDataScreenActivity
import com.east.customview.material_design_1.SimplePracticeActivity
import com.east.customview.parallax1_kugou_splash.ParallaxKugouActivity
import com.east.customview.parallax2_yahu_splash.ParallaxYahuActivity
import com.east.customview.practice_paint.PracticePaintActivity
import com.east.customview.view_draw_process.ViewDrawProcessActivity
import com.east.customview.view_touch.ViewTouchActivity
import com.east.customview.viewgroup_touch.ViewGroupTouchActivity
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
            //自定义流式布局TabLayout
            custom_tablayout -> startActivity(Intent(this, CustomTabLayoutActivity::class.java))
            //View的Touch分发事件
            viewTouch -> startActivity(Intent(this, ViewTouchActivity::class.java))
            //ViewGroup的Touch分发事件
            viewGroupTouch -> startActivity(Intent(this, ViewGroupTouchActivity::class.java))
            //酷狗侧滑菜单效果
            kugou -> startActivity(Intent(this, KugouSlidingMenuActivity::class.java))
            //QQ侧滑菜单效果
            qq -> startActivity(Intent(this, QQSlidingMenuActivity::class.java))
            //汽车之家折叠效果
            car_home -> startActivity(Intent(this, CarHomeActivity::class.java))
            //九宫格解锁
            lockpattern -> startActivity(Intent(this, LockPatternActivity::class.java))
            //MaterialDesign 初次练习
            simple_prcactice -> startActivity(Intent(this, SimplePracticeActivity::class.java))
            //自定义Behavior
            custom_behavior -> startActivity(Intent(this, CustomBehaviorActivity::class.java))
            //仿58同城数据加载动画
            fang58 -> startActivity(Intent(this, Fang58Activity::class.java))
            //常见多条命菜单筛选
            list_data_screen -> startActivity(Intent(this, ListDataScreenActivity::class.java))
            //花束直播加载动画
            huashu_loading -> {startActivity(Intent(this,HuaShuLoadingActivity::class.java))}
            //贝塞尔曲线1:消息气泡View
            messageBunbbleView -> {startActivity(Intent(this,MessageBubbleActivity::class.java))}
            //贝塞尔曲线2:可拖动爆炸消息气泡View
            messageBunbbleView_DragBomb -> {startActivity(Intent(this,DragBombActivity::class.java))}
            //贝塞尔曲线3:花椒直播点赞效果
            huajiao_praise -> {startActivity(Intent(this,HuajiaoPraiseActivity::class.java))}
            //视差动画1:酷狗引导页
            kugou_splash -> {startActivity(Intent(this,ParallaxKugouActivity::class.java))}
            //视差动画2:雅虎引导页
            yahu_splash -> {startActivity(Intent(this,ParallaxYahuActivity::class.java))}
        }
    }
}
