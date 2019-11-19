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
-> root.setView(view, wparams, panelParentView);  -> requestLayout() -> scheduleTraversals()  
-> doTraversal() -> performTraversals() (网上的文章都是从这里开始, 书本来的, 才开始)


