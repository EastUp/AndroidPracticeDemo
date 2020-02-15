package com.east.architect_zenghui.architect6_handler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.east.architect_zenghui.R

class HandlerActivity : AppCompatActivity() {

    private var mHander = object :Handler(){
        override fun handleMessage(msg: Message) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)

        Thread(
            Runnable {
                Thread.sleep(3000)

                var message1 = Message.obtain()
                mHander.sendMessage(message1)

                var message2 = Message.obtain()
                mHander.sendMessageDelayed(message2,10000)

                var message3 = Message.obtain()
                mHander.sendMessageDelayed(message3,500)

                //以上添加到消息列表中的顺序应该是 message1 message3 message2


                Looper.prepare() //准备循序,里面创建了Looper,Looper的构造方法中又创建了MessageQueue
                var handler = Handler()  //如果没有Looper 会报错:  Can't create handler inside thread " + Thread.currentThread() " that has not called Looper.prepare()
                Looper.loop() //循环
            }
        ).start()
    }
}
