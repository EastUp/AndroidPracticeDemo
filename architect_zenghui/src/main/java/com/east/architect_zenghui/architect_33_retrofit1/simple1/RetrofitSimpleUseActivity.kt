package com.east.architect_zenghui.architect_33_retrofit1.simple1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  Retrofit基本使用
 *  @author: jamin
 *  @date: 2020/3/16 2:23 PM
 * |---------------------------------------------------------------------------------------------------------------|
 */
class RetrofitSimpleUseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_simple_use)

        // OkHttp +RxJava + Retrofit
        RetrofitClient.getServiceApi().userlogin("east","1234")
            .enqueue(object:Callback<UserLoginResult>{
                override fun onFailure(call: Call<UserLoginResult>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<UserLoginResult>,
                    response: Response<UserLoginResult>
                ) {
                    var result = response.body()
                    if(result!!.isSuccessed()){

                    }
                }

            })
    }
}
