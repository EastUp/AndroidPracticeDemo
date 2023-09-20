package com.hencoder.xfermode.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.xfermode.R
import com.hencoder.xfermode.px

private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)

class XfermodeView(context: Context?, attrs: AttributeSet?) :
  View(context, attrs) {
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val bounds = RectF(150f.px, 50f.px, 300f.px, 200f.px)
  // 圆的区域包括了方
  private val circleBitmap = Bitmap.createBitmap(150f.px.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888)
  // 方的区域包括了圆
  private val squareBitmap = Bitmap.createBitmap(150f.px.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888)

  init {
    // 先用相对的位置处理 在 onDraw 33 行再进行屏幕绝对偏移绘制
    val canvas = Canvas(circleBitmap)  // 绘制圆形在这个 circleBitmap 中
    paint.color = Color.parseColor("#D81B60")
    canvas.drawOval(50f.px, 0f.px, 150f.px, 100f.px, paint)
    paint.color = Color.parseColor("#2196F3")
    canvas.setBitmap(squareBitmap) // 绘制方形在这个 squareBitmap 中
    canvas.drawRect(0f.px, 50f.px, 100f.px, 150f.px, paint)
  }

  override fun onDraw(canvas: Canvas) {
    val count = canvas.saveLayer(bounds, null)
    canvas.drawBitmap(circleBitmap, 150f.px, 50f.px, paint)
    paint.xfermode = XFERMODE
    canvas.drawBitmap(squareBitmap, 150f.px, 50f.px, paint)
    paint.xfermode = null
    canvas.restoreToCount(count)
  }
}