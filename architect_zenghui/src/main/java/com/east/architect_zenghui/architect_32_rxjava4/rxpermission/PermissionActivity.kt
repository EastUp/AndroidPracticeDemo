package com.east.architect_zenghui.architect_32_rxjava4.rxpermission

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.east.architect_zenghui.R
import com.tbruyelle.rxpermissions2.RxPermissions
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  RxPermission的使用
 *  @author: jamin
 *  @date: 2020/3/15 2:10 PM
 * |---------------------------------------------------------------------------------------------------------------|
 */
class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.CAMERA)
            .subscribe {
                if(it){
                    //成功
                }else{
                    //失败
                }
            }
    }
}
