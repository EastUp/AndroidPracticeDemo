package com.east.customview.parallax2_yahu_splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_parallax_yahu.*

class ParallaxYahuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallax_yahu)
        parallax_vp.setLayoutIds(supportFragmentManager,R.layout.fragment_page_first,R.layout.fragment_page_second,R.layout.fragment_page_third)
        Handler().postDelayed(
            {
                loadView.disappear()
            }
            , 1000
        )
    }
}
