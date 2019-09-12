package com.east.navigation.advance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.east.navigation.R

class CustomerBackNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_back_navigation)


        //添加返回回调 one two three 调用顺序为three/two/one ,一个执行后面的都不执行
        onBackPressedDispatcher.addCallback(object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Toast.makeText(this@CustomerBackNavigationActivity,"测试下拦截返回",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
