package com.east.architecture_components.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.east.architecture_components.R

/**
 * Android架构组件 Lifecycle
 */
class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        lifecycle.addObserver(MyObserver())
    }
}
