package com.east.navigation.advance.swip_view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-09-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
class DemoCollectionPagerAdapter(fm:FragmentManager) : FragmentStatePagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return DemoFragment.newInstance((position+1).toString())
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "OBJECT${position + 1}"
    }


}