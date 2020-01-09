package com.east.customview.parallax1_kugou_splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_parallax_kugou.*

class ParallaxKugouActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallax_kugou)
        parallax_vp.setLayoutIds(supportFragmentManager,R.layout.fragment_page_first,R.layout.fragment_page_second,R.layout.fragment_page_third)
    }
}
