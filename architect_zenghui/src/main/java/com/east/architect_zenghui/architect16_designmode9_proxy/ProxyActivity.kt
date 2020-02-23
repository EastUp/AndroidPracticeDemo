package com.east.architect_zenghui.architect16_designmode9_proxy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect16_designmode9_proxy.simple3.EastRetrofit
import com.east.architect_zenghui.architect16_designmode9_proxy.simple3.ServiceInterface

class ProxyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proxy)

        var eastRetrofit = EastRetrofit()

        // 核心代码 ServiceInterface.class 接口的 Class 会返回一个 ServiceInterface 的实例对象
        val serviceInterface = eastRetrofit.create(ServiceInterface::class.java)

        // 能看懂
         var result = serviceInterface.userLogin(null)
         Log.e("TAG", "返回值 = $result")

        serviceInterface.userRegister()

    }
}
