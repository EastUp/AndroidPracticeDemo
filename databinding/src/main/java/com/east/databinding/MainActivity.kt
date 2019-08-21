package com.east.databinding

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.east.databinding.bean.User
import com.east.databinding.databinding.UserBinding
import com.east.databinding.fragment.CustomerFragment

class MainActivity : AppCompatActivity(),CustomerFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userBinding = DataBindingUtil.setContentView<UserBinding>(this, R.layout.activity_main)
        userBinding.beanUserInfo = User("名称","密码",false)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl,CustomerFragment.newInstance("这是传递的用户名","这是密码"))
            .commitAllowingStateLoss()
    }
}
