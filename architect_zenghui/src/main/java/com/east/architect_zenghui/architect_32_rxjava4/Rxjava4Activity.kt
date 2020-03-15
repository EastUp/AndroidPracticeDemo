package com.east.architect_zenghui.architect_32_rxjava4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_32_rxjava4.operate.OperateActivity
import com.east.architect_zenghui.architect_32_rxjava4.rxlogin.LoginActivity
import com.east.architect_zenghui.architect_32_rxjava4.rxpermission.PermissionActivity
import kotlinx.android.synthetic.main.activity_rxjava4.*

class Rxjava4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava4)
    }

    fun onClick(v: View){
        when(v){
            operate -> startActivity(Intent(this,OperateActivity::class.java))
            rxpermission -> startActivity(Intent(this,PermissionActivity::class.java))
            rxlogin -> startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}
