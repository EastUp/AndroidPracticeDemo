package com.east.architect_zenghui.architect_32_rxjava4.operate

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.darren.sharecomponent.ShareApplication
import com.east.architect_zenghui.R
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_operate.*
import java.util.concurrent.TimeUnit
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  RxBinding和Rxjava的常用操作
 *  @author: jamin
 *  @date: 2020/3/15 2:11 PM
 * |---------------------------------------------------------------------------------------------------------------|
 */
class OperateActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operate)

        ShareApplication.attach(this)

        // addTextChangedListener
        user_name_et.textChanges().subscribe {
            clear_content.visibility = if (TextUtils.isEmpty(it)) View.INVISIBLE else View.VISIBLE
        }

        val userNameObservable = user_name_et.textChanges()
        val userPwdObservable = user_password_et.textChanges()
        // 相当于合并
        Observable.combineLatest(userNameObservable, userPwdObservable,
            BiFunction<CharSequence, CharSequence, Boolean> { userName, userPwd ->
                !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPwd)
            }).subscribe {
            // 设置按钮是否可用(或者改变背景颜色)
            clear_content.isEnabled = it
        }

        // 防止重复点击 debounce（时间，时间的单位）
        clear_content.clicks().debounce(300, TimeUnit.MILLISECONDS).subscribe {

        }

        // 控制操作时间间隔
        user_name_et.textChanges().debounce(1200,TimeUnit.MILLISECONDS).subscribe {
            Log.e("TAG","debounce")
        }

        // 接口轮询，轮询操作
        Observable.interval(2,2,TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.e("TAG","interval")
            }

        // 延时操作
        Observable.timer(2,TimeUnit.SECONDS)
            .subscribe{
                Log.e("TAG","timer")
            }

    }
}
