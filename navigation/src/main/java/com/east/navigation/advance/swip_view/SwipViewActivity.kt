package com.east.navigation.advance.swip_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.east.navigation.R
import kotlinx.android.synthetic.main.activity_swip_view.*

class SwipViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swip_view)


        pager.adapter = DemoCollectionPagerAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(pager)
    }
}
