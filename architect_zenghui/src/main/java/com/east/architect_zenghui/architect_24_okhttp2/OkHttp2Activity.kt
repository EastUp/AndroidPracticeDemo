package com.east.architect_zenghui.architect_24_okhttp2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import okhttp3.*
import java.io.IOException

class OkHttp2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http2)

        var okHttpClient = OkHttpClient()

        var request = Request.Builder()
            // 307    Location:https://www.baidu.com
            //  1. 构建一个请求 ，url,端口，请求头的一些参数，表单提交（contentType,contentLength）
            .url("http://www.baidu.com")
            .build()
        //  2. 把 Request 封装转成一个 RealCall
        var call = okHttpClient.newCall(request)
        // 3. enqueue 队列处理 执行
        call.enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                // 1-3 中小型企业
            }

            override fun onResponse(call: Call, response: Response) {
                var result = response.body!!.string()
                Log.e("TAG",result)
            }

        })
    }
}
