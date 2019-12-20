package com.east.customview.practice_paint

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_practice_paint.*

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  Paint练习:炫酷进度条/仿58同城数据加载切换
 *  @author: East
 *  @date: 2019-11-13 09:58
 * |---------------------------------------------------------------------------------------------------------------|
 */
class PracticePaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_paint)

        progressbar.setMax(4000f)
        var valueAnimator = ValueAnimator.ofFloat(0f,4000f)
        valueAnimator.duration = 2000
        valueAnimator.addUpdateListener {
            val mCurrentProgress = it.animatedValue as Float
            progressbar.setProgress(mCurrentProgress)
        }

        valueAnimator.start()

        Thread(Runnable {
            while (true){
                Thread.sleep(1000)
                shapeView.exchange()
            }
        }).start()

    }
}
