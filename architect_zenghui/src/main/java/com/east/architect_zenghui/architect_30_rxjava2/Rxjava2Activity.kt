package com.east.architect_zenghui.architect_30_rxjava2

import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_30_rxjava2.rxjava.Consumer
import com.east.architect_zenghui.architect_30_rxjava2.rxjava.Observable
import com.east.architect_zenghui.architect_31_rxjava3.rxjava.Function
import kotlinx.android.synthetic.main.activity_rxjava2.*
import java.net.HttpURLConnection
import java.net.URL

class Rxjava2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava2)

        // 1.观察者 Observable 被观察对象
        // Observer 观察者
        // subscribe 注册订阅
        // 1.观察者 Observable 被观察对象
        // Observer 观察者
        // subscribe 注册订阅
        Thread(Runnable {
            Observable.just("http://img.taopic.com/uploads/allimg/130331/240460-13033106243430.jpg")
                .map(object : Function<String, Bitmap> {
                    @Throws(Exception::class)
                    override fun apply(urlPath: String): Bitmap {
                        // 第五步
                        val url = URL(urlPath)
                        val urlConnection =
                            url.openConnection() as HttpURLConnection
                        val inputStream = urlConnection.inputStream
                        return BitmapFactory.decodeStream(inputStream)
                    }
                })
                .map(object : Function<Bitmap, Bitmap> {
                    @Throws(Exception::class)
                    override fun apply(@NonNull bitmap: Bitmap): Bitmap {
                        var bitmap = bitmap
                        bitmap = createWatermark(bitmap, "RxJava2.0")
                        return bitmap
                    }
                })
                .map(object : Function<Bitmap, Bitmap> {
                    @Throws(Exception::class)
                    override fun apply(bitmap: Bitmap): Bitmap{
                        return bitmap
                    }
                })
                .subscribe(object : Consumer<Bitmap>{
                    override fun onNext(bitmap: Bitmap) {
                        // 第七步
                        Log.e("TAG", "item = $bitmap")
                        runOnUiThread { iv.setImageBitmap(bitmap) }
                    }
                })
        }).start()
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
