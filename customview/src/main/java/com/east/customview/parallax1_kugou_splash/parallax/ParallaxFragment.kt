package com.east.customview.parallax1_kugou_splash.parallax

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.annotation.NonNull
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 视差的fragment在这拦截view的创建和解析
 *
 *  @author: East
 *  @date: 2020-01-09
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ParallaxFragment : Fragment(), LayoutInflater.Factory2 {
    companion object {
        const val LAYOUT_ID_KEY = "LAYOUT_ID_KEY"
    }

    var mCompatViewInflater: CompatViewInflater? = null

    //存放所有需要位移的View
    var mParallaxViews = ArrayList<View>()

    private val mParallaxAttrs = intArrayOf(
        R.attr.translationXIn,
        R.attr.translationXOut, R.attr.translationYIn, R.attr.translationYOut
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 获取布局的id
        val layoutId = arguments!!.getInt(LAYOUT_ID_KEY)
        // 2.2.2 把所有需要移动的属性解析出来，内涵端子插件式换肤
        // View创建的时候 我们去解析属性  这里传 inflater 有没有问题？(报错) 单例设计模式 代表着所有的View的创建都会是该 Fragment 去创建的
        var inflaterClone = inflater.cloneInContext(context)//克隆一个出来
        LayoutInflaterCompat.setFactory2(inflaterClone, this)
        return inflaterClone.inflate(layoutId, container, false)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        // View都会来这里,创建View
        // 拦截到View的创建  获取View之后要去解析
        // 1. 创建View
        // If the Factory didn't handle it, let our createView() method try
        val view: View? = createView(parent, name, context, attrs)
        // 2.1 一个activity的布局肯定对应多个这样的 SkinView
        view?.let { analysisAttrs(it, context, attrs) }
        return view
    }



    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return onCreateView(null,name,context,attrs)
    }

    /**
     *  获取属性
     */
    private fun analysisAttrs(view: View, context: Context, attrs: AttributeSet) {
        var typedArray = context.obtainStyledAttributes(attrs,mParallaxAttrs)
        if(typedArray!=null && typedArray.indexCount != 0){
            var tag = ParallaxTag()
            for(i in 0 until typedArray.indexCount){
                var attr = typedArray.getIndex(i)
                when(attr){
                    0 -> tag.translationXIn = typedArray.getFloat(attr,0f)
                    1 -> tag.translationXOut = typedArray.getFloat(attr,0f)
                    2 -> tag.translationYIn = typedArray.getFloat(attr,0f)
                    3 -> tag.translationYOut = typedArray.getFloat(attr,0f)
                }
            }
            // 自定义属性怎么存? 还要一一绑定  在View上面设置一个tag
            view.setTag(R.id.parallax_tag,tag)
            mParallaxViews.add(view)
        }
        typedArray.recycle()
    }


    private fun createView(
        parent: View?,
        name: String?, @NonNull context: Context?,
        @NonNull attrs: AttributeSet?
    ): View? {
        val isPre21 = Build.VERSION.SDK_INT < 21
        if (mCompatViewInflater == null) {
            mCompatViewInflater = CompatViewInflater()
        }
        // We only want the View to inherit it's context if we're running pre-v21
        val inheritContext = (isPre21 && shouldInheritContext(parent as ViewParent?))
        return mCompatViewInflater!!.createView(
            parent, name, context!!, attrs!!, inheritContext,
            isPre21,  /* Only read android:theme pre-L (L+ handles this anyway) */
            true /* Read read app:theme as a fallback at all times for legacy reasons */
        )
    }

    private fun shouldInheritContext(parent: ViewParent?): Boolean {
        var parent: ViewParent? = parent
            ?: // The initial parent is null so just return false
            return false
        while (true) {
            if (parent == null) { // Bingo. We've hit a view which has a null parent before being terminated from
                                // the loop. This is (most probably) because it's the root view in an inflation
                                // call, therefore we should inherit. This works as the inflated layout is only
                                // added to the hierarchy at the end of the inflate() call.
                return true
            } else if (parent !is View
                || ViewCompat.isAttachedToWindow(parent as View)
            ) { // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false
            }
            parent = parent.getParent()
        }
    }

    fun getParallaxViews(): List<View?>{
        return mParallaxViews
    }

}