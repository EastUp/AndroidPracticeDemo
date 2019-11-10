package com.east.customview.custom_changecolor

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.east.customview.R
import com.east.customview.custom_changecolor.widget.ColorTrackTextView
import kotlinx.android.synthetic.main.activity_change_color.*

class ChangeColorActivity : AppCompatActivity() {

    lateinit var valueAnimator: ValueAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_color)

        valueAnimator = ValueAnimator.ofFloat(0f,1f)

        valueAnimator.duration = 1000

        valueAnimator.addUpdateListener {
            Log.d("value",it.animatedValue.toString())
            colortrackview.setProgress(it.animatedValue as Float)
        }

        valueAnimator.start()

    }

    fun onClick(v: View){
        when(v){
            left_to_right -> {
                colortrackview.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
                valueAnimator.start()
            }
            right_to_left ->{
                colortrackview.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
                valueAnimator.start()
            }
        }
    }
}
