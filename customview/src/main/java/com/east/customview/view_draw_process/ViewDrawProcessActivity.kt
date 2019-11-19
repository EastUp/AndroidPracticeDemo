package com.east.customview.view_draw_process

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_view_draw_process.*

class ViewDrawProcessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_draw_process)

        Log.e("TAG","height1:${tv.measuredWidth}")

        tv.post {
            Log.e("TAG","height2:${tv.measuredWidth}")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG","height3:${tv.measuredWidth}")
    }
}
