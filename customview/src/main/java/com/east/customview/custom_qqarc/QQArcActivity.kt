package com.east.customview.custom_qqarc

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_qqarc.*

class QQArcActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qqarc)

        stepView.setStepMax(4000)

        var animator = ValueAnimator.ofFloat(0f,3000f)
        animator.duration = 1000
        animator.interpolator = DecelerateInterpolator() //开始快,后面慢
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            stepView.setCurrentStep( value.toInt())
        }

        animator.start()

    }
}
