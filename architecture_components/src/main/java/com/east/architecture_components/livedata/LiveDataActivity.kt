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



        //switchhMap方法中的参数Function 将Source的数据类型转换成LiveData并监听
        //switchMap和map方法的不同在于  switchmap 还要观察Function转换后返回的LiveData数据变化
        val switchMap = Transformations.switchMap(myViewModel.myLiveData, Function<String, LiveData<User>> {
            //转换成一个LiveData并且还要监听这个LiveData的数据变化


            switchFuntionLiveData.value = User(it, "passw")

            //观察下面livedata的数据变化
            switchFuntionLiveData
        })

        switchMap.observe(this, Observer {
            tv_username.text = it.password
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
