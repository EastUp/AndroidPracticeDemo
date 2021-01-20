@[TOC](仿QQ运动步数) 

## 一、仿QQ运动步数(圆弧/文字居中)


### 重要方法解释

**canvas.drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter,@NonNull Paint paint)**

- oval:圆弧边界
- startAngle:开始的角度,水平右边的角度为0f
- sweepAngle:画笔画圆弧时扫过的角度.
- useCenter:圆中心到圆弧起始/末位点的线不画
- paint:画笔


**invalidate()**

- 会重新调用onDraw()方法
- 这个控件的所有父布局的OnDraw方法都会调用一遍
- 所以注意过度渲染问题

```
	p.invalidateChild(this, damage);   //Parent父类

    parent = parent.invalidateChildInParent(location, dirty); 
    if (view != null) {
        // Account for transform on current parent
        Matrix m = view.getMatrix();
        if (!m.isIdentity()) {
            RectF boundingRect = attachInfo.mTmpTransformRect;
            boundingRect.set(dirty);
            m.mapRect(boundingRect);
            dirty.set((int) Math.floor(boundingRect.left),
                    (int) Math.floor(boundingRect.top),
                    (int) Math.ceil(boundingRect.right),
                    (int) Math.ceil(boundingRect.bottom));
        }
    }
} while (parent != null);

mView.draw(canvas);
```

<font color=red>invlidate() 流程 ：一路往上跑 ，跑到最外层

draw()-> dispatchDraw()  一路往下画 最终画到当前调用invaldate的View的onDraw()方法

invlidate() 牵连着整个layout布局中的View</font>

**为什么不能在子线程中更新UI ?** 

开了线程，更新UI  一般会调用setText()、setImageView()调回调到这里面来 ViewRootImpl checkThread()

`checkThread()` 用来检测线程

``` java
void checkThread() {
    if (mThread != Thread.currentThread()) {
        throw new CalledFromWrongThreadException(
                "Only the original thread that created a view hierarchy can touch its views.");
    }
}
```

mThread 在构造函数中初始化的 Thread.currentThread() 主线程mainThread

UI的绘制流程  `performTraversals()` 非常重要  `performMeasure()``performLayout()` `performDraw()`


**高级面试题讲解（如何像WX朋友圈一样优化过度渲染**

看自己界面有没有过度渲染  开发者选项   打开调试GPU过度绘制，不要见红  网上找（99%） 优化（不是特别靠谱）

自己写一些界面会非常复杂  QQ空间   WX朋友圈   列表嵌套列表（ Item里面布局可能嵌套布局）

1. 网上的解决方案 :  
	尽量不要嵌套  
	能不设置背景不要设置背景  
	........

 2. 最好的解决方案（蛋疼）  
    获取到数据去设置  setText()、setImageView其实 onInvalidate()。最好是自己画，不要用系统的嵌套布局    运行效率高，实现功能效率低（抉择问题） 

### 当自定义属性格式为：reference|color

``` kotlin
//获取xml中设置的值
mOuterColor = arr.getColorStateList(R.styleable.StepView_outerColor)?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.BLUE)
        )
//设置画笔颜色        
mOuterPaint.color = mOuterColor.getColorForState(drawableState,0)//设置画笔颜色
```


### attrs.xml代码：

``` kotlin
    <declare-styleable name="StepView">
        <!-- 底部圆弧背景颜色 -->
        <attr name="outerColor" format="reference|color"/>
        <!-- 顶部圆弧背景颜色 -->
        <attr name="innerColor" format="reference|color"/>
        <!-- 圆弧的宽度 -->
        <attr name="borderWidth" format="dimension"/>
        <!-- 最大步数 -->
        <attr name="stepMax" format="integer"/>
        <!-- 已经走了的步数 -->
        <attr name="stepProgress" format="integer"/>
        <!-- 中间步数的文字颜色 -->
        <attr name="stepTextColor" format="reference|color"/>
        <!-- 中间步数的文字大小 -->
        <attr name="stepTextSize" format="dimension"/>
    </declare-styleable>
}
```

### StepView代码：

``` kotlin
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  仿QQ运动步数
 *  @author: East
 *  @date: 2019-11-08
 * |---------------------------------------------------------------------------------------------------------------|
 */
class StepView @JvmOverloads constructor(
    context:Context,
    attrs: AttributeSet ?= null,
    defStyleAttr : Int = 0
):View(context,attrs,defStyleAttr) {

    private lateinit var mOuterColor:ColorStateList
    private lateinit var mInnerColor:ColorStateList
    private var mBorderWidth:Float = dp2px(8f)
    private var mTextColor:ColorStateList
    private var mTextSize:Float = sp2px(12f)

    private lateinit var mOuterPaint:Paint
    private lateinit var mInnerPaint:Paint
    private lateinit var mTextPaint:Paint


    private var mStepMax = 100
    private var mCurrentStep = 50

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.StepView)
        mOuterColor = arr.getColorStateList(R.styleable.StepView_outerColor)?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.BLUE)
        )
        mInnerColor = arr.getColorStateList(R.styleable.StepView_innerColor)?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.RED)
        )
        mBorderWidth = arr.getDimension(R.styleable.StepView_borderWidth,mBorderWidth)
        mTextColor = arr.getColorStateList(R.styleable.StepView_stepTextColor) ?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.BLACK)
        )
        mTextSize = arr.getDimensionPixelSize(R.styleable.StepView_stepTextSize, mTextSize.toInt()).toFloat()
        mStepMax = arr.getInt(R.styleable.StepView_stepMax,mStepMax)
        mCurrentStep = arr.getInt(R.styleable.StepView_stepProgress,mCurrentStep)

        arr.recycle()

        mOuterPaint = Paint()
        mOuterPaint.isAntiAlias = true // 抗锯齿
        mOuterPaint.style = Paint.Style.STROKE //空心画笔
        mOuterPaint.strokeCap = Paint.Cap.ROUND //笔画以半圆突出,中心在路径的结尾端
        mOuterPaint.strokeWidth = mBorderWidth //设置笔画宽度
        mOuterPaint.color = mOuterColor.getColorForState(drawableState,0)//设置画笔颜色

        mInnerPaint = Paint()
        mInnerPaint.isAntiAlias = true // 抗锯齿
        mInnerPaint.style = Paint.Style.STROKE //空心画笔
        mInnerPaint.strokeCap = Paint.Cap.ROUND //笔画以半圆突出,中心在路径的结尾端
        mInnerPaint.strokeWidth = mBorderWidth //设置笔画宽度
        mInnerPaint.color = mInnerColor.getColorForState(drawableState,0)//设置画笔颜色

        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true // 抗锯齿
        mTextPaint.textSize = mTextSize
        mTextPaint.color = mTextColor.getColorForState(drawableState,0)//设置画笔颜色

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //如果是AT_MOST模式证明是wrap_content则需要手动指定一个默认大小给到显示

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        if(widthMode == MeasureSpec.AT_MOST)
            width = dp2px(200f).toInt()

        if(heightMode == MeasureSpec.AT_MOST)
            height = dp2px(200f).toInt()

        width = if(width >= height) height else width //取最短的作为宽也就是圆弧的直径

        var radius = width.toDouble()/2 // 这个圆弧的半径

        // 圆弧的实际宽高
//        setMeasuredDimension(width, width/2+sqrt(radius*radius/2).toInt())

        //宽高是整个圆的宽高
        setMeasuredDimension(width, width)
    }


    override fun onDraw(canvas: Canvas) {
        /**
         * left :mBorderWidth/2 ,mBorderWidth的一般开始画,就可以贴边
         * top :mBorderWidth/2 ,mBorderWidth的一般开始画,就可以贴边
         * right :width-mBorderWidth/2 ,画到宽度减去画笔宽度的一般,这样右边画笔也可以贴边
         */
        val rect = RectF(mBorderWidth/2,mBorderWidth/2,width-mBorderWidth/2,width-mBorderWidth/2)

        if(mCurrentStep == 0)return

        //画底部圆弧
        /**
         * rect : 圆弧的边界
         * 135f : 开始的角度,水平右边的角度为0f
         * 270f : 画笔画圆弧时扫过的角度.
         * false : 圆中心到圆弧起始/末位点的线不画
         * mOuterPaint : 画笔
         */
        canvas.drawArc(rect,135f,270f,false,mOuterPaint)

        //画顶部圆弧
        canvas.drawArc(rect,135f,(mCurrentStep.toFloat()/mStepMax)*270f,false,mInnerPaint)

        //画中心文字
        val stepProgress = mCurrentStep.toString()
        var textBounds = Rect()
        mTextPaint.getTextBounds(stepProgress,0, stepProgress.length,textBounds)
        val fontMetricsInt = mTextPaint.fontMetricsInt
        var dy = (fontMetricsInt.descent - fontMetricsInt.ascent)/2 - fontMetricsInt.descent
        canvas.drawText(stepProgress, (width/2-textBounds.width()/2).toFloat(),
            (width/2+dy).toFloat(),mTextPaint)
    }

    @Synchronized
    fun setCurrentStep(currentStep : Int){
        mCurrentStep = currentStep

        /**
         * 会重新调用onDraw()方法
         * 这个控件的所有父布局的OnDraw方法都会调用一遍
         * 所以注意过度渲染问题
         */
        invalidate()
    }

    @Synchronized
    fun setStepMax(max:Int){
        this.mStepMax = max
    }


    fun sp2px(sp:Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,resources.displayMetrics)
    }

    fun dp2px(dp:Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.displayMetrics)
    }
}
```


### activity中设置动画  
``` kotlin
	stepView.setStepMax(4000)
	
	var animator = ValueAnimator.ofFloat(0f,3000f)
	animator.duration = 1000
	animator.interpolator = DecelerateInterpolator() //开始快,后面慢
	animator.addUpdateListener {
	    val value = it.animatedValue as Float
	    stepView.setCurrentStep( value.toInt())
	}
	
	animator.start()
```
	
