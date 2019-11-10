package com.east.customview.custom_changecolor

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.east.customview.R
import com.east.customview.custom_changecolor.widget.ColorTrackTextView
import kotlinx.android.synthetic.main.activity_view_pager.*
import kotlinx.android.synthetic.main.activity_view_pager.view.*

class ViewPagerActivity : AppCompatActivity() {

    val TAG = ViewPagerActivity::class.java.simpleName


    private val items = arrayOf("直播", "推荐", "视频", "图片", "段子", "精华")
    lateinit var list: MutableList<ColorTrackTextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        list = ArrayList<ColorTrackTextView>()

        for(i in items.indices){
            var params = LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            var colorTrackTextView = ColorTrackTextView(this)
            colorTrackTextView.layoutParams = params
            colorTrackTextView.textSize  = 20f
            colorTrackTextView.setChangeColor(Color.RED)
            colorTrackTextView.text = items[i]
            indicatorContainer.addView(colorTrackTextView)
            list.add(colorTrackTextView)
            colorTrackTextView.setOnClickListener {
                view_pager.currentItem = i
            }
        }

//        view_pager.offscreenPageLimit = 5

        view_pager.adapter =  object:FragmentPagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
            override fun getItem(position: Int): Fragment {
                return ItemFragment.newInstance(items[position])
            }

            override fun getCount(): Int {
                return items.size
            }
        }

        view_pager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d(TAG,"position:${position}--positionOffset:${positionOffset}---positionOffsetPixels:${positionOffsetPixels}")
                //右边到左边
                list[position].setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
                list[position].setProgress(1-positionOffset)
                if(position+1 >= list.size)
                    return

                //从左到右
                list[position+1].setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
                list[position+1].setProgress(positionOffset)

            }

            override fun onPageSelected(position: Int) {
            }

        })

    }
}
