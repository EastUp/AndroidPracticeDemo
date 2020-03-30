package com.east.architect_zenghui.architect_33_retrofit2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_33_retrofit2.retrofit.Call
import com.east.architect_zenghui.architect_33_retrofit2.simple.RetrofitClient
import com.east.architect_zenghui.architect_33_retrofit2.retrofit.Callback
import com.east.architect_zenghui.architect_33_retrofit2.retrofit.Response
import com.east.architect_zenghui.architect_33_retrofit2.simple.UserInfo

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  自己动手写核心架构部分
 *  @author: jamin
 *  @date: 2020/3/16 9:36 AM
 * |---------------------------------------------------------------------------------------------------------------|
 */
class Retrofit2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit2)

        RetrofitClient.getServiceApi().userlogin("Darren", "940223")
            .enqueue(object : Callback<Result<UserInfo>>{
                override fun onResponse(
                    call: Call<Result<UserInfo>>?,
                    response: Response<Result<UserInfo>>?
                ) {
                    Log.e("TAG", response!!.body.toString())
                }

                override fun onFailure(call: Call<Result<UserInfo>>?, t: Throwable) {

                }

            })
    }
}
