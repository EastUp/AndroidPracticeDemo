package com.east.architect_zenghui.architect_27_okhttp5.simple_cache

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import okhttp3.*
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

class OkHttp5CacheActivity : AppCompatActivity() {

    private lateinit var mHttpClient:OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http5)

        var file = File(getExternalFilesDir(Environment.DIRECTORY_DCIM),"cache")
        var cache = Cache(file,10*1024*1024)

        mHttpClient = OkHttpClient.Builder()
            .cache(cache)
            // 加载最前 过期时间缓存多少秒
            .addInterceptor(CacheRequestInterceptor(this))
            // 加载最后,数据缓存 过期时间 30s
            .addNetworkInterceptor(CacheResponseInterceptor())
            .build()

    }

    fun onClick(view: View) {
        val url = "https://api.saiwuquan.com/api/appv2/sceneModel"
        // 构建一个请求
        val request = Request.Builder()
            .url(url).build()
        // new RealCall 发起请求
        val call: Call = mHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.e("TAG", response.body!!.string())
                response.body!!.close()
                // 都是有 第一把，第二把没有网络的了只有缓存的 (30s 以内)，过了 30s 之后又会有网络的了（会再请求更新）
                Log.e(
                    "TAG",
                    response.cacheResponse.toString() + " ; " + response.networkResponse
                )
            }
        })
    }
}
