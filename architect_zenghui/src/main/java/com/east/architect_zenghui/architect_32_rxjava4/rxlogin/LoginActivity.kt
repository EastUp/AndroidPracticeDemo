package com.east.architect_zenghui.architect_32_rxjava4.rxlogin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darren.sharecomponent.ShareApplication
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_32_rxjava4.rxlogin.rxlogin.RxLogin
import com.east.architect_zenghui.architect_32_rxjava4.rxlogin.rxlogin.RxLoginPlatform
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login2.*
import java.util.concurrent.TimeUnit

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  仿照Rxpermission写一个RxLogin
 *  @author: jamin
 *  @date: 2020/3/15 2:35 PM
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        ShareApplication.attach(this)

        clear_content.clicks()
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                RxLogin.create(this)
                    .doOauthVerify(RxLoginPlatform.Platform_QQ)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it.isSucceed) {
                            // 怎么进来
                        }
                        Toast.makeText(
                            this,
                            it.msg,
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
    }
}
