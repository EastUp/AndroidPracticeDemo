package com.east.architect_zenghui.architect_27_okhttp5

import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.net.URLConnection

class OkHttp5Activity : AppCompatActivity() {

    var toast:Toast ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http5)

        // 这个是 Okhttp 上传文件的用法

        // 这个是 Okhttp 上传文件的用法
        val url = "https://api.saiwuquan.com/api/upload"
        val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "test.apk")
        val client = OkHttpClient()
        // 构建请求 Body , 这个我们之前自己动手写过
        var builder =  MultipartBody.Builder()
            .addFormDataPart("platform", "android")
            .addFormDataPart("file",file.name,file.asRequestBody(guessMimeType(file.absolutePath).toMediaTypeOrNull()))

        // 怎么监听上传文件的进度？
        var exMultipartBody = ExMultipartBody(builder.build(),object :UploadProgressListener{
            override fun onProgress(total: Long, current: Long) {
                showToast(total,current)
            }
        })

        // 构建一个请求
        val request = Request.Builder()
            .url(url)
            .post(exMultipartBody).build()
        // new RealCall 发起请求
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.e("TAG", response.body!!.string())
            }
        })

    }

    private fun showToast(total: Long, current: Long) {
        runOnUiThread {
            if(toast == null)
                toast = Toast.makeText(this, "$current/$total",Toast.LENGTH_LONG)
            toast!!.setText("$current/$total")
            toast!!.show()
        }
    }


    fun guessMimeType(filePath:String) : String{
        var fileNameMap = URLConnection.getFileNameMap();

        val mimType = fileNameMap.getContentTypeFor(filePath)
        return if (TextUtils.isEmpty(mimType)) {
            "application/octet-stream"
        } else mimType
    }
}
