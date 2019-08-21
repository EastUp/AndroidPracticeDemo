package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.east.databinding.databinding.ActivityTest6Binding

/**
 *  使用类方法
 */
class Test6Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest6Binding = DataBindingUtil.setContentView<ActivityTest6Binding>(this, R.layout.activity_test6)

    }





}
