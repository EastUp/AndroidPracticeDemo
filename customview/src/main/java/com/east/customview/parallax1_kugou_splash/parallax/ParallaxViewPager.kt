package com.east.customview.parallax1_kugou_splash.parallax

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 视差动画的ViewPager
 *  @author: East
 *  @date: 2020-01-09
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ParallaxViewPager @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet ?= null
):ViewPager(context,attrs) {

    var mFragments = ArrayList<ParallaxFragment>()

    /**
     *  设置布局
     */
    fun setLayoutIds(fm: FragmentManager,vararg fragmentLayoutId:Int) {
        //1.实例化fragment
        mFragments.clear()
        for (layoutId in fragmentLayoutId) {
            var parallaxFragment =
                ParallaxFragment()
            var bundle = Bundle()
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY,layoutId)
            parallaxFragment.arguments = bundle
            mFragments.add(parallaxFragment)
        }
        //2.设置Adapter
        adapter = ParallaxAdapter(fm)
        //3.根据滑动来改变fragment中的view位移
        addOnPageChangeListener(object :OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // 滚动  position 当前位置    positionOffset 0-1     positionOffsetPixels 0-屏幕的宽度px
                Log.e(
                    "TAG",
                    "position->$position positionOffset->$positionOffset positionOffsetPixels->$positionOffsetPixels"
                )

                // 获取左out 右 in
                val outFragment: ParallaxFragment = mFragments[position]
                var parallaxViews =
                    outFragment.getParallaxViews()
                for (parallaxView in parallaxViews) {
                    if(parallaxView == null) return
                    val tag = parallaxView.getTag(R.id.parallax_tag) as ParallaxTag
                    // 为什么这样写 ？
                    parallaxView.translationX = -positionOffsetPixels * tag.translationXOut
                    parallaxView.translationY = -positionOffsetPixels * tag.translationYOut
                }

                try {
                    val inFragment: ParallaxFragment = mFragments[position + 1]
                    parallaxViews = inFragment.getParallaxViews()
                    for (parallaxView in parallaxViews) {
                        if(parallaxView == null) return
                        val tag = parallaxView.getTag(R.id.parallax_tag) as ParallaxTag
                        parallaxView.translationX =
                            (measuredWidth - positionOffsetPixels) * tag.translationXIn
                        parallaxView.translationY =
                            (measuredWidth - positionOffsetPixels) * tag.translationYIn
                    }
                } catch (e: Exception) {
                }

            }

            override fun onPageSelected(position: Int) {

            }

        })
    }


    private inner class ParallaxAdapter(fm:FragmentManager) : FragmentStatePagerAdapter(fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        override fun getItem(position: Int): Fragment = mFragments[position]

        override fun getCount(): Int = mFragments.size
    }

}