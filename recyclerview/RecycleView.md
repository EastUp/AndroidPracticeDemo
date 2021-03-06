@[TOC](RecycleView进阶) 

## 一、功能介绍
### **依赖**  
   ``` gradle
    "androidx.recyclerview:recyclerview:1.0.0"
   ```
### **RecyclerView xml 属性设置**  
   xml 布局中有些属性我们要熟悉，像高度设置，滚动条设置，底部的阴影效果等。 
 
   ``` xml
	   <!--重点部分，使用RecyclerView，高度设置，-->
	    <!--如果是垂直布局，使用match_parent-->
	    <!--如果是水平布局，使用wrap_content -->
	    <android.support.v7.widget.RecyclerView
	        android:id="@+id/rv_meizhi"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        <!--底部虚化效果的方向设置-->
	        android:requiresFadingEdge="vertical"
	        <!--底部虚化效果的高度设置-->
	        android:fadingEdgeLength="200dp" 
	        <!--底部阴影效果设置，never 为去掉-->
	        android:overScrollMode="always"
	        <!--滚动条设置，none 为去掉-->
	        android:scrollbars="vertical"
	        android:scrollbarThumbVertical="@drawable/my_bar"/>
   ```  
   还有一个点，对于滚动条是可以改变样式的，通过设置自定义的 drawable ,注意这个 自定义的 drawble 有点特别，不是普通的shape，而是 layer-list 开头的，主要是为了将滚动条到屏幕边设置一个像素的距离。如果不设置距离，直接使用普通的shape就行

	``` xml
	    <?xml version="1.0" encoding="utf-8"?>
	    <layer-list xmlns:android="http://schemas.android.com/apk/res/android">
	    <item android:right="1dp">
	    <shape>
	    <solid android:color="#999999"/>
	    <corners android:radius="1dp"/>
	    <stroke android:color="#111111"    android:width="1px"/>
	    <size android:width="2dp"/>
	    </shape>
	    </item>
	    </layer-list>
	```
### **方法**  
	`setHasFixedSize` 作用 : **如果我们能够提前知道 RecyclerView 的大小不受 Adapter 内容的影响时，RecyclerView 能够通过设置 setHasFixedSize 来进行优化。**

	``` java
	    mRecyclerView.setHasFixedSize(true);
	```
	举个例子：当我使用 RecyclerView时，adapter 的item的布局大小是一定的，与数量无关,这时每一屏上的数量是一定的，即使没有铺满屏幕也不要紧，照样可以使用 setHasFixedSize。练习中假设每张图片的大小是 50 * 50的，那么就满足条件。假设 ImageView 中的使用了 wrap_content,这时所有的图片大小不一定相同，就不符合条件了，所以不能使用 setHasFixedSize。
	
	`GridLayoutManager 的网格合并`
	
	``` java
		// SpanSizeLookup
        // A helper class to provide the number of spans each item occupies.
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = mRecyclerView.getAdapter().getItemViewType(position);
                // 实际中最好根据 item 的类型来判断
                if (position == 0 || position == 1) {
                    // 相当于 LinearLayout,position为0和1的占GridlayoutManage的2个位置
                    return 2;
                }

                return 1;
            }
        });
        
	```

## 二、分割线
分割线设置有两种方法：
  
### 在xml布局中设置   
   在 drawable 中定义一个 shape

	``` xml
	<?xml version="1.0" encoding="utf-8"?>
	<shape xmlns:android="http://schemas.android.com/apk/res/android"
	    android:shape="rectangle" >
	
	    // 渐变颜色
	    <gradient
	        android:centerColor="#ff00ff00"
	        android:endColor="#ff0000ff"
	        android:startColor="#ffff0000"
	        android:type="linear" />
	        
	        // 分割线宽度
	    <size android:height="4dp"/>
	
	</shape>

	```
	加到应用主题中,让activity或Application中指定theme

	``` xml
	<!-- Application theme. -->
    <style name="AppTheme1" parent="Theme.AppCompat.NoActionBar">
        <item name="colorPrimary">@color/theme_primary</item>
        <item name="colorPrimaryDark">@color/theme_primary_dark</item>
        <item name="colorAccent">@color/md_red_400</item>
        // 分割线主题颜色设置
        <item name="android:listDivider">@drawable/divider_shape</item>
        <!-- 加入toolbar溢出【弹出】菜单的风格 -->
        <item name="actionOverflowMenuStyle">@style/OverflowMenuStyle</item>

   </style>

	```
### 使用 ItemDecoration  
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;第一种方法在 LinearLayoutManager 中使用是正常的，但是在 StaggeredGridLayoutManager 和 GridLayoutManager 中使用是有问题的。所以建议都使用第二种方式：自定义实现`RecyclerView.ItemDecoration`

	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;下面的是 RecyclerView.ItemDecoration 的三个方法，实现时是要完成这几个方法即可，onDraw 和 onDrawOver 完成其中一个方法即可。  
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;`getItemOffests`可以通过outRect.set(l,t,r,b)设置指定itemview的paddingLeft，paddingTop， paddingRight， paddingBottom  
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;`onDraw`可以通过一系列c.drawXXX()方法在绘制itemView之前绘制我们需要的内容。  
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;`onDrawOver`与`onDraw`类似，只不过是在绘制itemView之后绘制，具体表现形式，就是绘制的内容在itemview上层。

	``` java
	
	    1. 绘制网格线，绘制时机是在每个 item 之前
	    public void onDraw(Canvas c, RecyclerView parent, State state) {
	        onDraw(c, parent);
	    }
	    
	    2. 绘制网格线，绘制时机是在每个 item 之后
	    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
	        onDrawOver(c, parent);
	    }
	    
	    3. 设置网格线的位置，根据 view 的位置来确定分割线画在哪，同时设置网格线的矩形区域，及设置宽高和位置
	    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
	  			//代表view下方padding 50
	  			outRect.set(0, 0, 0, 50);
	        
	        getItemOffsets(outRect, ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition(),
	                parent);
	    }

	```
## itemAnimator 动画

### 依赖第三方动画库
``` gradle
	implementation 'jp.wasabeef:recyclerview-animators:3.0.0'
```
### 调用
``` kotlin
	rv.itemAnimator = SlideInLeftAnimator()
```
### 所有的动画库
``` kotlin
    FadeIn(FadeInAnimator()), 		//渐变
    FadeInDown(FadeInDownAnimator()), //渐变并从顶部下滑下来
    FadeInUp(FadeInUpAnimator()),		//渐变并从底部上滑上去
    FadeInLeft(FadeInLeftAnimator()),//渐变并从左边滑进去
    FadeInRight(FadeInRightAnimator()),//渐变并从右边滑进去
    Landing(LandingAnimator()),		//从四周缩小
    ScaleIn(ScaleInAnimator()),		//以中心点为中心从最小变大
    ScaleInTop(ScaleInTopAnimator()), //以顶部中间为中心从最小变大
    ScaleInBottom(ScaleInBottomAnimator()), //以底部中间为中心从最小变大
    ScaleInLeft(ScaleInLeftAnimator()),  //以左下角为中心从最小变大
    ScaleInRight(ScaleInRightAnimator()),//以右下角为中心从最小变大
    FlipInTopX(FlipInTopXAnimator()),    //逆时针翻转，到顶部
    FlipInBottomX(FlipInBottomXAnimator()), //顺时针翻转到底部
    FlipInLeftY(FlipInLeftYAnimator()),	//翻转到Left
    FlipInRightY(FlipInRightYAnimator()), //翻转到Right
    SlideInLeft(SlideInLeftAnimator()), //从左侧划入
    SlideInRight(SlideInRightAnimator()), //从右侧划入
    SlideInDown(SlideInDownAnimator()), //从顶部划入
    SlideInUp(SlideInUpAnimator()),     //从底部划入
    OvershootInRight(OvershootInRightAnimator(1.0f)), //从右侧划入，并有1f的跳动
    OvershootInLeft(OvershootInLeftAnimator(1.0f))   //从左侧划入，并有1f的跳动
```
## ItemTouchHelper(互换位置、滑动删除)
**新建接口用于上下拖动，左右滑动**

``` kotlin
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 用于上下拖动时交换位置。以及左右滑动删除
 *  @author: East
 *  @date: 2019-11-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface OnItemCallbackListener {

    /**
     * @param fromPosition 起始位置
     * @param toPosition 移动位置
     */
    fun onMove(fromPosition:Int, toPosition:Int)


    //左滑
    fun onSwipeLeft(position:Int)

    //右滑
    fun onSwipeRight(position: Int)
}
```
**新建ItemTouchHelper实现类，上面创建的接口作为构造函数的参数**

``` kotlin 
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 作为ItemTouchHelper的构造参数传入,再调用ItemTouchHelper.attachToRecyclerView方法,使得他们可以拖拽
 *  @author: East
 *  @date: 2019-11-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
class DragItemTouchHelpCallback(private val onItemCallbackListener: OnItemCallbackListener): ItemTouchHelper.Callback() {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if(direction == ItemTouchHelper.END){
            //item滑动方向为向右
            onItemCallbackListener.onSwipeRight(viewHolder.adapterPosition)
        }else if(direction == ItemTouchHelper.START){
            //item滑动方向为向左
            onItemCallbackListener.onSwipeLeft(viewHolder.adapterPosition)
        }
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        //设置为可上下拖拽
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        //设置侧滑方向为左右
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags,swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        onItemCallbackListener.onMove(viewHolder.adapterPosition,target.adapterPosition)
        return true
    }

    /**
     *  是否长按拖拽
     */
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }
}
```
**Adapter实现创建的接口，在里面进行数据的操作**

``` kotlin
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 互换位置,滑动删除的Adapter
 *  @author: East
 *  @date: 2019-11-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
class SwapAdapter :RecyclerView.Adapter<SwapAdapter.ViewHolder>,OnItemCallbackListener{
    private var picList: MutableList<String> ?= null
    lateinit var onTouchListener: OnTouchListener
    constructor()

    constructor(picList: MutableList<String>,onTouchListener: OnTouchListener){
        this.picList = picList
        this.onTouchListener = onTouchListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_swap_rv,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv!!.text = picList!![position]

        /**
         * 在onBindViewHolder()方法中设置触摸监听，然后调用startDrag()方法进行移动。
         */
        holder.itemView.setOnTouchListener { v, event ->
            onTouchListener.onTouchListener(holder)
        }
    }

    override fun getItemCount(): Int {
        if (picList.isNullOrEmpty()) {
            return 0
        }
        return picList!!.size
    }

    class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        var tv: TextView? = null

        init {
            initView()
        }

        private fun initView() {
            tv = mView.findViewById(R.id.tv)
        }
    }

    override fun onMove(fromPosition: Int, toPosition: Int) {
        /**
         * 在这里进行给原数组数据的移动
         * 第一个参数为数据源
         */
        Collections.swap(picList,fromPosition,toPosition)

        /**
         *  通知数据移动
         */
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onSwipeLeft(position: Int) {
        /**
         *  左滑删除
         */
        picList!!.removeAt(position)
        notifyItemRemoved(position)
        onTouchListener.onSwipeLeft()
    }

    override fun onSwipeRight(position: Int) {
        //右滑删除
        picList!!.removeAt(position)
        notifyItemRemoved(position)
        onTouchListener.onSwipeRight()
    }


    interface OnTouchListener{
        fun onTouchListener(holder: ViewHolder):Boolean
        fun onSwipeLeft()
        fun onSwipeRight()
    }
}
```

**Activity中实现**

``` kotlin
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  不用长按拖动,左滑,右滑
 *  @author: East
 *  @date: 2019-11-06 17:45
 * |---------------------------------------------------------------------------------------------------------------|
 */
class SwapActivity : AppCompatActivity() {

    lateinit var itemTouchHelper : ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap)

        //添加分割线
        rv.addItemDecoration(RecyclerViewItemDecoration.Builder(this)
            .color("#ffffff")
            .thickness(DisplayUtil.dip2px(this,2f))
            .create())
        var list = listOf<String>("1","2","3","4","5","6","7","8","9","10").toMutableList()
        val adapter = SwapAdapter(list,object : SwapAdapter.OnTouchListener {
            override fun onTouchListener(holder: SwapAdapter.ViewHolder): Boolean {
                /**
                 * 设置触摸监听，然后调用startDrag()方法进行移动。
                 */
//                itemTouchHelper.startDrag(holder)
                return true
            }

            override fun onSwipeLeft() {
                Toast.makeText(this@SwapActivity,"左滑",Toast.LENGTH_SHORT).show()
            }

            override fun onSwipeRight() {
                Toast.makeText(this@SwapActivity,"右滑",Toast.LENGTH_SHORT).show()
            }
        })
        rv.adapter = adapter


        //先实例化Callback
        var dragItemTouchHelpCallback =  DragItemTouchHelpCallback(adapter)
        //用Callback构造ItemtouchHelper
        itemTouchHelper = ItemTouchHelper(dragItemTouchHelpCallback)
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        itemTouchHelper.attachToRecyclerView(rv)
//        itemTouchHelper.startDrag()

    }
}
```
	
