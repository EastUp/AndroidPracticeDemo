package com.east.architect_zenghui.architect_29_rxjava1

import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import kotlinx.android.synthetic.main.activity_rxjava1.*
import java.net.HttpURLConnection
import java.net.URL

class SimpleActivity : AppCompatActivity() {

    private var mHandler = Handler(Handler.Callback {
        iv.setImageBitmap(it.obj as Bitmap)
        true
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava1)

        Thread(
            Runnable {
                var url =
                    URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583650205989&di=9f7c377fd7e7979d3d785b1f73c44d86&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2018-08-17%2F5b76383262774.jpg")
                var connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                var bitmap = BitmapFactory.decodeStream(inputStream)
                //加个水印
                bitmap = createWatermark(bitmap,"Rxjava")
                // 显示到图片
                var message = Message.obtain(mHandler)
                message.obj = bitmap
                message.sendToTarget()
            }
        ).start()
    }

    /**
     * 加水印
     */
    fun createWatermark(bitmap: Bitmap, mark: String) :Bitmap{
        var w = bitmap.width
        var h = bitmap.height
        var bmp = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bmp)
        var paint = Paint()
        // 水印颜色
        paint.color = Color.parseColor("#C5FF0000")
        // 水印字体大小
        paint.textSize = 150f
        //抗锯齿
        paint.isAntiAlias = true
        canvas.drawBitmap(bitmap,0f,0f,paint)

        //文字的边界
        var bounds = Rect()
        paint.getTextBounds(mark,0,mark.length,bounds)

        val fontMetrics = paint.fontMetrics
        var dy = (fontMetrics.bottom-fontMetrics.top)/2-fontMetrics.bottom
        var baseline = h-bounds.height()/2 +dy
        //绘制文字
        canvas.drawText(mark, (w-bounds.width()).toFloat(),baseline,paint)
        return bmp
    }
}
