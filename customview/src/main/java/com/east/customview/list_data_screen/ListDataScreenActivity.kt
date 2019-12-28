package com.east.customview.list_data_screen

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import com.east.customview.list_data_screen.adapter.ListDataScreenAdapter
import kotlinx.android.synthetic.main.activity_list_data_screen.*

class ListDataScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data_screen)

        list_data_screen_view.setAdapter(ListDataScreenAdapter())
    }

    fun onClick(v: View){
        val textView = list_data_screen_view.mTabViewContainer.getChildAt(0) as TextView
        if (textView.currentTextColor == Color.WHITE) {
            textView.setTextColor(Color.RED)
        }else{
            textView.setTextColor(Color.WHITE)
        }
    }
}
