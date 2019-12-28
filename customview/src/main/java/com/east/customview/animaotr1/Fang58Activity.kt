package com.east.customview.animaotr1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_fang58.*

class Fang58Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fang58)
    }

    fun onClick(view: View){
        if(view == btn_close_animator)
            loading_view.visibility = View.GONE
    }
}
