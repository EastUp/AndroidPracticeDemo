package com.east.navigation.utils

import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import com.google.android.material.bottomnavigation.LabelVisibilityMode


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-10-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
object BottomNavigationViewUtil {

    /**
     * 解决BottomNavigationView大于3个item时的位移
     * 针对com.android.support:design:28.0.0以上或andoidx中com.google.android.material:material:1.0.0-beta01以上
     * @param view
     */
    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            menuView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED // 1
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShifting(false)
            }
        } catch (e: Exception) {
            Log.e("NavigationView", "Unable to get shift mode field", e)
        }

    }
}