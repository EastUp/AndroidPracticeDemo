package com.east.customview.custom_redpacket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.east.customview.R
import kotlinx.android.synthetic.main.activity_red_package.*

class RedPackageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_red_package)

        red_pack_view.setTotalProgress(3)

        red_pack_view.setOnClickListener {
            red_pack_view.startAnimation(1,3)
        }
    }
}
