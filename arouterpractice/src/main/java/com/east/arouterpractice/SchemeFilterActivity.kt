package com.east.arouterpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter

class SchemeFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheme_filter)


        var uri = intent.data
        ARouter.getInstance().build(uri).navigation(this,object: NavCallback() {
            override fun onArrival(postcard: Postcard?) {
                finish()
            }
        })
    }
}
