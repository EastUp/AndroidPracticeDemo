@[TOC](自定义View) 

## 一、自定义TextView

### onMeasure()
// 获取宽高的模式
var widthMode = MeasureSpec.getMode(widthMeasureSpec); // 获取前两位
var heightMode = MeasureSpec.getMode(heightMeasureSpec);

// 获取宽高的值 
var widthSize = MeasureSpec.getSize(widthMeasureSpec); // 获取后面30位
var heightSize = MeasureSpec.getSize(heightMeasureSpec);

MeasureSpec.AT_MOST : 在布局中指定了wrap_content   
MeasureSpec.EXACTLY : 在不居中指定了确切的值  100dp   match_parent  fill_parent 
MeasureSpec.UNSPECIFIED : 尽可能的大,很少能用到，ListView , ScrollView 在测量子布局的时候会用UNSPECIFIED 

ScrollView + ListView 会显示不全问题，

widthMeasureSpec heightMeasureSpec :是一个32位的值,前两位表示模式,后30位表示的是值  

### onDraw() 

``` kotlin
 override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*// 画文本
        canvas.drawText();
        // 画弧
        canvas.drawArc();
        // 画圆
        canvas.drawCircle();*/
}
```


### onTouch()  分析源码（不害怕）

``` kotlin
    /**
     *  处理跟用户交互的，手指触摸等等
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN ->  // 手指按下
                Log.e("TAG","手指按下")

            MotionEvent.ACTION_MOVE ->  // 手指移动
                Log.e("TAG","手指移动")

            MotionEvent.ACTION_UP ->  // 手指抬起
                Log.e("TAG","手指抬起")
        }

        invalidate()
        return super.onTouchEvent(event)
    }
```

### 自定义View的属性
自定义View的属性需要在  在res下的values下面新建attrs.xml

### 获取自定义View的属性

``` kotlin 
        //获取自定义的属性
        var typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyTextView)
        mText = typedArray.getString(R.styleable.MyTextView_myText)?:""
        mTextColor = typedArray.getColor(R.styleable.MyTextView_myTextColor,mTextColor)
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_myTextSize,sp2px(mTextSize)).toFloat()

        //回收
        typedArray.recycle()
```

### 获取基线

canvas.drawText(@NonNull String text, float x, float y, @NonNull Paint paint)  
参数示意:  

- text，文字内容
- x，文字从画布上开始绘制的x坐标（Canvas是一个原点在左上角的平面坐标系）
- y，Baseline所在的y坐标，不少人已开始以为y是绘制文字区域的底部坐标，其实是不正确的，这是两个概念
- paint，画笔，设置的文字的大小颜色等属性  
了解了文字绘制的方法，我们现在就了解一下这个参数y（Baseline）的计算方法。

[BaseLine详解](https://www.jianshu.com/p/057ce6b81c52)

获取baseLine有两种方式

``` kotlin
//dy 代表的是：高度的一半到 baseLine的距离
var fontMetricsInt = mPaint.fontMetricsInt
var dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom  
var dy = (fontMetricsInt.descent - fontMetricsInt.ascent)/2 - fontMetricsInt.descent  
var baseLine = height/2+dy
```





### 代码  
1. 先在values文件夹下新建attrs.xml文件

``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!--name 自定义View的名字 MyTextView-->
    <declare-styleable name="MyTextView">
        <!--
            name:属性名称 不能跟系统或自己定义了的属性名称重复

            format 格式: string 文字  color 颜色
                        dimension 宽高 字体大小 integer 数字
                        reference 资源(drawable/color)
        -->
        <attr name="myText" format="string"/>
        <attr name="myTextColor" format="reference|color"/>
        <attr name="myTextSize" format="dimension"/>
        <attr name="myMaxLength" format="integer"/>
        <!-- background 自定义View都是继承自View , 背景是由View管理的-->
        <!--<attr name="myBackground" format="reference|color"/>-->

        <!--枚举-->
        <attr name="myInputType">
            <enum name="number" value="1"/>
            <enum name="text" value="2"/>
            <enum name="password" value="3"/>
        </attr>
    </declare-styleable>
</resources>
```

2. 自定义MyTextView

``` kotlin
package com.east.customview.customtextview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 自定义TextView,还需在values-attrs文件夹中创建name为MyTextView的declare-styleable
 *  @author: East
 *  @date: 2019-11-07
 * |---------------------------------------------------------------------------------------------------------------|
 */
class MyTextView : View{

    var mText :String = ""
    var mTextColor : Int = Color.BLACK
    var mTextSize : Float = 15f

    lateinit var mPaint :Paint

    /**
     *  在代码中 new的时候调用
     *  var textview = MyTextView(this)
     */
    constructor(context: Context) : this(context,null)

    /**
     *  在xml中调用(attrs:在xml中指定的属性)
     *  <com.east.customview.customtextview.widget.MyTextView
     *      android:layout_width="wrap_content"
     *      android:layout_height="wrap_content"
     *      android:text="Hello World!" />
     */
    constructor(context: Context, attrs: AttributeSet?) : this(context,attrs,0)

    /**
     *  在xml中调用(attrs:在xml中指定的属性,defStyleAttr:调用了的style)
     *  <com.east.customview.customtextview.widget.MyTextView
     *       style="@style/defualt"
     *       android:text="Hello World!"
     */
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        //获取自定义的属性
        var typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyTextView)
        mText = typedArray.getString(R.styleable.MyTextView_myText)?:""
        mTextColor = typedArray.getColor(R.styleable.MyTextView_myTextColor,mTextColor)
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_myTextSize,sp2px(mTextSize)).toFloat()

        //回收
        typedArray.recycle()

        mPaint = Paint()
        mPaint.isAntiAlias = true //抗锯齿
        mPaint.textSize = mTextSize
        mPaint.color = mTextColor

        //  默认给一个背景
//        setBackgroundColor(Color.TRANSPARENT)

//        setWillNotDraw(false)

    }


    /**
     *  自定义View的测量方法,宽高都由这个方法这指定
     *  宽高模式有三种:
     *          MeasureSpec.AT_MOST : 在布局中指定了wrap_content
     *          MeasureSpec.EXACTLY : 在不居中指定了确切的值  100dp   match_parent  fill_parent
     *          MeasureSpec.UNSPECIFIED : 尽可能的大,很少能用到，ListView , ScrollView 在测量子布局的时候会用UNSPECIFIED
     *
     *  @param widthMeasureSpec 是一个32位的值,前两位表示模式,后30位表示的是值
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 指定控件的宽高，需要测量
        // 获取宽高的模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec) // 获取前两位
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        //1.确定的值,这个时候不需要计算,给多少就是多少
        var width = MeasureSpec.getSize(widthMeasureSpec)

        //2.给的是wrapcontent 需要计算
        if(widthMode == MeasureSpec.AT_MOST){
            //计算的宽度 与 字体的长度有关  与字体的大小  用画笔来测量
            var bounds = Rect()
            //// 获取文本的Rect
            mPaint.getTextBounds(mText,0,mText!!.length,bounds)
            width = bounds.width()+paddingLeft+paddingRight
        }

        var height = MeasureSpec.getSize(heightMeasureSpec)

        if(heightMode == MeasureSpec.AT_MOST){
            //计算的宽度 与 字体的长度有关  与字体的大小  用画笔来测量
            var bounds = Rect()
            //// 获取文本的Rect
            mPaint.getTextBounds(mText,0,mText!!.length,bounds)
            height = bounds.height()+paddingTop+paddingBottom
        }

        //设置控件的宽高
        setMeasuredDimension(width,height)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*// 画文本
        canvas.drawText();
        // 画弧
        canvas.drawArc();
        // 画圆
        canvas.drawCircle();*/

        // x 就是开始的位置   0
        // y 基线 baseLine   求？   getHeight()/2知道的   centerY
        //dy 代表的是：高度的一半到 baseLine的距离
        var fontMetricsInt = mPaint.fontMetricsInt
        // top 是一个负值  bottom 是一个正值    top，bttom的值代表是  bottom是baseLine到文字底部的距离（正值）
//        var dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom
        var dy = (fontMetricsInt.descent - fontMetricsInt.ascent)/2 - fontMetricsInt.descent
        var baseLine = height/2+dy
        var x = paddingLeft
        canvas.drawText(mText, x.toFloat(), baseLine.toFloat(),mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN ->  // 手指按下
                Log.e("TAG","手指按下")

            MotionEvent.ACTION_MOVE ->  // 手指移动
                Log.e("TAG","手指移动")

            MotionEvent.ACTION_UP ->  // 手指抬起
                Log.e("TAG","手指抬起")
        }

        invalidate()
        return super.onTouchEvent(event)
    }


    fun sp2px(sp:Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,resources.displayMetrics).toInt()
    }
```
	
