@[TOC](View的绘制流程) 

## 一、mTextView.getMeasuredHeight() 为什么拿不到

### setContent源码:

PhoneWindow -> ViewRootImpl -> DecorView -> ViewGroup(加载系统布局:有无actionBar,是否半透明等等) -> FrameLayout(R.id.content) -> (自己的View)    

之所有能够拿到控件的宽高是因为调用了  onMeasure() 一定要调用才能拿到宽高，指定的 mMeasuredHeight  
setContentView  只是创建DecorView 把我们的布局加载到了DecorView 

### Activity的启动流程


performLaunchActivity -> Activity.onCreate() ->  
handleResumeActivity()  
 -> performResumeActivity() -> Activity的onResume()方法  
 -> wm.addView(decor, l);  才开始把我们的 DecorView 加载到 WindowManager, 
 -> View的绘制流程在这个时候才开始 measure() layout() draw()    
 

###  wm.addView(decor, l); 
wm.addView(decor, l); ->  WindowManangerImpl.addView()  
-> root.setView(view, wparams, panelParentView);   
-> requestLayout()   
-> scheduleTraversals()    
-> doTraversal()   
-> performTraversals() (网上的文章都是从这里开始, 书本来的, 才开始)

### 重点performTraversals开始
第一个调用的方法：performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);  
-> mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);    
-> onMeasure(widthMeasureSpec, heightMeasureSpec); 测量开始   
-> LinearLayout.onMeasure(widthMeasureSpec, heightMeasureSpec)  第三又从这里开始    
-> measureVertical(int widthMeasureSpec, int heightMeasureSpec)    
-> measureChildBeforeLayout(child, i, widthMeasureSpec, 0,heightMeasureSpec, usedHeight);  
-> measureChildWithMargins   
childWidthMeasureSpec , childHeightMeasureSpec 测量模式 是通过getChildMeasureSpec计算

当LinearLayout测量模式是wrap_content时，即使里面的子View测量模式是match_parent，也会被修改成为wrap_content



``` java
public static int getChildMeasureSpec(int spec, int padding, int childDimension) {  
        // 父布局  的 宽高的模式
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);

        int size = Math.max(0, specSize - padding);
        int resultSize = 0;
        int resultMode = 0;
        switch (specMode) {  
		// Parent has imposed an exact size on us
      	case MeasureSpec.EXACTLY: // 父布局是一个  指定的值
            if (childDimension >= 0) {
                resultSize = childDimension;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size. So be it.
                resultSize = size;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            }
            break;

        // Parent has imposed a maximum size on us
        case MeasureSpec.AT_MOST:
            if (childDimension >= 0) {
                // Child wants a specific size... so be it
                resultSize = childDimension;
                resultMode = MeasureSpec.EXACTLY;
            } else if (childDimension == LayoutParams.MATCH_PARENT) {
                // Child wants to be our size, but our size is not fixed.
                // Constrain child to not be bigger than us.
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                // Child wants to determine its own size. It can't be
                // bigger than us.
                resultSize = size;
                resultMode = MeasureSpec.AT_MOST;
            }
            break;			
```
	  
-> child.measure(childWidthMeasureSpec, childHeightMeasureSpec);  第二  第四    

这个时候我们都会调用setMeasuredDimension() 这个时候我们布局才真正指定宽度和高度，   
mMeasuredWidth和mMeasuredHeight才开始有值   

接着执行ViewGroup的onMeasure() 方法 ，这个时候要指定自己的宽高了     
childHeight = child.getMeasuredHeight(); 高度的算法如果是垂直方向是不断的叠加子View的高度,    
RelativeLayout的高度算法是指定，子孩子里面最高的   


