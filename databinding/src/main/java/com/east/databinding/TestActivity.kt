package com.east.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.east.databinding.databinding.ActivityTestBinding
import com.east.databinding.entity.Goods
import kotlin.random.Random

class TestActivity : AppCompatActivity() {

    lateinit var goods: Goods
    lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTestBinding = DataBindingUtil.setContentView<ActivityTestBinding>(this, R.layout.activity_test)

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        goods = Goods("哈哈", "儿童饮料", 12.5f)
        activityTestBinding.goods = goods
        activityTestBinding.goodsHandler = GoodsHandler()

        goods.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.details -> {
                        toast.setText("Details改变")
                        Log.i("Goods","Details改变")
                    }
                    BR.name -> {
                        toast.setText("name改变")
                        Log.i("Goods","name改变")
                    }
                    BR._all -> {
                        toast.setText("_all改变")
                        Log.i("Goods","_all改变")
                    }

                }
                toast.show()
            }
        })
    }


    inner class GoodsHandler {
        fun changeGoodsName() {
            goods.name = "code${Random.nextInt(100)}"
            goods.price = Random.nextInt(100).toFloat()
            goods.notifyChange()
        }

        fun changeGoodsDetail() {
            goods.details = "details${Random.nextInt(100)}"
            goods.price = Random.nextInt(100).toFloat()
            goods.notifyPropertyChanged(BR.details)
        }
    }
}
