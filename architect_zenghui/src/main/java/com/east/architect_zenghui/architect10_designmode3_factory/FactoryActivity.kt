package com.east.architect_zenghui.architect10_designmode3_factory

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.R
import kotlinx.android.synthetic.main.activity_factory.*
import com.east.architect_zenghui.architect10_designmode3_factory.simple1_normal_preference.TestActivity as activity1
import com.east.architect_zenghui.architect10_designmode3_factory.simple2_preferenceutil.TestActivity as activity2
import com.east.architect_zenghui.architect10_designmode3_factory.simple3_simple_factory_mode.TestActivity as activity3
import com.east.architect_zenghui.architect10_designmode3_factory.simple4_factory_method_mode.TestActivity as activity4
import com.east.architect_zenghui.architect10_designmode3_factory.simple5_abstract_factory_mode.TestActivity as activity5
import com.east.architect_zenghui.architect10_designmode3_factory.simple6_abstract_factory_mode_singleton.TestActivity as activity6

class FactoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factory)
    }

    fun onClick(v: View){
        when(v){
            normal_preference -> startActivity(Intent(this,activity1::class.java))
            preferenceutil -> startActivity(Intent(this,activity2::class.java))
            simple_factory_mode -> startActivity(Intent(this,activity3::class.java))
            factory_method_mode -> startActivity(Intent(this,activity4::class.java))
            abstract_factory_mode -> startActivity(Intent(this,activity5::class.java))
            abstract_factory_mode_singleton -> startActivity(Intent(this,activity6::class.java))
        }
    }
}
