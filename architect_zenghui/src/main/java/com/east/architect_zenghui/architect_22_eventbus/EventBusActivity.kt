package com.east.architect_zenghui.architect_22_eventbus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import kotlinx.android.synthetic.main.activity_event_bus.*

class EventBusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_bus)
        EventBus.getDefault().register(this)

        test_tv.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this,
                TestActivity::class.java
            )
            startActivity(intent)
        })
    }

    /**
     * threadMode 执行的线程方式
     * priority 执行的优先级
     * sticky 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 50, sticky = true)
    fun test1(msg: String) { // 如果有一个地方用 EventBus 发送一个 String 对象，那么这个方法就会被执行
        Log.e("TAG", "msg1 = $msg")
        test_tv.text = msg
    }

    /**
     * threadMode 执行的线程方式
     * priority 执行的优先级，值越大优先级越高
     * sticky 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100, sticky = true)
    fun test2(msg: String) { // 如果有一个地方用 EventBus 发送一个 String 对象，那么这个方法就会被执行
        Log.e("TAG", "msg2 = $msg")
        test_tv.text = msg
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
