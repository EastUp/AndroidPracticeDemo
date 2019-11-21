@[TOC](变色字体) 

## 一、变色字体(canvas.clipRect)


### 两种方式直接上代码

第一种画drawable

``` kotlin 
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 仿淘宝评价 RatingBar
 *  @author: East
 *  @date: 2019-11-13
 * |---------------------------------------------------------------------------------------------------------------|
 */
class RatingBar @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet ?= null,
    defStyleAttr : Int = 0
) :View(context,attrs,defStyleAttr) {

    private var mStarNormal : Drawable
    private var mStarFocus : Drawable
    private var mStarNumber : Int
    private var mPadding : Float

    private var mSelectStarNumber = 0//选中的星星
    init {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar)
        mStarNormal = typedArray.getDrawable(R.styleable.RatingBar_starNormal)?:resources.getDrawable(R.drawable.star_normal)
        mStarFocus = typedArray.getDrawable(R.styleable.RatingBar_starFocus)?:resources.getDrawable(R.drawable.star_selected)
        mStarNumber = typedArray.getInt(R.styleable.RatingBar_starNumber,5)
        mPadding = typedArray.getDimension(R.styleable.RatingBar_ratingBarPadding,dip2px(10f))
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = mStarNormal.intrinsicWidth*mStarNumber+mPadding*(mStarNumber+1)
        setMeasuredDimension(width.toInt(),mStarNormal.intrinsicHeight)
    }

    override fun onDraw(canvas: Canvas) {
        for(i in 0 until mStarNumber){
            val left = (i + 1) * mPadding + i * mStarNormal.intrinsicWidth
            mStarNormal.bounds = Rect(left.toInt(),0,
                (left + mStarNormal.intrinsicWidth).toInt(),mStarNormal.intrinsicHeight)
            mStarNormal.draw(canvas)
        }

        for(i in 0 until mSelectStarNumber){
            val left = (i + 1) * mPadding + i * mStarNormal.intrinsicWidth
            mStarFocus.bounds = Rect(left.toInt(),0,
                (left + mStarNormal.intrinsicWidth).toInt(),mStarNormal.intrinsicHeight)
            mStarFocus.draw(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE -> {
                val x = event.x  //相对于父View的水平距离 rawX是相对于屏幕的水平距离
                val fl = mPadding + mStarNormal.intrinsicWidth
                var currentStarNumber = (x / fl).toInt()
                if(x - fl*currentStarNumber > mPadding)
                    currentStarNumber += 1

                if(currentStarNumber!=mSelectStarNumber){  //减少invalidate的调用,优化UI
                    mSelectStarNumber = currentStarNumber
                    invalidate()
                }
            }
        }

        return true //将事件继续分发下去
    }



    fun dip2px(dip:Float):Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,resources.displayMetrics)
    }
}
```

第二种画Bitmap

``` koltin
public class RatingBar extends View {
    private static final String TAG = "RatingBar";

    private Bitmap mStarNormalBitmap, mStarFocusBitmap;
    private int mGradeNumber = 5;

    private int mCurrentGrade = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int starNormalId = array.getResourceId(R.styleable.RatingBar_starNormal, 0);
        if (starNormalId == 0) {
            throw new RuntimeException("请设置属性 starNormal ");
        }

        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);

        int starFocusId = array.getResourceId(R.styleable.RatingBar_starFocus, 0);
        if (starFocusId == 0) {
            throw new RuntimeException("请设置属性 starFocus ");
        }

        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);

        mGradeNumber = array.getInt(R.styleable.RatingBar_gradeNumber, mGradeNumber);

        array.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 高度  一张图片的高度， 自己去实现 padding  + 加上间隔
        int height = mStarFocusBitmap.getHeight();
        int width = mStarFocusBitmap.getWidth() * mGradeNumber;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mGradeNumber; i++) {
            // i*星星的宽度
            int x = i * mStarFocusBitmap.getWidth();

            // 结合第二个步骤 触摸的时候mCurrentGrade值是不断变化
            if(mCurrentGrade>i){// 1  01
                // 当前分数之前
                canvas.drawBitmap(mStarFocusBitmap, x, 0, null);
            }else{
                canvas.drawBitmap(mStarNormalBitmap, x, 0, null);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 移动 按下 抬起 处理逻辑都是一样，判断手指的位置，根据当前位置计算出分数，再去刷新显示
        Log.e(TAG, "moveXE -> " + event.getAction() +"");
        switch (event.getAction()) {
            // case MotionEvent.ACTION_DOWN: // 按下 尽量减少onDraw()的调用
            case MotionEvent.ACTION_MOVE: // 移动
            // case MotionEvent.ACTION_UP: // 抬起 尽量减少onDraw()的调用
                float moveX = event.getX();//event.getX()相对于当前控件的位置   event.getRawX()获取幕的x位置
                // Log.e(TAG, "moveX -> " + moveX +"");
                // 计算分数
                int currentGrade = (int) (moveX/mStarFocusBitmap.getWidth()+1);

                // 范围问题
                if(currentGrade<0){
                    currentGrade = 0;
                }
                if(currentGrade>mGradeNumber){
                    currentGrade = mGradeNumber;
                }
                // 分数相同的情况下不要绘制了 , 尽量减少onDraw()的调用
                if(currentGrade == mCurrentGrade){
                    return true;
                }

                // 再去刷新显示
                mCurrentGrade = currentGrade;
                invalidate();// onDraw()  尽量减少onDraw()的调用，目前是不断调用，怎么减少？
                break;
        }
        return true;// onTouch 后面看源码（2天,3个小时） false 不消费 第一次 DOWN false DOWN以后的事件是进不来的
    }
}
```





