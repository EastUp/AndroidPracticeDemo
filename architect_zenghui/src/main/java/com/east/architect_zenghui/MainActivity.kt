package com.east.architect_zenghui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.east.architect_zenghui.architect10_designmode3_factory.FactoryActivity
import com.east.architect_zenghui.architect11_Designmode4_decorator.DecoratorActivity
import com.east.architect_zenghui.architect12_Designmode5_tempolate.TemplateActivity
import com.east.architect_zenghui.architect13_Designmode6_strategy.StrategyActivity
import com.east.architect_zenghui.architect14_designmode7_adapter.AdapterActivity
import com.east.architect_zenghui.architect15_designmode8_observe.ObserveActivity
import com.east.architect_zenghui.architect16_designmode9_proxy.ProxyActivity
import com.east.architect_zenghui.architect17_designmode10_prototype.PrototypeActivity
import com.east.architect_zenghui.architect1_change_network_engine.simple3.MainActivity
import com.east.architect_zenghui.architect2_aop.AopActivity
import com.east.architect_zenghui.architect4_reflect_annotation_generics.ReflectAnnotationGenericsActivity
import com.east.architect_zenghui.architect5_butterknife.ButterKnifeActivity
import com.east.architect_zenghui.architect6_handler.HandlerActivity
import com.east.architect_zenghui.architect8_designmode1_singleton.MyMainActivity
import com.east.architect_zenghui.architect9_designmode2_builder.BuilderActivity
import com.east.architect_zenghui.architect_18_designmode11_iteration.IterationActivity
import com.east.architect_zenghui.architect_19_designmode12_responsibility_chain.ResponsibilityChainActivity
import com.east.architect_zenghui.architect_22_eventbus.EventBusActivity
import com.east.architect_zenghui.architect_24_okhttp2.OkHttp2Activity
import com.east.architect_zenghui.architect_25_okhttp3.OkHttp3Activity
import com.east.architect_zenghui.architect_26_okhttp4_interceptor.OkHttp4Activity
import com.east.architect_zenghui.architect_27_okhttp5.OkHttp5Activity
import com.east.architect_zenghui.architect_28_okhttp6_download.OkHttp6Activity
import com.east.architect_zenghui.architect_29_rxjava1.rxjava.Rxjava1Activity
import com.east.architect_zenghui.architect_30_rxjava2.Rxjava2Activity
import com.east.architect_zenghui.architect_31_rxjava3.Rxjava3Activity
import com.east.architect_zenghui.architect_32_rxjava4.Rxjava4Activity
import com.east.architect_zenghui.architect_33_retrofit1.Retrofit1Activity
import com.east.architect_zenghui.architect_34_retrofit2.Retrofit2Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View) {
        when (v) {
            change_netword_engine -> {//1.面向对象6大原则-网络引擎切换
                startActivity(Intent(this, MainActivity::class.java))
            }
            aop -> {//2.aop 面向切面编程
                startActivity(Intent(this, AopActivity::class.java))
            }
            //3.UML建模
            reflect_annotation_generics -> {//4.反射 注解和泛型
                startActivity(Intent(this, ReflectAnnotationGenericsActivity::class.java))
            }
            butterknife_test -> {//5.ButterKnife源码分析和手写(APT(Annotation Processing Tool))
                startActivity(Intent(this, ButterKnifeActivity::class.java))
            }
            //6.编译时注解-绕过微信支付和分享的局限  (具体请看 Joker项目)
            handler -> {//7.Handler源码分析
                startActivity(Intent(this, HandlerActivity::class.java))
            }
            singleton -> {//8.单例设计模式,activity管理
                startActivity(Intent(this, MyMainActivity::class.java))
            }
            builder -> { //9. Builder设计模式 增强版NavigationBar
                startActivity(Intent(this, BuilderActivity::class.java))
            }
            factory -> { //10. 工厂模式-数据存储的特有方式
                startActivity(Intent(this, FactoryActivity::class.java))
            }

            decorator -> { //11. 装饰者设计模式-RecycleView添加头部和底部
                startActivity(Intent(this, DecoratorActivity::class.java))
            }

            template -> { //12. 模板设计模式-手写OkHttp的Dispatcher
                startActivity(Intent(this, TemplateActivity::class.java))
            }

            strategy -> { //13.策略模式-Log 日志输出策略
                startActivity(Intent(this, StrategyActivity::class.java))
            }
            adapter_mode -> {// 14.Adapter设计模式-打造通用的IndicatorView
                startActivity(Intent(this, AdapterActivity::class.java))
            }
            observe -> {// 15.观察者设计模式-观察数据的插入
                startActivity(Intent(this, ObserveActivity::class.java))
            }

            proxy -> { // 16.代理设计模式 - 实现 Retrofit 的 create
                startActivity(Intent(this, ProxyActivity::class.java))
            }

            prototype -> { // 17.原型设计模式 - 订单查询拆分
                startActivity(Intent(this, PrototypeActivity::class.java))
            }

            iteration -> { // 18.迭代器设计模式 - 构建通用 BottomTabNavigat
                startActivity(Intent(this, IterationActivity::class.java))
            }

            responsibility_chain -> { // 19.责任链设计模式/ 门面设计模式 - QQ微信多用户系统检测
                startActivity(Intent(this, ResponsibilityChainActivity::class.java))
            }

            architect_20 -> {//20. 享元/命令/组合设计模式

            }

            architect_20 -> {//21. 状态/桥接/中介/备忘录设计模式

            }

            eventbus -> {  // 22.EventBus源码分析和手写
                startActivity(Intent(this, EventBusActivity::class.java))
            }

            okhttp1 -> { // 23.OkHttp-Java网络编程基础

            }
            okhttp2 -> { // 24.OkHttp-整体架构和源码分析
                startActivity(Intent(this, OkHttp2Activity::class.java))
            }
            okhttp3 -> { // 25.OkHttp-手写表单提交和文件上传
                startActivity(Intent(this, OkHttp3Activity::class.java))
            }
            okhttp4 -> { // 26.OkHttp-源码精髓之拦截器分析
                startActivity(Intent(this, OkHttp4Activity::class.java))
            }
            okhttp5 -> { // 27.OkHttp-上传进度监听和自定义缓存
                startActivity(Intent(this, OkHttp5Activity::class.java))
            }
            okhttp6 -> { // 28.文件断点下载
                startActivity(Intent(this, OkHttp6Activity::class.java))
            }
            rxjava1 -> { // 29.第三方开源库 RXJava - 基本使用和源码分析
                startActivity(Intent(this, Rxjava1Activity::class.java))
            }
            rxjava2 -> { // 30.第三方开源库 RxJava - 自己动手写事件变换
                startActivity(Intent(this, Rxjava2Activity::class.java))
            }
            rxjava3 -> { // 31.第三方开源库 RxJava - 自己动手线程调度切换
                startActivity(Intent(this, Rxjava3Activity::class.java))
            }
            rxjava4 -> { // 32.第三方开源库 RxJava - Android实际开发场景
                startActivity(Intent(this, Rxjava4Activity::class.java))
            }
            retrofit1 -> { // 33.第三方开源库 Retrofit - 源码设计模式分析
                startActivity(Intent(this,Retrofit1Activity::class.java))
            }
            retrofit2 -> { // 34.第三方开源库 Retrofit - 自己动手写核心架构部分
                startActivity(Intent(this, Retrofit2Activity::class.java))
            }
            okhttp_rxjava_retrofit -> { // 35.第三方开源库封装 - OkHttp + RxJava + Retrofit
                startActivity(Intent(this, com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple2.MainActivity::class.java))
            }
            retrofit_optimize -> { // 36.第三方开源库 Retrofit - 自己动手优化网络引擎
                startActivity(Intent(this, com.east.architect_zenghui.architect_36_retrofit_optimize.simple5.MainActivity::class.java))
            }
            mvp1 -> { // 37.开发模式 MVP - 基础框架搭建分析
                startActivity(Intent(this, Retrofit2Activity::class.java))
            }
            mvp2 -> { // 38.开发模式 MVP - 静态代理和动态扩展
                startActivity(Intent(this, Retrofit2Activity::class.java))
            }
            project_actual_combat1 -> { // 39.项目实战 - 代码架构和运行时架构
                startActivity(Intent(this, Retrofit2Activity::class.java))
            }
            project_actual_combat2 -> { // 40.项目实战 - 系统架构部分的总结和展望
                startActivity(Intent(this, Retrofit2Activity::class.java))
            }
            glide -> { // 41.第三方开源库 Glide - 源码分析（补）
                startActivity(Intent(this, Retrofit2Activity::class.java))
            }
            drouter -> { // 42.Android 多模块多组件开发 - 打造属于自己的路由（补）
                startActivity(Intent(this, Retrofit2Activity::class.java))
            }
            // 43.经验分享 - 深圳社招大厂面试分享（补）


        }
    }
}
