package com.east.architect_zenghui.architect_28_okhttp6_download

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.east.architect_zenghui.BuildConfig
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_28_okhttp6_download.download.DownloadCallback
import com.east.architect_zenghui.architect_28_okhttp6_download.download.DownloadFacade
import java.io.File
import java.io.IOException

class OkHttp6Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http6)

        // 有三个类需要用户去关注，后面我们有可能会自己去更新代码，用户就需要换调用方式
        // 调用的方式 门面

        // 有三个类需要用户去关注，后面我们有可能会自己去更新代码，用户就需要换调用方式
        // 调用的方式 门面
        DownloadFacade.getFacade().init(this)

        DownloadFacade.getFacade()
            .startDownload(
                "http://acj3.pc6.com/pc6_soure/2017-11/com.ss.android.essay.joke_664.apk",
                object : DownloadCallback{
                    override fun onFailure(e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onSucceed(file: File?) {
                        installFile(file!!)
                    }
                })

        // 多线断点下载，只要客户端做一下处理就可以了
        // 什么叫做断点续传，逻辑是什么？
        // 如果下载中断（网络断开，程序退出），下次可以接着上次的地方下载
        // 多线程的逻辑是什么？多个线程读后台文件每个线程只负责读取单独的内容

        // 文件更新 ，专门下载apk软件（应用宝，迅雷，百度云）

        // 文件更新，1. 可以直接跳转到浏览器更新，2.直接下载不断点，也不多线程（OkHttp）3.多线程 4. 多线程加断点

        // 专门下载apk软件：多线程 + 断点，最多只能同时下载几个文件，一些正在下载，一些暂停，一些准备，参考 OKHttp 源码 Dispatch 的逻辑

        // 4. 多线程加断点

        /*var call = OkHttpManager.getDefault().asyncCall("http://acj3.pc6.com/pc6_soure/2017-11/com.ss.android.essay.joke_664.apk")

        call.enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {

            }
            override fun onResponse(call: Call, response: Response) {
                // 不断的读写文件，单线程
                val inputStream = response.body!!.byteStream()
                var file = File(cacheDir,"x02345.apk");

                var outputStream = FileOutputStream(file)

                var len = 0
                val buffer = ByteArray(1024 * 10)

                while ((inputStream.read(buffer).also { len = it })!= -1) {
                    outputStream.write(buffer, 0, len)
                }

                inputStream.close()
                outputStream.close()

                installFile(file)
            }

        })*/
    }

    private fun installFile(file: File) {
        // 核心是下面几句代码
        val intent =
            Intent(Intent.ACTION_VIEW)
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val contentUri: Uri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID.toString() + ".fileProvider",
                file
            )
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(
                Uri.fromFile(file),
                "application/vnd.android.package-archive"
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

}
