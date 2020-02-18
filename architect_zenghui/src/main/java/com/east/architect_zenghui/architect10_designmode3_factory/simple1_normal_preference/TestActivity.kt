package com.east.architect_zenghui.architect10_designmode3_factory.simple1_normal_preference

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var preferences = getSharedPreferences("cache", Context.MODE_PRIVATE)
        preferences.edit().putString("userName","east").putString("userAge","941220").commit()
    }

    fun onClick(v: View){
        var preferences = getSharedPreferences("cache", Context.MODE_PRIVATE)
        val userName = preferences.getString("userName", "")
        val userAge = preferences.getString("userAge", "")
        tv.text = "userName = $userName  userAge = $userAge"
    }
}
