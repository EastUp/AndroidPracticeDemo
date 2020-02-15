package com.east.architect_zenghui.architect8_designmode1_singleton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect8_designmode1_singleton.kotlin.simple2.sync.Singleton3
import com.east.architect_zenghui.architect8_designmode1_singleton.manager.ActivityManager

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ActivityManager.getInstance().attach(this)
    }

    fun onClick(v: View){
        startActivity(Intent(this,RegisterActivity::class.java))
    }

    override fun onDestroy() {
        ActivityManager.getInstance().detach(this)
        super.onDestroy()
    }
}
