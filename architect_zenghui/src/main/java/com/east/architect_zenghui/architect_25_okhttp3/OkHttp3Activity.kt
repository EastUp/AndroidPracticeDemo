package com.east.architect_zenghui.architect_25_okhttp3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_25_okhttp3.okhttp.*
import java.io.File
import java.io.IOException

class OkHttp3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http3)

        val file = File("")

        val client = OkHttpClient()
        val requestBody: RequestBody = RequestBody()
            .type(RequestBody.FORM)
            .addParam("file", RequestBody.create(file))
            .addParam("file2", RequestBody.create(file))
            .addParam("pageSize", 1.toString() + "")

        val request: Request = Request.Builder()
            .url("https://api.saiwuquan.com/api/appv2/sceneModel")
            .post(requestBody).build()

        val call: Call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response) {
                Log.e("TAG", response.string())
                //Log.e("TAG",response.string());
            }
        })
    }
}
