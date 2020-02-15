package com.east.architect_zenghui.architect8_designmode1_singleton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect8_designmode1_singleton.manager.ActivityManager

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ActivityManager.getInstance().attach(this)
    }

    fun onClick(v: View){
        ActivityManager.getInstance().finish(this)
        ActivityManager.getInstance().finish(LoginActivity::class.java)
    }

    override fun onDestroy() {
        ActivityManager.getInstance().detach(this)
        super.onDestroy()
    }
}
