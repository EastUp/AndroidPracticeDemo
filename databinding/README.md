```
    databinding
```
#配置说明
 	1-
 	  android{
 	  		dataBinding {
        		enabled = true
    		}
 	  }
 	2- 如果有注解@Bindable 则需要在gradle中添加
 	   apply plugin: 'kotlin-kapt'
#xml说明
	<?xml version="1.0" encoding="utf-8"?>
	<!-- 每个绑定文件都会生成一个绑定类,ViewDataBinding的实例名是根据布
	局文件名来生成的,将之改为首字母大写的驼峰命名法来命名，并省略布局文件
	名包含的下划线。控件的获取方式类似，但首字母小写,
	 -->
	<layout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools"
	        xmlns:app="http://schemas.android.com/apk/res-auto">
	    <!-- 数据标签 生成的绑定类默认以布局文件命名,也可以使用class指定-->
	    <!-- java.lang.*包的类会自动导入,可以直接使用 -->
	    <data class="UserBinding">
	        <!-- 如果多出使用可以使用import导入,如果存在类名相同的情况,可以使用别名进行区分 -->
	        <import alias="entityUser" type="com.east.databinding.entity.User"/>
	        <import alias="beanUser" type="com.east.databinding.bean.User"/>
	        <!-- 声明要使用的变量名和类的全路径 -->
	        <variable name="entityUserInfo" type="entityUser"/>
	        <variable name="beanUserInfo" type="beanUser"/>
	    </data>
	    <androidx.constraintlayout.widget.ConstraintLayout
	            android:layout_width="match_parent"
	            android:layout_height="match_parent"
	            tools:context=".MainActivity">
	
	        <TextView
	                android:id="@+id/tv_username"
	                android:layout_width="wrap_content"
	                android:layout_height="wrap_content"
	                android:text="@{beanUserInfo.username,default=username}"
	                app:layout_constraintLeft_toLeftOf="parent"
	                app:layout_constraintRight_toRightOf="parent"
	                app:layout_constraintTop_toTopOf="parent"/>
	
	        <TextView
	                android:id="@+id/tv_password"
	                android:layout_width="wrap_content"
	                android:layout_height="wrap_content"
	                android:text="@{beanUserInfo.password,default=password}"
	                app:layout_constraintTop_toBottomOf="@id/tv_username"
	                app:layout_constraintLeft_toLeftOf="parent"
	                app:layout_constraintRight_toRightOf="parent"
	                android:layout_marginBottom="10dp"/>
	
	        <FrameLayout
	                android:id="@+id/fl"
	                app:layout_constraintTop_toBottomOf="@id/tv_password"
	                app:layout_constraintBottom_toBottomOf="parent"
	                app:layout_constraintLeft_toLeftOf="parent"
	                app:layout_constraintRight_toRightOf="parent"
	                android:layout_width="0dp" android:layout_height="0dp"/>
	
	        
	
	    </androidx.constraintlayout.widget.ConstraintLayout>
	</layout>
     
#单向数据绑定
1. 继承 <font color=#0000ff size=4> Observable </font>  **<font color=#ff0000 size=5>"不推荐"</font>**  
	BaseObservable提供了notifyChange（）和notifyPropertyChanged（）两个方法，前者会刷新所有的值域，后者则只更新对应BR的旗帜，该BR的生成通过注释@Bindable生成，可以通过BR notify特定属性关联的视图
  
   		public class Goods extends BaseObservable  {
   		
		    //public 修饰符可以直接在成员变量上 加@Bindable 注解
		    @Bindable
		    public String name;
		
		    private String details;
		
		    //如果是private 则需要在get方法上加@Bindable 注解
		    private float price;
		
		    public Goods(String name, String details, float price) {
		        this.name = name;
		        this.details = details;
		        this.price = price;
		    }
		
		    public Goods() {
		    }
		
		    public String getName() {
		        return name;
		    }
		
		    public void setName(String name) {
		        this.name = name;
		    }
		
		    @Bindable
		    public String getDetails() {
		        return details;
		    }
		
		    public void setDetails(String details) {
		        this.details = details;
		    }
		
		    public float getPrice() {
		        return price;
		    }
		
		    public void setPrice(float price) {
		        this.price = price;
		    }
		}

2. 使用<font color=#0000ff size=4> ObservableField</font>  
   调用<font color=#0000ff size=4> ObservableField </font>的**<font color=#ff0000 size=5>set()</font>**方法才能更新视图数据
	```
	data class ObservableFieldFGoods(var name:ObservableField<String>,var details:ObservableField<String>,var price:ObservableFloat)
	```
3. 使用<font color=#0000ff size=4> ObservableList和ObservableMap </font>
		
		<?xml version="1.0" encoding="utf-8"?>
		<layout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools"
		        xmlns:app="http://schemas.android.com/apk/res-auto">
		    <data>
		        <import type="androidx.databinding.ObservableList"/>
		        <import type="androidx.databinding.ObservableMap"/>
		        <variable name="list" type="ObservableList&lt;String&gt;"/>
		        <variable name="map" type="ObservableMap&lt;String,String&gt;"/>
		        <variable name="key" type="String"/>
		    </data>
		    <androidx.constraintlayout.widget.ConstraintLayout
		            android:layout_width="match_parent"
		            android:layout_height="match_parent"
		            tools:context=".Test3Activity">
			
		        <TextView
		                android:id="@+id/tv_list"
		                app:layout_constraintTop_toTopOf="parent"
		                app:layout_constraintLeft_toLeftOf="parent"
		                android:text="@{list[0],default=娃哈哈}"
		                android:layout_width="wrap_content"
		                android:layout_height="wrap_content"/>
			
		        <TextView
		                android:id="@+id/tv_map"
		                app:layout_constraintTop_toBottomOf="@id/tv_list"
		                app:layout_constraintLeft_toLeftOf="parent"
		                android:text="@{map[key],default=儿童饮料}"
		                android:layout_width="wrap_content"
		                android:layout_height="wrap_content"/>
			
		        <Button
		                app:layout_constraintTop_toBottomOf="@id/tv_map"
		                app:layout_constraintLeft_toLeftOf="parent"
		                android:text="修改"
		                android:onClick="change"
		                android:layout_width="wrap_content"
		                android:layout_height="wrap_content"/>
			
		    </androidx.constraintlayout.widget.ConstraintLayout>
		</layout>
		
#双向数据绑定
&emsp;&emsp;双向绑定的意思即为当数据改变时同时使视图刷新，而视图改变时也可以同时改变数据  
&emsp;&emsp;绑定变量的方式比单向绑定多了一个等号:<font color=#0000ff>``` android:text="@={goods.name}"  ```</font>   
#事件绑定
事件处理有两种方式:  
1. 方法引用 (Method References <font color=#0000ff size=4>所绑定方法的参数必须与事件方法参数一致，同时返回值也必须一致</font>)  
	&emsp;&emsp;在事件属性的表达式中调用一个方法，这个方法的签名必须和事件回调方法签名一致。当事件回调发生时，就会去转而调用传入的这个方法
	&emsp;&emsp;调用语法可以是<font color=#ff0000>@{handlers::onClickFriend}或者@{handlers.onClickFriend} #deprecated弃用</font>  
2. 监听器绑定 (Listener Bindings <font color=#0000ff size=4>不再要求签名一致，而只是要求返回值一样</font>)
	&emsp;&emsp;```属性值表达式是一个lambda表达式，总会创建一个监听器来设置到目标view上，事件发生的时候才会执行这个表达式```  
	&emsp;&emsp;lambda表达式可以根据->分成两部分： 
->左边的部分是(view) ，这里是原来listener中void onClick(View v)中的参数，<font color=#ff0000>这个参数必须全部写或者全部不写,  
调用语法(必须在方法后+括号): @ {（） - > userPresenter.onUserNameClick（userInfo）}</font>  
#使用类方法
	object StringUtils {
    fun toUpperCase(str:String):String{
        return str.toUpperCase()
    	}
	}
****	
	<?xml version="1.0" encoding="utf-8"?>
	<layout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools"
	        xmlns:app="http://schemas.android.com/apk/res-auto">
	    <data>
	        <import type="com.east.databinding.utils.StringUtils"/>
	    </data>
	    <androidx.constraintlayout.widget.ConstraintLayout
	            android:layout_width="match_parent"
	            android:layout_height="match_parent"
	            tools:context=".Test6Activity">

	        <TextView
	                android:id="@+id/tv_name"
	                app:layout_constraintTop_toTopOf="parent"
	                app:layout_constraintLeft_toLeftOf="parent"
	                android:text="@{StringUtils.INSTANCE.toUpperCase(&quot;aabbccdd&quot;),default=aabbccdd}"
	                android:layout_width="wrap_content"
	                android:layout_height="wrap_content"/>

    	</androidx.constraintlayout.widget.ConstraintLayout>
	</layout>  
#运算符
1. 基础运算符  
	&emsp;&emsp;DataBinding 支持在布局文件中使用以下运算符、表达式和关键字
		
		算术  +  -  /  *  %  
		字符串合并  +  
		逻辑  &&  ||  
		二元  &  |  ^  
		一元  +  -  !  ~  
		移位 >>  >>>  <<  
		比较 ==  >  <  >=  <=  
		Instanceof  
		Grouping ()  
		character, String, numeric, null  
		Cast  
		方法调用  
		Field 访问  
		Array 访问 []  
		三元 ?:  
		
		目前不支持以下操作  
		
		this  
		super  
		new  
		显示泛型调用  
		
	此外，DataBinding 还支持以下几种形式的调用  
	
2. Null Coalescing  
&emsp;&emsp;空合并运算符 ?? 会取第一个不为 null 的值作为返回值  

		<TextView  
		     android:layout_width="match_parent"  
		     android:layout_height="wrap_content"  
		     android:text="@{user.name ?? user.password}" />

&emsp;&emsp;等价于  
		
		android:text="@{user.name != null ? user.name : user.password}"

3. 属性控制  
&emsp;&emsp;可以通过变量值来控制 View 的属性
	 
		 <TextView
		     android:layout_width="match_parent"
		     android:layout_height="wrap_content"
		     android:text="可见性变化"
		     android:visibility="@{user.male  ? View.VISIBLE : View.GONE}" />

4. 避免空指针异常  
&emsp;&emsp;DataBinding 也会自动帮助我们避免空指针异常  
&emsp;&emsp;例如，如果 "@{userInfo.password}" 中 userInfo 为 null 的话，userInfo.password 会被赋值为默认值 null，而不会抛出空指针异常

#include和viewStub
1. include  
	include_layout.xml
	
		<?xml version="1.0" encoding="utf-8"?>
		<layout xmlns:android="http://schemas.android.com/apk/res/android">
		    <data>
		        <import type="com.east.databinding.bean.User"/>
		        <variable name="user" type="User"/>
		    </data>
		    <LinearLayout
		            android:orientation="vertical"
		            android:layout_width="wrap_content"
		            android:layout_height="wrap_content">
		
		        <TextView
		                android:id="@+id/tv_name"
		                android:text="@{user.username}"
		                android:layout_width="wrap_content"
		                android:layout_height="wrap_content"/>
		
		    </LinearLayout>
		</layout>
		
	主布局 将相应的变量传递给 include 布局，从而使两个布局文件之间共享同一个变量
	
		<?xml version="1.0" encoding="utf-8"?>
		<layout xmlns:android="http://schemas.android.com/apk/res/android"
		        xmlns:tools="http://schemas.android.com/tools"
		        xmlns:bind="http://schemas.android.com/apk/res-auto">
		    <data>
		        <import type="com.east.databinding.bean.User"/>
		        <variable name="user" type="User"/>
		    </data>
		    <androidx.constraintlayout.widget.ConstraintLayout
		            android:layout_width="match_parent"
		            android:layout_height="match_parent"
		            tools:context=".Test8Activity">
		
		        <include
		                android:id="@+id/include"
		                layout="@layout/include"
		                bind:user="@{user}"/>
		        
		        <ViewStub
		                android:id="@+id/view_stub"
		                bind:layout_constraintLeft_toLeftOf="parent"
		                bind:layout_constraintTop_toBottomOf="@id/include"
		                android:layout="@layout/viewstub"
		                bind:user="@{user}"
		                android:inflatedId="@+id/view_stub"
		                android:layout_width="wrap_content"
		                android:layout_height="wrap_content"/>
		
		    </androidx.constraintlayout.widget.ConstraintLayout>
		</layout>
2. viewStub(<font color=#ff00000>ConstraintLayout中添加ViewStub 需要给viewStub添加inflatedId与ViewStub的android:id的值设置的完全一样，即可解决问题</font>)  

	viewStub.xml
	
		<?xml version="1.0" encoding="utf-8"?>
		<layout xmlns:android="http://schemas.android.com/apk/res/android">
		    <data>
		        <import type="com.east.databinding.bean.User"/>
		        <variable name="user" type="User"/>
		    </data>
		    <LinearLayout
		            android:orientation="vertical"
		            android:layout_width="match_parent"
		            android:layout_height="match_parent">
		
		        <TextView
		                android:text="@{user.password}"
		                android:layout_width="wrap_content"
		                android:layout_height="wrap_content"/>
		
		    </LinearLayout>
		</layout>
		
	主布局见上上图
	
		class Test8Activity : AppCompatActivity() {

		    override fun onCreate(savedInstanceState: Bundle?) {
		        super.onCreate(savedInstanceState)
		        val activityTest8Binding =
		            DataBindingUtil.setContentView<ActivityTest8Binding>(this, R.layout.activity_test8)
		        val user = User("娃哈哈", "功能饮料", false)
		        activityTest8Binding.user = user
		
		
		        activityTest8Binding.viewStub.setOnInflateListener { stub, inflated ->
		
		            //如果xml中没有 bind:user="@{user}"对数据进行绑定
		            //那么可以在此处手动进行绑定
		            val viewstubBinding = DataBindingUtil.bind<ViewstubBinding>(inflated)
		            viewstubBinding?.user =user
		        }
		
		        activityTest8Binding.viewStub.viewStub!!.inflate()
		    }
		}

#BindingAdapter
&emsp;&emsp;这个注解用于支持自定义属性，或者是修改原有属性  
&emsp;&emsp;需要先定义一个静态方法，为之添加 BindingAdapter 注解  
静态方法的两个参数可以这样来理解：<font color=#ff0000>第一个是"作用的控件是谁",第二个是"获取这个控件上这个属性的值"</font>

	companion object{
        //覆盖Android原先控件属性
        //两个参数解释:只有当TextView控件的android:text属性发生变化时才会生效
        @BindingAdapter("android:text")
        @JvmStatic
        fun changeText(view:TextView,str:String?){
            view.text = str + "BindAdataper"
        }

        @BindingAdapter("password")
        @JvmStatic
        fun changePassword(view:TextView,str:String?){
            view.text = str+"自定义属性password"
        }
    }
#BindingConversion
 1. BindingConversion比BindingAdapter的优先级要高些  
 2. 此外，BindingConversion也可以用于转换属性值的类型  
    以下把 textColor 的字符串"红色" 转换成了 Int 类型
    
		<?xml version="1.0" encoding="utf-8"?>
		<layout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools"
		        xmlns:app="http://schemas.android.com/apk/res-auto"
		        xmlns:bind="http://schemas.android.com/apk/res-auto">
		    <data>
		        <variable name="goods" type="com.east.databinding.entity.ObservableFieldFGoods"/>
		    </data>
		    <androidx.constraintlayout.widget.ConstraintLayout
		            android:layout_width="match_parent"
		            android:layout_height="match_parent"
		            tools:context=".Test10Activity">
		
		        <TextView
		                android:id="@+id/tv_name"
		                app:layout_constraintLeft_toLeftOf="parent"
		                app:layout_constraintTop_toTopOf="parent"
		                android:text="@{goods.name}"
		                android:textColor='@{"红色"}'
		                android:layout_width="wrap_content"
		                android:layout_height="wrap_content"/>
		
		
		    </androidx.constraintlayout.widget.ConstraintLayout>
		</layout>
	
	***
	
		object Presenter {
	
	        //布局文件中所有@{String}的String进行处理
	        @BindingConversion
	        @JvmStatic
	        open fun conversionString(str: String): String {
	            return "$str--conversion--"
	        }
	
	        //布局文件中所有@{String}的String进行处理
	        @BindingConversion
	        @JvmStatic
	        open fun conversionStringToDrawable(str: String): Int {
	            return if (str == "红色")
	                Color.parseColor("#FF4081")
	            else
	                Color.BLACK
	        }
	
	        //覆盖Android原先控件属性
	        //两个参数解释:只有当textView控件的android:text属性发生变化时才会生效
	        @BindingAdapter("android:text")
	        @JvmStatic
	        open fun changeText(view: TextView, str: String?) {
	            view.text = str + "BindAdataper"
	        }
	
	    }	
	
    
 <font color=#ff0000 size=5>切记:</font>不能在Companion Object中声明带@BindingConversion的方法,以下是报错信息  
 
		@BindingConversion is only allowed on public static methods conversionString(java.lang.String)  
		 public java.lang.String conversionString(@org.jetbrains.annotations.NotNull()

使用 Tool -> Kotlin -> Show Kotlin Bytecode 点击<font color=#ff0000> Decompile </font> 编译后的结果为:  方法上没有 static 修饰

		 public static final class Companion {
	      @BindingConversion
	      @JvmStatic
	      @NotNull
	      
	      //没有 static 修饰
	      public String conversionString(@NotNull String str) {
	         Intrinsics.checkParameterIsNotNull(str, "str");
	         return str + "--conversion--";
	      }
	
	      @BindingConversion
	      @JvmStatic
	      public int conversionStringToDrawable(@NotNull String str) {
	         Intrinsics.checkParameterIsNotNull(str, "str");
	         return Intrinsics.areEqual(str, "红色") ? Color.parseColor("#FF4081") : -16777216;
	      }
	
	      @BindingAdapter({"android:text"})
	      @JvmStatic
	      public void changeText(@NotNull TextView view, @Nullable String str) {
	         Intrinsics.checkParameterIsNotNull(view, "view");
	         view.setText((CharSequence)Intrinsics.stringPlus(str, "BindAdataper"));
	      }
	
	      private Companion() {
	      }
	
	      // $FF: synthetic method
	      public Companion(DefaultConstructorMarker $constructor_marker) {
	         this();
	      }
	   }  

#List-Set-Map-Array...
	 <?xml version="1.0" encoding="utf-8"?>
	<layout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools">
	
	    <data>
	        <import type="java.util.List" />
	        <import type="java.util.Map" />
	        <import type="java.util.Set" />
	        <import type="android.util.SparseArray" />
	        <variable
	            name="array"
	            type="String[]" />
	        <variable
	            name="list"
	            type="List&lt;String&gt;" />
	        <variable
	            name="map"
	            type="Map&lt;String, String&gt;" />
	        <variable
	            name="set"
	            type="Set&lt;String&gt;" />
	        <variable
	            name="sparse"
	            type="SparseArray&lt;String&gt;" />
	        <variable
	            name="index"
	            type="int" />
	        <variable
	            name="key"
	            type="String" />
	    </data>
	
	    <LinearLayout
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:orientation="vertical"
	        tools:context=".Main7Activity">
	
	        <TextView
	            ···
	            android:text="@{array[1]}" />
	        <TextView
	            ···
	            android:text="@{sparse[index]}" />
	        <TextView
	            ···
	            android:text="@{list[index]}" />
	        <TextView
	            ···
	            android:text="@{map[key]}" />
	        <TextView
	            ···
	            android:text='@{map["leavesC"]}' />
	        <TextView
	            ···
	            android:text='@{set.contains("xxx")?"xxx":key}' />
	    </LinearLayout>
	</layout>
#资源引用

		<data>
	        <variable
	            name="flag"
	            type="boolean" />
	   </data>       
	   <Button
	         android:layout_width="match_parent"
	         android:layout_height="wrap_content"
	         android:paddingLeft="@{flag ? @dimen/paddingBig:@dimen/paddingSmall}"
	         android:text='@{@string/format("leavesC", "Ye")}'
	         android:textAllCaps="false" />



	   
	   
	

