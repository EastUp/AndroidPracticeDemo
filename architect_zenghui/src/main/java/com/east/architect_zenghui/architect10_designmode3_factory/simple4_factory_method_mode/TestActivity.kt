package com.east.architect_zenghui.architect10_designmode3_factory.simple4_factory_method_mode

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        tv.text = "4"

        var ioFactory = MemoryIOFactory()
        val ioHandler = ioFactory.createIOHandler()
        ioHandler.saveString("userName","liuwei")
        ioHandler.saveString("userAge","960518")
    }

    fun onClick(v: View){
        var ioFactory = MemoryIOFactory()
        val ioHandler = ioFactory.createIOHandler()
        val userName = ioHandler.getString("userName")
        val userAge = ioHandler.getString("userAge")
        tv.text = "userName = $userName  userAge = $userAge"
    }
}
