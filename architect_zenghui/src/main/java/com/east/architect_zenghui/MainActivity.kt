package com.east.architect_zenghui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.architect1_change_network_engine.simple3.MainActivity
import com.east.architect_zenghui.architect2_aop.AopActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v:View){
        when(v){
            //面向对象6大原则-网络引擎切换
            change_netword_engine -> startActivity(Intent(this,MainActivity::class.java))
            //
            aop -> startActivity(Intent(this,AopActivity::class.java))

        }
    }
}
