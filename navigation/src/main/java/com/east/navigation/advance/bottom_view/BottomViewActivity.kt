package com.east.navigation.advance.bottom_view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.east.navigation.R
import com.east.navigation.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_bottom_view.*

class BottomViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_view)


        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置沉浸式状态栏
        StatusBarUtil.setTranslucentStatus(this)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        window.statusBarColor = Color.TRANSPARENT

        val navController = findNavController(R.id.nav_host_bottom)

        bottomview.setupWithNavController(navController)

        //actionbar跟navigation的联动在fragment里面

        //先让Actionbar跟home模块中的nav_graph联动
//        setupActionBarWithNavController(findNavController(R.id.nav_host_home),appBarConfiguration)

        //监听目标转换
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            when(destination!!.id){
//                R.id.homeFragment -> setupActionBarWithNavController(findNavController(R.id.nav_host_home),appBarConfiguration)
//                R.id.leaderBoardFragment -> setupActionBarWithNavController(findNavController(R.id.nav_host_home),appBarConfiguration)
//                R.id.registerFragment -> setupActionBarWithNavController(findNavController(R.id.nav_host_home),appBarConfiguration)
//            }
//        }



    }


    override fun onSupportNavigateUp(): Boolean {
        var navController = findNavController(R.id.nav_host_bottom)
        when(navController.currentDestination!!.id){
            R.id.homeFragment -> navController = findNavController(R.id.nav_host_home)
            R.id.leaderBoardFragment -> navController = findNavController(R.id.nav_host_leaderboard)
            R.id.registerFragment -> navController = findNavController(R.id.nav_host_register)
        }
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
