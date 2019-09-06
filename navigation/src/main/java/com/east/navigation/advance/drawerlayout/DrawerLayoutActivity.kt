package com.east.navigation.advance.drawerlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.east.navigation.R
import kotlinx.android.synthetic.main.activity_drawer_layout.*

class DrawerLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_layout)

        navigation_view.itemIconTintList = null
        navigation_view.setupWithNavController(findNavController(R.id.nav_host_drawer))

    }

    override fun onSupportNavigateUp(): Boolean {
        var navController = findNavController(R.id.nav_host_drawer)
        when(navController.currentDestination!!.id){
            R.id.homeFragment -> navController = findNavController(R.id.nav_host_home)
            R.id.leaderBoardFragment -> navController = findNavController(R.id.nav_host_leaderboard)
            R.id.registerFragment -> navController = findNavController(R.id.nav_host_register)
        }
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
