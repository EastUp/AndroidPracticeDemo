package com.east.architecture_components.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.east.architecture_components.R
import com.east.architecture_components.viewmodel.fragment.Test1Fragment
import com.east.architecture_components.viewmodel.fragment.Test2Fragment
import kotlinx.android.synthetic.main.activity_view_model.*

class ViewModelActivity : AppCompatActivity() {

    val TAG = ViewModelActivity::class.java.simpleName
    var test1Fragment : Test1Fragment = Test1Fragment()
    var test2Fragment : Test2Fragment = Test2Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)

        val myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        myViewModel.myLiveData.observe(this, Observer {
            tv_username.text = it
        })

        val shareViewModel = ViewModelProviders.of(this).get(ShareViewModel::class.java)
        shareViewModel.data.observe(this, Observer {
            Log.d(TAG,it)
        })

        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fl,test1Fragment)
        beginTransaction.commitAllowingStateLoss()


        btn_switchover.setOnClickListener {
            val beginTransaction = supportFragmentManager.beginTransaction()
            supportFragmentManager.fragments.forEach {
                if(it == test1Fragment){
                    beginTransaction.replace(R.id.fl,test2Fragment)
                    beginTransaction.commitAllowingStateLoss()
                }else{
                    beginTransaction.replace(R.id.fl,test1Fragment)
                    beginTransaction.commitAllowingStateLoss()
                }
            }

        }
    }
}
