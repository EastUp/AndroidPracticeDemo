package com.east.customview.custom_letterside

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import com.east.customview.R
import com.east.customview.custom_letterside.widget.LetterSideBar
import kotlinx.android.synthetic.main.activity_custom_text_view.*
import kotlinx.android.synthetic.main.activity_letter_side.*

class LetterSideActivity : AppCompatActivity() {

    private var mHandler = object :Handler(){
        override fun handleMessage(msg: Message) {
            tv_hint.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_side)
        lettersideBar.mLetterTouchListener = object :LetterSideBar.LetterTouchListener{
            override fun onTouch(letter: String, isTouch: Boolean) {
                if(!isTouch)
                    mHandler.sendEmptyMessageDelayed(0,1000)
                else{
                    mHandler.removeCallbacksAndMessages(null)
                    tv_hint.visibility = View.VISIBLE
                    tv_hint.text = letter
                }
            }
        }


    }
}
