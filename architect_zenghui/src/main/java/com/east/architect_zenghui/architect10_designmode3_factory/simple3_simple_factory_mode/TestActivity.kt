package com.east.architect_zenghui.architect10_designmode3_factory.simple3_simple_factory_mode

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        tv.text = "3"

        val ioHandler = IOHandlerFactory.createIOHandler(IOHandlerFactory.IOType.MEMORY)
        ioHandler.saveString("userName","liuwei")
        ioHandler.saveString("userAge","960518")
    }


    fun onClick(v: View){
        val ioHandler = IOHandlerFactory.createIOHandler(IOHandlerFactory.IOType.MEMORY)
        val userName = ioHandler.getString("userName")
        val userAge = ioHandler.getString("userAge")
        tv.text = "userName = $userName  userAge = $userAge"
    }
}
