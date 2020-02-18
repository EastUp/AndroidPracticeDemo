package com.east.architect_zenghui.architect10_designmode3_factory.simple5_abstract_factory_mode

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        tv.text = "5"
        val ioHandler = IOHandlerFactory.getMemoryIOHandler()
        ioHandler.saveString("userName","5liuwei")
        ioHandler.saveString("userAge","960518")
    }

    fun onClick(v: View){
        val ioHandler = IOHandlerFactory.getMemoryIOHandler()
        val userName = ioHandler.getString("userName")
        val userAge = ioHandler.getString("userAge")
        tv.text = "userName = $userName  userAge = $userAge"
    }
}
