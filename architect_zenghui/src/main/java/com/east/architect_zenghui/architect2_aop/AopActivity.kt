package com.east.architect_zenghui.architect2_aop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect2_aop.aop_checknet.CheckNet
import com.east.architect_zenghui.architect2_aop.aop_singleclick.CheckFastClick
import kotlinx.android.synthetic.main.activity_aop.*

class AopActivity : AppCompatActivity() {

    private var mCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop)
    }


    @CheckNet
    fun onJump(v: View) {
        startActivity(Intent(this, AopActivity::class.java))
    }


    @CheckFastClick(5000)
    fun onSingleClick(v: View) {
        mCount++
        clickCount.text = "点击次数:$mCount"
    }
}
