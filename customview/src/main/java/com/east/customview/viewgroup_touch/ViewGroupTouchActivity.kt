package com.east.customview.viewgroup_touch

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_view_group_touch.*

class ViewGroupTouchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_group_touch)


        touchView.setOnTouchListener { v, event ->
            Log.e("TAG","View1 -> OnTouchListener")
            false
        }

//        touchView.setOnClickListener {
//            Log.e("TAG","View1 -> OnClickListener")
//        }

        var touchView2 = object :TouchView(this){
            override fun dispatchTouchEvent(event: MotionEvent): Boolean {
                Log.e("TAG","View2 -> dispatchTouchEvent")
                return super.dispatchTouchEvent(event)
            }
            override fun onTouchEvent(event: MotionEvent?): Boolean {
                Log.e("TAG","View2 -> onTouchEvent")
                return true //这里返回true,View3接收到了触摸消息后,没消费,到view2这里才消费了
            }
        }
        touchViewGroup.addView(touchView2)

        touchView2.setOnTouchListener { v, event ->
            Log.e("TAG","View2 -> OnTouchListener")
            false
        }

//        touchView2.setOnClickListener {
//            Log.e("TAG","View2 -> OnClickListener")
//        }

        var touchView3 = object :TouchView(this){
            override fun dispatchTouchEvent(event: MotionEvent): Boolean {
                Log.e("TAG","View3 -> dispatchTouchEvent")
                return super.dispatchTouchEvent(event)
            }
            override fun onTouchEvent(event: MotionEvent?): Boolean {
                Log.e("TAG","View3 -> onTouchEvent")
                return false
            }
        }
        touchViewGroup.addView(touchView3)

        touchView3.setOnTouchListener { v, event ->
            Log.e("TAG","View3 -> OnTouchListener")
            false
        }

//        touchView3.setOnClickListener {
//            Log.e("TAG","View3 -> OnClickListener")
//        }
    }
}
