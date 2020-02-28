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
            //6.编译时注解-绕过微信支付和分享的局限
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
        }
    }
}
