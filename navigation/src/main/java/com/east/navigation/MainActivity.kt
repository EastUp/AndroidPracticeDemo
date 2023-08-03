package com.east.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.east.navigation.advance.CustomerBackNavigationActivity
import com.east.navigation.advance.bottom_view.BottomViewActivity
import com.east.navigation.advance.drawerlayout.DrawerLayoutActivity
import com.east.navigation.advance.sharedata_viewmodel.TestActivity
import com.east.navigation.advance.swip_view.SwipViewActivity
import com.east.navigation.basic.BasicActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View){
        when(v){
            basic -> startActivity(Intent(this,BasicActivity::class.java))
            customback -> startActivity(Intent(this,CustomerBackNavigationActivity::class.java))
            viewmodel_send_data -> startActivity(Intent(this,TestActivity::class.java))
            viewpager_tablayout -> startActivity(Intent(this, SwipViewActivity::class.java))
            bottomview -> startActivity(Intent(this,BottomViewActivity::class.java))
            drawerlayout -> startActivity(Intent(this,DrawerLayoutActivity::class.java))
        }
    }
}
