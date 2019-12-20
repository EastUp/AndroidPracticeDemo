package com.east.architecture_components.livedata

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.east.architecture_components.R
import com.east.architecture_components.livedata.entity.User
import com.east.architecture_components.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_live_data.*
import kotlin.random.Random

class LiveDataActivity : AppCompatActivity() {

    lateinit var myViewModel: MyViewModel
    var switchFuntionLiveData = MutableLiveData<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        myViewModel.myLiveData.observe(this, Observer<String> {
            tv_username.text = it
        })

        myViewModel.customerLiveData.observe(this, Observer {
            tv_username.text = it
        })


        //livedata进行类型转换,每当source的livedata变化,map后生成的livedata跟着变化
        val transformationsMap = Transformations.map(myViewModel.myLiveData) {
            User(it, "userpassword${Random.nextInt(100)}")
        }

        transformationsMap.observe(this, Observer {
            tv_username.text = it.password
        })


        /**
         * switchMap和map方法的不同在于
         *      map是如果传入的LiveData多次放生改变,UI就会订阅多个LiveData.
         *      swichmap在每次传入的Livedata的值发送改变的时候,使用MediatorLiveData来在添加新源时删除原始源
         */

        //switchMap只观察Function转换后返回的LiveData数据变化,参数传递进去的LiveData变化只会触发Funtion方法
        val switchMap = Transformations.switchMap(myViewModel.myLiveData) {
            //转换成一个LiveData并且还要监听这个LiveData的数据变化


            switchFuntionLiveData.value = User(it, "passw")

            //观察下面livedata的数据变化
            switchFuntionLiveData
        }

        switchMap.observe(this, Observer {
            tv_username.text = it.username+it.password
        })







    }


    fun onClick(v: View) {


        when (v) {
            btn_mutable_livedata       -> myViewModel.myLiveData.value = "LiveData${Random.nextInt(100)}"
            btn_custome_livedata       -> myViewModel.customerLiveData.value = "customerData${Random.nextInt(100)}"

            //单例下的livedata实现了数据共享
            btn_sigle_custome_livedata -> CustomLiveData.instace.value = "sigleCustomer${Random.nextInt(100)}"

            //测试Transmations.switchMap方法
            btn_test_switchmap -> {
                val value = switchFuntionLiveData.value
                value?.password = "switchMap${Random.nextInt(100)}"
                switchFuntionLiveData.value = value
            }
        }
    }
}
