package com.east.arouterpractice.testactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.east.arouterpractice.R

@Route(path = "/arouter1/activity2")
class TestActivity2 : AppCompatActivity() {

    @Autowired(name = "key1")
    @JvmField
    var value : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
        ARouter.getInstance().inject(this)

//        val value = intent.getStringExtra("key1")
        if (!TextUtils.isEmpty(value)) {
            Toast.makeText(this, "exist param :$value", Toast.LENGTH_LONG).show()
        }


        setResult(999, Intent().apply {
            putExtra("result","/arouter1/activity2返回的结果")
        })
    }
}
