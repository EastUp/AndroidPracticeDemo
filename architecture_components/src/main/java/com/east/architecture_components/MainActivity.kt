package com.east.architecture_components

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architecture_components.lifecycle.LifecycleActivity
import com.east.architecture_components.livedata.LiveDataActivity
import com.east.architecture_components.paginglibrary.PagingLibraryActivity
import com.east.architecture_components.room.RoomActivity
import com.east.architecture_components.viewmodel.ViewModelActivity
import com.east.architecture_components.workmanager.WorkManagerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun onClick(v: View){
        when(v){
            Lifycycle -> {
                startActivity(Intent(this, LifecycleActivity::class.java))
            }
            LiveData -> {
                startActivity(Intent(this, LiveDataActivity::class.java))
            }
            ViewModel -> {
                startActivity(Intent(this, ViewModelActivity::class.java))
            }
            Room -> {
                startActivity(Intent(this, RoomActivity::class.java))
            }
            PagingLibrary ->{
                startActivity(Intent(this, PagingLibraryActivity::class.java))
            }
            WorkManager ->{
                startActivity(Intent(this, WorkManagerActivity::class.java))
            }

        }
    }
}
