package com.east.navigation.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.east.navigation.R

class BasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)


        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.blankFragment2)
            .setArguments(bundleOf("name" to "zhangsan"))
            .createPendingIntent()

        //Activity中找到NavController
//        findNavController(R.id.nav_host_fragment).navigate(R.id.action_blankFragment3_to_blankFragment)
//        AppBarConfiguration(findNavController(R.id.nav_host_fragment).graph,)

    }
}
