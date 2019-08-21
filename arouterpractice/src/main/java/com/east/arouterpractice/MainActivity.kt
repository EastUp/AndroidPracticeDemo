package com.east.arouterpractice

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.fastjson.JSON
import com.east.arouterpractice.testinject.TestKotlinFastJsonData
import com.east.arouterpractice.testinject.TestObj
import com.east.arouterpractice.testinject.TestParcelable
import com.east.arouterpractice.testinject.TestSerializable
import com.east.arouterpractice.testservice.HelloService
import com.east.arouterpractice.testservice.SingleService
import java.util.ArrayList
import java.util.HashMap

/**
 *  组件化Arouter测试
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity = this
//        ARouter.getInstance()
//            .build("/test/1")
//            .withString("name","lisi")
//            .withBoolean("gender",true)
//            .navigation()
    }

    companion object {
        var activity: Activity? = null

        fun getThis(): Activity? {
            return activity
        }
    }

    fun onClick(v: View) {
        when (v.id) {
            /**
             * ----------------------------------------基本设置-------------------------------
             */
            //打开日志并打印堆栈
//            R.id.openLog -> ARouter.openLog()
//            //开启调试模式(InstantRun需要开启)
//            R.id.openDebug -> ARouter.openDebug()
//            //初始化AROUTER
//            R.id.init -> {
//                // 调试模式不是必须开启，但是为了防止有用户开启了InstantRun，但是
//                // 忘了开调试模式，导致无法使用Demo，如果使用了InstantRun，必须在
//                // 初始化之前开启调试模式，但是上线前需要关闭，InstantRun仅用于开
//                // 发阶段，线上开启调试模式有安全风险，可以使用BuildConfig.DEBUG
//                // 来区分环境
//                ARouter.openDebug()
//                ARouter.init(application)
//            }
            //关闭ARouter
            R.id.destroy -> ARouter.getInstance().destroy()

            /**
             * ----------------------------------------基础功能(请先初始化)-------------------------------
             */
            //简单的应用内跳转
            R.id.normalNavigation -> ARouter.getInstance()
                .build("/arouter1/activity2")
                .navigation()
            //跳转到Kotlin页面
            R.id.kotlinNavigation -> ARouter.getInstance()
                .build("/kotlin/test")
                .withString("name", "老王")
                .withInt("age", 23)
                .navigation()
            //跳转ForResult
            R.id.normalNavigation2 -> ARouter.getInstance()
                .build("/arouter1/activity2")
                .navigation(this, 666)
            //获取Fragment实例
            R.id.getFragment -> {
                val fragment = ARouter.getInstance().build("/arouter2/fragment").navigation() as Fragment
                Toast.makeText(this, "找到Fragment:$fragment", Toast.LENGTH_SHORT).show()
            }
            //携带参数的应用内跳转
            R.id.normalNavigationWithParams -> {
                // ARouter.getInstance()
                //         .build("/test/activity2")
                //         .withString("key1", "value1")
                //         .navigation();

                val testUriMix = Uri.parse("arouter://m.aliyun.com/arouter1/activity2")
                ARouter.getInstance().build(testUriMix)
                    .withString("key1", "value1")
                    .navigation()
            }
            //旧版本转场动画
            R.id.oldVersionAnim -> ARouter.getInstance()
                .build("/arouter1/activity2")
                .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                .navigation(this)
            //新版本转场动画
            R.id.newVersionAnim -> if (Build.VERSION.SDK_INT >= 16) {
                val compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.width / 2, v.height / 2, 0, 0)

                ARouter.getInstance()
                    .build("/arouter1/activity2")
                    .withOptionsCompat(compat)
                    .navigation()
            } else {
                Toast.makeText(this, "API < 16,不支持新版本动画", Toast.LENGTH_SHORT).show()
            }

            /**
             * ----------------------------------------进阶用法-------------------------------
             */
            //通过URL跳转
            R.id.navByUrl -> ARouter.getInstance()
                .build("/arouter1/webview")
                .withString("url", "file:///android_asset/schame-test.html")
                .navigation()
            //拦截器测试
            R.id.interceptor -> ARouter.getInstance()
                .build("/arouter1/activity4")
                .navigation(this, object : NavCallback() {
                    override fun onArrival(postcard: Postcard) {

                    }

                    override fun onInterrupt(postcard: Postcard?) {
                        Log.d("ARouter", "被拦截了")
                    }
                })
            //依赖注入(参照代码)
            /**
             *  ----默认支持serializable和parcelable|||如果要支持直接传递object则需要创建一个实现类实现  SerializationService
             *  ----fastJson1.2.48无法parseObject   com.alibaba:fastjson:1.2.32 这个版本没问题
             *
             *  出现的问题:kotlin的 default constructor not found
             *  解决:
             *       1..修改fastjson版本为: implementation 'com.alibaba:fastjson:1.2.32'  这个还可以解决map无法解析的问题(第二问题)
             *    或 2..添加: implementation "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
             *
             *
             *  出现的问题: com.alibaba.fastjson.JSONException: TODO
             */
            R.id.autoInject -> {
                var testSerializable = TestSerializable("Titanic", 555)
                var testParcelable = TestParcelable("jack", 666)
                var testObj = TestObj("Rose", 777)
//                var testObj = TestKotlinFastJsonData("Rose", 777)
                var objList = ArrayList<TestObj>()
//                var objList = ArrayList<TestKotlinFastJsonData>()
                objList.add(testObj)

                val jsonString = JSON.toJSONString(testObj)
                Log.d("fastJson","jsonString:$jsonString")
                Log.d("fastJson","bean:${JSON.parseObject(jsonString)}")

                var map = HashMap<String, List<TestObj>>()
//                var map = HashMap<String, List<TestKotlinFastJsonData>>()
                map["testMap"] = objList

                ARouter.getInstance().build("/arouter1/activity1")
                    .withString("name", "老王")
                    .withInt("age", 18)
                    .withBoolean("boy", true)
                    .withLong("high", 180)
                    .withString("url", "https://a.b.c")
                    .withSerializable("ser", testSerializable)
                    .withParcelable("pac", testParcelable)
                    .withObject("obj", testObj)
                    .withObject("objList", objList)
                    .withObject("map", map)
                    .navigation()
            }
            /**
             * ----------------------------------------服务管理-------------------------------
             */
            //ByName调用服务
            R.id.navByName -> (ARouter.getInstance().build("/yourservicegroupname/hello").navigation() as HelloService).sayHello(
                "mike"
            )
            //ByType调用服务
            R.id.navByType -> ARouter.getInstance().navigation(HelloService::class.java).sayHello("mike")
            //调用单类
            R.id.callSingle -> ARouter.getInstance().navigation(SingleService::class.java).sayHello("Mike")
            /**
             * ----------------------------------------多模块测试-------------------------------
             */
            //跳转到模块1
            R.id.navToMoudle1 -> ARouter.getInstance().build("/kotlin/test").navigation()
            //跳转到模块2
            R.id.navToMoudle2 ->
                // 这个页面主动指定了Group名
                ARouter.getInstance().build("/module/2", "m2").navigation()
            /**
             * ----------------------------------------跳转失败测试-------------------------------
             */
            //跳转失败，单独降级
            R.id.failNav -> ARouter.getInstance().build("/xxx/xxx").navigation(this, object : NavCallback() {
                override fun onFound(postcard: Postcard?) {
                    Log.d("ARouter", "找到了")
                }

                override fun onLost(postcard: Postcard?) {
                    Log.d("ARouter", "找不到了")
                }

                override fun onArrival(postcard: Postcard) {
                    Log.d("ARouter", "跳转完了")
                }

                override fun onInterrupt(postcard: Postcard?) {
                    Log.d("ARouter", "被拦截了")
                }
            })
            //跳转失败，全局降级
            R.id.failNav2 -> ARouter.getInstance().build("/xxx/xxx").navigation()
            //服务调用失败
            R.id.failNav3 -> ARouter.getInstance().navigation(MainActivity::class.java)
            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 666) {
            Log.i(
                "activityResult", "返回结果码为resultCode=$resultCode--" +
                        "返回信息为:${data?.getStringExtra("result")}"
            )
        }

    }

}
