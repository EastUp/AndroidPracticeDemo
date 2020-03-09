package com.east.architect_zenghui.architect_26_okhttp4_interceptor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.*
import java.io.IOException

class OkHttp4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http4)
        val requestBody: RequestBody = RequestBody()
            .type(RequestBody.FORM)
            .addParam("pageNo", 1.toString() + "")
            .addParam("platform", "android")
        val request: Request = Request.Builder()
            .url("https://api.saiwuquan.com/api/appv2/sceneModel")
            .post(requestBody)
            .build()

        val okHttpClient = OkHttpClient()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                //e.printStackTrace();
                Log.e("TAG", "出错了")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response) {
                val result: String = response.string()
                Log.e("TAG", result)
            }
        })
    }
}
