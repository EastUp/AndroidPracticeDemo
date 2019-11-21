package com.east.customview.view_viewgroup_touch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_view_and_view_group_touch.*

class ViewAndViewGroupTouchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_and_view_group_touch)

        touchView1.setOnTouchListener { v, event ->
            Log.e("TAG","View -> OnTouchListener")
            false
        }

        touchView1.setOnClickListener {
            Log.e("TAG","View -> OnClickListener")
        }


        touchView2.setOnTouchListener { v, event ->
            Log.e("TAG","View -> OnTouchListener")
            false
        }

        touchView2.setOnClickListener {
            Log.e("TAG","View -> OnClickListener")
        }

    }
}
