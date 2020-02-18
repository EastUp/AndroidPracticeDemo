package com.east.architect_zenghui.architect10_designmode3_factory.simple2_preferenceutil

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        tv.text = "2"

        val preferencesUtils = PreferencesUtils.getInstance()
        preferencesUtils.init(this)

        preferencesUtils.saveString("userName","eastrise").saveString("userAge","19941230").commit()
    }


    fun onClick(v: View){
        val preferencesUtils = PreferencesUtils.getInstance()
        val userName = preferencesUtils.getString("userName")
        val userAge = preferencesUtils.getString("userAge")
        tv.text = "userName = $userName  userAge = $userAge"
    }
}
