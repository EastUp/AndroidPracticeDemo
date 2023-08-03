package com.east.spannableString

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.east.spannableString.utils.SpanUtils
import kotlinx.android.synthetic.main.activity_autolink.*
import java.util.regex.Pattern


class AutoLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autolink)



//        tv.autoLinkMask =Linkify.ALL
        tv.text = "天下https://www.baidu.com无敌"

        interceptHyperLink(tv)


    }

    private fun interceptHyperLink(tv:TextView){
//        tv.movementMethod = LinkMovementMethod.getInstance()
//        Linkify.addLinks(tv,Linkify.ALL)
//        //要想达到效果必须设置在setText的前面
////        tv.autoLinkMask = Linkify.ALL
//        val charSequence = tv.text
//        if(charSequence is Spannable){
//            val spannable = tv.text as Spannable
//            val urlSpans = spannable.getSpans(0, spannable.length, URLSpan::class.java)
//            if(urlSpans.isEmpty())
//                return
//            var spannableStringBuilder = SpannableStringBuilder(charSequence)
//            spannableStringBuilder.clearSpans()
//            for (urlSpan in urlSpans) {
//                spannableStringBuilder.setSpan(object : MyCustomClickSpan(urlSpan.url){
//                    override fun onClick(widget: View) {
//                        Toast.makeText(this@AutoLinkActivity,"链接",Toast.LENGTH_SHORT).show()
//                    }
//                },spannable.getSpanStart(urlSpan),spannable.getSpanEnd(urlSpan),Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//            }
//            tv.text = spannableStringBuilder
//        }

        //设置超链接可点击
        tv.movementMethod = LinkMovementMethod.getInstance()
        val msg = tv.text.toString()
        var spannableStringBuilder = SpannableStringBuilder(tv.text)

        val pattern = Pattern.compile("[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.]]*", Pattern.CASE_INSENSITIVE)
        val m = pattern.matcher(msg)
        while (m.find()){
            var start  = m.start()
            var end  = m.end()
            spannableStringBuilder.setSpan(object : MyCustomClickSpan(msg.substring(start,end),Color.parseColor("#00ff00"),false){
                override fun onClick(widget: View) {
                    Toast.makeText(this@AutoLinkActivity,"链接",Toast.LENGTH_SHORT).show()
                }
            },start,end,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }

        val drawable = ContextCompat.getDrawable(this, R.drawable.timg)
        val imageSpanAppend = SpanUtils.setImageSpanAppend(
            spannableStringBuilder,
            drawable!!
        )

        tv.text = imageSpanAppend
        //设置点击后的背景高亮颜色
        tv.highlightColor = Color.TRANSPARENT
    }


    /**
     * 自定义超链接点击
     */
    abstract class MyCustomClickSpan() : ClickableSpan() {

        //超链接内容
        var url : String ?= null
        //超链接颜色
        var color : Int = -1
        //超链接是否有下划线
        var hasUnderLine : Boolean = true

        constructor(url: String) : this() {
            this.url = url
        }

        constructor(url: String,color:Int) : this(url) {
            this.color = color
        }

        constructor(url: String,hasUnderLine : Boolean) : this(url) {
            this.hasUnderLine = hasUnderLine
        }

        constructor(url: String,color:Int,hasUnderLine : Boolean) : this(url,color) {
            this.hasUnderLine = hasUnderLine
        }


        override fun updateDrawState(ds: TextPaint) {

            ds.color = if(color == -1) ds.linkColor else color
            ds.isUnderlineText = hasUnderLine

        }

    }

}
