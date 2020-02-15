package com.east.architect_zenghui.architect8_designmode1_singleton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect8_designmode1_singleton.manager.ActivityManager
import kotlinx.android.synthetic.main.activity_my_main.*

class MyMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_main)
        ActivityManager.getInstance().attach(this)
    }

    fun onClick(v: View){
        when(v){
            // 进入登录界面
            tv -> startActivity(Intent(this,LoginActivity::class.java))
            // 退出程序
            btn ->{
                ActivityManager.getInstance().exitApplication()
            }
        }
    }

    override fun onDestroy() {
        ActivityManager.getInstance().detach(this)
        super.onDestroy()
    }
}
