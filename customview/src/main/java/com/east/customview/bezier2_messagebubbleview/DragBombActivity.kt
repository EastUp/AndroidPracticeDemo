package com.east.customview.bezier2_messagebubbleview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import com.east.customview.bezier2_messagebubbleview.widget.MessageBubbleView
import kotlinx.android.synthetic.main.activity_drag_bomb.*

class DragBombActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_bomb)
        MessageBubbleView.attachView(tv,object: MessageBubbleView.BubbleDisapearListener {
            override fun disappear() {
                Toast.makeText(this@DragBombActivity,"TextView消失了",Toast.LENGTH_SHORT).show()
            }
        })

        MessageBubbleView.attachView(btn,object :MessageBubbleView.BubbleDisapearListener{
            override fun disappear() {
                Toast.makeText(this@DragBombActivity,"按钮消失了",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
