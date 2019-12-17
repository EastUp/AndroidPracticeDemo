package com.east.customview.custom_lockpattern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  九宫格解锁
 *  @author: East
 *  @date: 2019-12-14 10:54
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LockPatternActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_pattern)
    }
}
