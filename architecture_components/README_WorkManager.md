```
    WorkManager
```
[android-architecture-components/WorkManagerSample/](https://github.com/googlesamples/android-architecture-components/tree/master/WorkManagerSample)

# WorkManager(androidx.work.WorkManager)
 [androidx.work.WorkManager](https://developer.android.google.cn/topic/libraries/architecture/workmanager)通过WorkManager API，可以轻松安排即使应用程序退出或设备重新启动也可以运行的可延迟的异步任务。
 
 **关键功能:**

- 兼容最低API 14
 	- 在API 23+ 的设备上使用JobScheduler实现
	- 在API14-22的设备上使用BroadcastReceiver + AlarmManager组合实现
- 可以添加执行任务的约束条件，例如：网络是否连接、是否处于充电状态
- 可以用于完成一次性、周期性的异步任务
- 监控管理任务执行
- 可以将任务串联起来完成（例如先完成任务A再完成任务B最后完成任务C）
- 即使是App、手机重启也能保证执行相关任务

WorkManager被设计用于执行延迟任务，这意味着这些任务不需要被立即执行，并且这些延迟任务在App被杀死或者设备重启后依然能够可靠的执行。这么说有点抽象了，举两个这样任务的例子：

- 发送App运行的log和分析数据到后端服务器（这个比较像友盟、Bugly这样的服务会用到）
- 周期性的和服务器同步数据（例如：笔记类的应用，就可以定期的同步服务器和App的笔记）

WorkManager不适用于完成需要和App生命周期关联的任务，也不适用于完成需要被立即执行的任务。关于什么样的任务适用Android提供的相关API，可以参考下图（这里的任务都是指需要后台执行的任务）

![](https://developer.android.google.cn/images/guide/background/bg-job-choose.svg)

* Event 事件 从组件或者Lifecycle类分发出来的生命周期，它们和Activity／Fragment生命周期的事件一一对应。(ON_CREATE,ON_START,ON_RESUME,ON_PAUSE,ON_STOP,ON_DESTROY)

* State 状态 当前组件的生命周期状态(INITIALIZED,DESTROYED,CREATED,STARTED,RESUMED)

说的有点抽象了，看一下下面的图：

   ![image.png](https://upload-images.jianshu.io/upload_images/3067882-a68fdc08777bdc15.png)



简单明了的选择分类，就不做过多解释了。

由于Android对后台任务管理的不断严格，在完成后台任务的时候需要考虑不同API版本对后台任务的限制。关于如何选择完成后台任务的API，可以按如下几点来考虑：


- 后台任务是否需要被立即执行还是可以延迟执行？ 例如如果是点击按钮获取网络数据，那么这个任务就需要被立即执行。但是如果只是想把log上传到服务器，那么这个任务就可以延迟执行，这样就不会对App运行有影响

- 完成任务是否需要系统处于某些特定的场景 有些任务可能需要在某些特定的场景下执行，例如手机处于充电模式并且有网络连接等情况。为什么要处于这样的场景下才完成某些任务呢？如果手机处于充电、熄屏并且连接wifi等情况下，那么我们就可以完成一些比较耗电耗流量的任务，并且不会对用户体验造成任何影响。这样的任务有可能需要事先存储需要完成的任务，再集中执行。

- 任务是否需要在精确的时间被执行 日历类的应用需要在精确地时间提醒用户设置的事件，但其他的任务可能就没有必要在精确地时间执行。通常有可能的情况是：任务A执行完成->执行任务B完成->执行任务C，但是并不需要这些任务在精确地时间（例如下午6：30）执行。

好了,使用workmanager的场景想必大家都了解一点了,下面我们看看如何使用WorkManager

## 如何使用

### 1.依赖

第一步当然是添加workmanager的**依赖**啦!!

将以下依赖添加进入你的build.gradle中:

```
dependencies {
  def work_version = "2.2.0"

    // (Java only)
    implementation "androidx.work:work-runtime:$work_version"

    // Kotlin + coroutines
    implementation "androidx.work:work-runtime-ktx:$work_version"

    // optional - RxJava2 support
    implementation "androidx.work:work-rxjava2:$work_version"
    // optional - Test helpers
    androidTestImplementation "androidx.work:work-testing:$work_version"
  }
```

### 2.创建worker子类(即一个后台任务)

创建一个类继承[worker](https://developer.android.google.cn/reference/androidx/work/Worker),重写`dowork()`方法,doWork()方法是在WorkManager提供的后台线程上同步运行。请看下面代码:

```kotlin
class TestWorker(var context: Context,workParams:WorkerParameters) : Worker(context,workParams){

    val TAG = TestWorker::class.java.simpleName
    var handler = Handler(Looper.getMainLooper())

    //这是在后台线程运行的
    override fun doWork(): Result {

        handler.post {
            Toast.makeText(context,"测试work是在子线程运行的", Toast.LENGTH_SHORT).show()
        }

        return Result.success()
    }
}
```

### 3.配置worker运行的方式和时间

`worker`定义要做什么任务,[workRequest](https://developer.android.google.cn/reference/androidx/work/WorkRequest)定义了如何以及何时运行任务.任务可能是一次性或定期的,对于一次性的任务,使用[OneTimeWorkRequest](https://developer.android.google.cn/reference/androidx/work/OneTimeWorkRequest),对于定期的任务使用[PeriodicWorkRequest](https://developer.android.google.cn/reference/androidx/work/PeriodicWorkRequest).

下面是WorkRequest的简单示例:

```kotlin
var testWorkRequest = OneTimeWorkRequestBuilder<TestWorker>().build()
```

### 4.将任务交给系统

之前以及定义了`WorkRequest`,现在你可以使用[WorkManager](https://developer.android.google.cn/reference/androidx/work/WorkManager)的[enqueue()](https://developer.android.google.cn/reference/androidx/work/WorkManager.html#enqueue(androidx.work.WorkRequest))方法将任务添加到系统计划中.任务入队，`WorkManager`调度执行

```kotlin
WorkManager.getInstance(this).enqueue(testWorkRequest)
```

## 进阶使用

### 一.自定义WorkRequest(自定义任务何时,如何执行)

#### 1.任务的限制条件

可以添加约束`Constraints`来指明任务什么时候执行,请看一下示例:

```kotlin
 var constraints = Constraints.Builder()
                    .setRequiresBatteryNotLow(true)////执行任务时电池电量不能偏低。
                    .setRequiresCharging(true)////在充电时执行
//                    .setRequiresDeviceIdle(true)////在待机状态执行
                    .setRequiresStorageNotLow(true)//设备储存空间足够时才能执行。
//                    .setTriggerContentMaxDelay(Duration.ZERO)
//                    .setTriggerContentUpdateDelay(Duration.ZERO)
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)//对网络没要求
                    .build()

                var request = OneTimeWorkRequestBuilder<ConstraintWorker>()
                    .setConstraints(constraints)
                    .build()
                WorkManager.getInstance(this).enqueue(request)
```

其它的都只是传入一个boolean值,网络状态要复杂点,具体看一下说明:

```
	/**
     * 指定网络状态执行任务
     * NetworkType.NOT_REQUIRED：对网络没有要求
     * NetworkType.CONNECTED：网络连接的时候执行
     * NetworkType.UNMETERED：不计费的网络比如WIFI下执行
     * NetworkType.NOT_ROAMING：非漫游网络状态
     * NetworkType.METERED：计费网络比如3G，4G下执行。
     */
```

#### 2.初始化延时

当没有约束条件或约束条件都满足的情况下,系统会直接运行任务,如果不想直接运行,可以通过`WorkRequest`的setInitialDelay()方法设置执行的初始延时时间.

```kotlin
var request = OneTimeWorkRequest.Builder(InitialDelayWorker::class.java)
                    .setInitialDelay(5, TimeUnit.SECONDS)
                    .build()
                WorkManager.getInstance(this).enqueue(request)
```

#### 3.重试和后退策略

如果你要求`WorkManager`重行执行你的任务,你可以在你的`Worker`中返回[Result.retry()](https://developer.android.google.cn/reference/androidx/work/ListenableWorker.Result.html#retry()).你的任务会安排一个默认的退避延时和策略.退避策略定义了后续重试尝试的后退延迟将如何随时间增加(这块有点儿难理解,请接着往下看)

这是可以指定退避策略和延时通过[WorkRequest.setBackoffCriteria(@NonNull BackoffPolicy backoffPolicy,
                long backoffDelay,
                @NonNull TimeUnit timeUnit)]()方法.

**这里注意几点:**

1. 默认延时: **30s**  
   退避策略BackoffPolicy: BackoffPolicy.EXPONENTIAL (指数)


2. 退避延时要求最小为:**10s(10秒)**,最大为**5h(5个小时)**,设置的时间小于10秒按10秒来算,大于5小时按5小时算

3. 退避策略解释(**默认延时设置为10秒**):  
   BackoffPolicy.EXPONENTIAL (指数)规律: <font color=#ff0000>Math.scalb(10, 尝试次数 - 1) 即 10 × 2^(尝试次数-1)</font>  
   BackoffPolicy.LINEAR (线性)规律: <font color=#ff0000>10 20 30 40 50 即  (10 * 尝试次数)</font>
   

在`WorkRequest`中定义的退避延时范围及默认延时  


   ```kotlin
   public abstract class WorkRequest {

	    /**
	     * The default initial backoff time (in milliseconds) for work that has to be retried.
	     */
	    public static final long DEFAULT_BACKOFF_DELAY_MILLIS = 30000L;
	
	    /**
	     * The maximum backoff time (in milliseconds) for work that has to be retried.
	     */
	    public static final long MAX_BACKOFF_MILLIS = 5 * 60 * 60 * 1000; // 5 hours.
	
	    /**
	     * The minimum backoff time for work (in milliseconds) that has to be retried.
	     */
	    public static final long MIN_BACKOFF_MILLIS = 10 * 1000; // 10 seconds.
    
    ....
    }
   ```

#### 4.定义任务的输入/输出

您的任务可能要求将数据作为输入参数传递或作为结果返回。例如，处理上传图片的任务需要将图片的URI作为输入参数，并且可能需要将上传图片的URL作为输出。

**注意**：Data对象应该很小，值可以是字符串，基本类型或它们的数组变体，Data对象的最大大小限制为`10KB`。如果你需要在worker中输入输出更多的数据，则应将数据放在其他位置，例如Room数据库。  

Worker类可以通过调用Worker.getInputData()来访问输入参数。

Data类还可用于输出返回值。通过将Data对象包含在Result.success（）或Result.failure（）中的Result中来返回Data对象，如下所示。

```kotlin
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  worker的输入/输出数据
 *  @author: East
 *  @date: 2019-08-16
 * |---------------------------------------------------------------------------------------------------------------|
 */
class InputOutputWorker(var context: Context, workParams:WorkerParameters) : Worker(context,workParams){

    val TAG = InputOutputWorker::class.java.simpleName
    var handler = Handler(Looper.getMainLooper())

    //这是在后台线程运行的
    override fun doWork(): Result {

        val s = inputData.getString("key")

        Log.d(TAG,"接受到的数据为:$s")
        handler.post {
            Toast.makeText(context,"接受到的数据为:$s", Toast.LENGTH_SHORT).show()
        }



        //创建需要返回的数据
        var outputData = workDataOf("key" to "this is output data")

        //返回数据
        return Result.success(outputData)
    }
}
```

#### 5.标记Work

您可以通过为任何WorkRequest对象分配标记字符串来逻辑地对任务进行分组。这允许您使用特定标记操作任务。例如：

- WorkManager.getInstance(Context context).cancelAllWorkByTag(String tag)  关闭指定tag的所有任务
- WorkManager.getInstance(Context context).getWorkInfosByTagLiveData(String tag) 返回一个包含指定tag的所有任务状态列表的livedata

示例如下：

```kotlin
var request = OneTimeWorkRequestBuilder<TestWorker>()
    .addTag("clean up")
    .build()
    
//关闭所有tag为clean up的任务
//WorkManager.getInstance(this).cancelAllWorkByTag("clean up")
    
WorkManager.getInstance(this).enqueue(request)
```

### 二.Work States and observing work(任务状态和观察任务)

#### 1.Work State

随着work的一生,work会经历很多状态,我们来学些下有哪些状态

- [BLOCKED](https://developer.android.google.cn/reference/androidx/work/WorkInfo.State.html#BLOCKED)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;如果尚未满足所有的约束条件时.
- [ENQUEUE](https://developer.android.google.cn/reference/androidx/work/WorkInfo.State.html#ENQUEUED)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;满足了所有的约束条件时.
- [RUNNING](https://developer.android.google.cn/reference/androidx/work/WorkInfo.State.html#RUNNING) &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;worker正在被运行时
- [SUCCEEDED](https://developer.android.google.cn/reference/androidx/work/WorkInfo.State.html#SUCCEEDED)&emsp;&emsp;&emsp;当worker返回`Result.success()`时,这是一个终极状态,只有OneTimeWorkRequests可以进入此状态。
- [FAILED](https://developer.android.google.cn/reference/androidx/work/WorkInfo.State.html#FAILED)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;当worker返回`Result.failure()`时,这也是一个终极状态,只有OneTimeWorkRequests可以进入此状态。后续所有相关联的worker都会被标记为FAILED状态且不会被执行.
- [CANCEL](https://developer.android.google.cn/reference/androidx/work/WorkInfo.State.html#CANCELLED)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;当明确指定cancel尚未终止的WorkRequest时.

#### 2.Observing your work(观察你的任务)

Worker的状态信息在[WorkInfo](https://developer.android.google.cn/reference/androidx/work/WorkInfo.html)对象中,WorkInfo中包含了work的`id`,`tags`,还有它当前的`state`以及一些输出数据.

可以通过以下三种方式获取workInfo:

- 通过id,&emsp;&emsp;WorkManager.getInstance(Context).getWorkInfoById(UUID) or WorkManager.getInstance(Context).getWorkInfoByIdLiveData(UUID).
- 通过tag,&emsp;&emsp;WorkManager.getInstance(Context).getWorkInfosByTag(String) or WorkManager.getInstance(Context).getWorkInfosByTagLiveData(String).
- 通过 unique work name,,&emsp;&emsp;WorkManager.getInstance(Context).getWorkInfosForUniqueWork(String) or WorkManager.getInstance(Context).getWorkInfosForUniqueWorkLiveData(String)

示例代码如下:

```kotlin
var request = OneTimeWorkRequestBuilder<InputOutputWorker>()
                    .addTag("clean up")
                    .build()


WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id).observe(this, Observer {
    if(it != null && it.state == WorkInfo.State.SUCCEEDED){
        val value = it.outputData.getString("key")
        Log.d(TAG,"work 完成,接收到数据为:$value")
    }
})

//val listenableFuture = WorkManager.getInstance(this).getWorkInfosByTag("clean up")
//val workInfo = listenableFuture.get()

//WorkManager.getInstance(this).getWorkInfosForUniqueWork("hehe")



WorkManager.getInstance(this).enqueue(request)
//WorkManager.getInstance(this).enqueueUniqueWork("hehe",ExistingWorkPolicy.REPLACE,request)
```

### 三.任务链

#### 1.介绍任务链

WorkManager允许你创建并排队运行一个任务链.

创建任务链使用 `WorkManager.getInstance(Context).beginWith(OneTimeWorkRequest)` or `WorkManager.getInstance(Context).beginWith(List<OneTimeWorkRequest>)` 会返回一个[WorkContinuation](https://developer.android.google.cn/reference/androidx/work/WorkContinuation)

`WorkContinuation`添加`OneTimeWorkRequest`使用方法 `WorkContinuation.then(OneTimeWorkRequest)` or `WorkContinuation.then(List<OneTimeWorkRequest>)` .会接着返回`WorkContinuation`的新实例,如果添加`List` of `OneTimeWorkRequests`,则他们并行执行.最后调用`WorkContinuation.enqueue() `方法来排队运行你的任务链.

```kotlin
var request = OneTimeWorkRequestBuilder<TestWorker>().build()
var requestConstraint = OneTimeWorkRequestBuilder<ConstraintWorker>().build()
var requestInputOutputWorker = OneTimeWorkRequestBuilder<InputOutputWorker>().build()

WorkManager.getInstance(this)
	 //workRequest并行运行
    .beginWith(arrayListOf(request,requestConstraint))
    //在之前的workRequst之后运行
    .then(requestInputOutputWorker)
    //最后别忘了enqueue
    .enqueue()
```

#### 2.Input Mergers

在使用`OneTimeWorkRequest`的任务链时,上一个WorkRequest的输出Data将会作为下一个WorkRequest的输入Data.比如上面示例中`request`,`requestConstraint`的输出Data将会作为`requestInputOutputWorker`的输入Data.

为了管理来自多个父OneTimeWorkRequests的输入，WorkManager使用[InputMergers](InputMerger)。**需要在WorkRequest中调用他的setInutMerger方法**它有两种不同的类型:

- [OverwritingInputMerger](https://developer.android.google.cn/reference/androidx/work/OverwritingInputMerger.html)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;尝试将所有输入的所有keys添加到输出Data中。如果发生冲突，它会覆盖先前设置的密钥。
- [ArrayCreatingInputMerger](https://developer.android.google.cn/reference/androidx/work/ArrayCreatingInputMerger.html)&emsp;&emsp;&emsp;&emsp;尝试合并输入，并在必要时创建数组。获取输入数据时在`dowork()`方法中调用 getInputData().getStringArray(String key)方法获取在链中之前执行的workRequest中相同key对应的value值.

```kotlin
var request = OneTimeWorkRequestBuilder<TestWorker>()
    .build()
var requestConstraint = OneTimeWorkRequestBuilder<ConstraintWorker>()
    .build()
var requestInputOutputWorker = OneTimeWorkRequestBuilder<InputOutputWorker>()
    .setInputMerger(ArrayCreatingInputMerger::class.java)
    .build()
	
	
WorkManager.getInstance(this)
    //workRequest并行运行
    .beginWith(arrayListOf(request,requestConstraint))
    //在之前的workRequst之后运行
    .then(requestInputOutputWorker)
    //最后别忘了enqueue
    .enqueue()
```

InputOutputWorker:


```kotlin
//如果inputMerger是OverwritingInputMerger则 s = "constraintworker"
//val s = inputData.getString("key")

//如果inputMerger是ArrayCreatingInoputMerger则 s = "[testworker,constraintworker]"
val s = inputData.getStringArray("key")
```

TestWorker

```kotlin
return Result.success(workDataOf("key" to "testworker"))
```

ConstraintWorker:

```kotlin
 return Result.success(workDataOf("key" to "constraintWorker"))
```

#### 3.任务链的状态

创建OneTimeWorkRequest任务链需要记住的几件事:

- 当所有其父OneTimeWorkRequests成功（即返回Result.success()）时，依赖OneTimeWorkRequests才从`BLOCKED`状态转化为`ENQUEUED`状态.
- 当父OneTimeWorkRequest失败时(即返回Result.failure()),后面所有依赖的OneTimeWorkRequests都会被标记成`FAILED`状态
- 当父OneTimeWorkRequest被取消(即为canceled状态),后面所有依赖的OneTimeWorkRequests都会被标记成`CANCELED`状态

### 四.取消和停止任务

- `WorkManager.getInstance(Context).cancelWorkById(workRequest.id)`&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;通过id来取消任务
- `WorkManager.getInstance(Context).cancelAllWorkByTag(String)`&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;通过tag来取消任务
- `WorkManager.getInstance(Context).cancelUniqueWork(String)`&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;通过unique name来取消任务
- `WorkManager.getInstance(Context).cancelAllWork()`&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;取消全部任务

以下情况中你正在运行的worker将被WorkManager停止:

- 你自己明确取消(例如调用cancelWorkById)
- 在唯一任务的情况下，您使用REPLACE的ExistingWorkPolicy明确地添加了一个新的WorkRequest。旧的WorkRequest立即被视为已终止。
- 任务的constraints(约束)将不再被满足
- 系统指示您的应用由于某种原因停止任务。如果超过10分钟的执行截止日期，就会发生这种情况。这项任务计划在稍后重试。


这些情况下你会接受到[ListenableWorker.onStopped()](https://developer.android.google.cn/reference/androidx/work/ListenableWorker.html#onStopped()),调用[ ListenableWorker.isStopped()](https://developer.android.google.cn/reference/androidx/work/ListenableWorker.html#isStopped())来判断worker是否停止.

### 五.定期任务(最短间隔时间15分钟)
你的应用有些时候会需要定期的执行一些任务,例如:定期备份数据,定期上传应用日志等等.

使用[PeriodicWorkRequest](https://developer.android.google.cn/reference/androidx/work/PeriodicWorkRequest)用来执行定期的任务.

注意:`PeriodicWorkRequest`最短的间隔时间是**15分钟**

`PeriodicWorkRequest`不能添加到工作链中,如果需要工作链,请使用`OneTimeWorkRequest`.关于`PeriodicWorkRequest`的使用,请看下面的例子.

```kotlin
var constraints = Constraints.Builder()
                    .setRequiresCharging(true)
                    .build()

//过了间隔时间后,也会观察是否满足充电的约束条件,是的话才会执行(最小间隔时间必须大于等于15分钟)
var request = PeriodicWorkRequestBuilder<TestWorker>(5,TimeUnit.SECONDS)
    .setConstraints(constraints)
    .build()

WorkManager.getInstance(this).enqueue(request)
```

### 六.unique work(独特任务)

和`tag`不一样unique name是仅和一个work 或 work chain 对应.  
和`id`不同的是unique name是开发者可定义的.

创建一个unique work 可以通过  [WorkManager.getInstance(Context).enqueueUniqueWork(String, ExistingWorkPolicy, OneTimeWorkRequest)](https://developer.android.google.cn/reference/androidx/work/WorkManager.html#enqueueUniqueWork(java.lang.String,%20androidx.work.ExistingWorkPolicy,%20androidx.work.OneTimeWorkRequest)) or [WorkManager.getInstance(Context).enqueueUniquePeriodicWork(String, ExistingPeriodicWorkPolicy, PeriodicWorkRequest)](https://developer.android.google.cn/reference/androidx/work/WorkManager.html#enqueueUniquePeriodicWork(java.lang.String,%20androidx.work.ExistingPeriodicWorkPolicy,%20androidx.work.PeriodicWorkRequest)).

第一个参数是unique name 用来标识WorkRequest的秘钥

第二个参数是`冲突解决策略`(如果已经存在了该unique name的work或work chain):

- [REPLACE](https://developer.android.google.cn/reference/androidx/work/ExistingWorkPolicy.html#REPLACE)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;关闭之前存在的work或work chain,使用新的work或work chain来替代
- [KEEP](https://developer.android.google.cn/reference/androidx/work/ExistingWorkPolicy.html#KEEP)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;保留之前存在的work或work chain,忽略新的work或work chain
- [APPEND](https://developer.android.google.cn/reference/androidx/work/ExistingWorkPolicy.html#APPEND)&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;在已经存在的work或work request完成后再执行新的work或work chain,您不能将`ExistingPeriodicWorkPolicy.APPEND`与`PeriodicWorkRequests`一起使用。

第三个参数是 `WorkRequest`

如果你需要创建 unique work chain 使用方法 

```kotlin
WorkManager.getInstance(Context).beginUniqueWork(String, ExistingWorkPolicy, OneTimeWorkRequest)
```

示例代码:

```kotlin
 btn_unique_work -> {
    var request = PeriodicWorkRequestBuilder<TestWorker>(15,TimeUnit.MINUTES).build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork("test",ExistingPeriodicWorkPolicy.KEEP,request)
}

btn_unique_work_chain -> {
    var request = OneTimeWorkRequestBuilder<TestWorker>()
        .build()
    var requestConstraint = OneTimeWorkRequestBuilder<ConstraintWorker>()
        .build()
    var requestInputOutputWorker = OneTimeWorkRequestBuilder<InputOutputWorker>()
        .setInputMerger(ArrayCreatingInputMerger::class.java)
        .build()

    WorkManager.getInstance(this)
        .beginUniqueWork("test",ExistingWorkPolicy.REPLACE,arrayListOf(request,requestConstraint))
        .then(requestInputOutputWorker)
        .enqueue()

}
```

### 七.自定义WorkManager的配置和初始化

#### 1.默认初始化

默认初始化适用于大多数app,当应用打开时,WorkManager使用自定义的`ContentProvider`初始化自己,使用默认的[Configuration](https://developer.android.google.cn/reference/androidx/work/Configuration),默认初始化是自动加载,除非你 disable 它.

#### 2.自定义初始化

第一步先禁用默认初始化,在`AndroidManifest.xml`中使用合并规则:`tools:node="remove"`:
```xml
<provider
    android:name="androidx.work.impl.WorkManagerInitializer"
    android:authorities="${applicationId}.workmanager-init"
    tools:node="remove" />
```

第二步自定义自己的初始化:

确保初始化在Application.onCreate（）或ContentProvider.onCreate（）中运行。:

```kotlin
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 自定义 WorkManager的初始化
 *  @author: East
 *  @date: 2019-08-20
 * |---------------------------------------------------------------------------------------------------------------|
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        //提供自定义的配置
        var config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setExecutor(Executors.newSingleThreadExecutor())
            .build()

        //初始化WorkManager
        WorkManager.initialize(this,config)

    }
}
```

#### 3.按需初始化(WorkManager 2.1.0-alpha01及更高版本中提供了按需初始化。)

为WorkManager提供自定义初始化的最灵活方法是使用按需初始化。按需初始化允许你仅在需要该组件时初始化WorkManager,加快应用启动速度.

第一步:编辑AndroidManifest.xml并禁用默认初始化程序。

第二步:让你的Application类实现Configuration.Provider接口，并提供你自己的Configuration.Provider.getWorkManagerConfiguration（）实现

第三步:当您需要使用WorkManager时，请调用方法WorkManager.getInstance（Context）。WorkManager调用应用程序的自定义getWorkManagerConfiguration()方法来发现其配置.(您不需要自己调用WorkManager.initialize()。）

```kotlin
class App : Application(),Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
    }
    
    //使用按需初始化workManager时 需要实现接口
    override fun getWorkManagerConfiguration(): Configuration {
        //提供自定义的配置
        var config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setExecutor(Executors.newSingleThreadExecutor())
            .build()
        return config
    }
}
```

### 八.在WorkManager中进行线程化

WorkManager提供了4种不同的Work:

#### 1.Worker

`worker`的`dowork()`方法自动运行在WorkManager的Configuration中定义的Executor中的后台线程中,请注意，Worker.doWork()是一个同步调用 - 您需要以阻塞方式完成整个后台工作，并在方法退出时完成它。如果在doWork()中调用异步API并返回Result，则回调可能无法正常运行。要解决这类问题请使用[ListenableWorker](https://developer.android.google.cn/reference/androidx/work/ListenableWorker.html)

#### 2.CoroutineWorker
对于Kotlin用户，WorkManager为协同程序提供一流的支持。`CoroutineWorker.doWork()`此代码不在Configuration中指定的Executor上运行,相反，它默认为Dispatchers.Default。您可以通过提供自己的CoroutineContext来自定义它.

```kotlin
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  适合kotlin用户的协同Worker  CoroutineWorker
 *  @author: East
 *  @date: 2019-08-20
 * |---------------------------------------------------------------------------------------------------------------|
 */
class KotlinWorker(var context:Context, params:WorkerParameters) : CoroutineWorker(context,params) {


    //已经过时请在doWork方法中使用withContext来指定任务执行时运行的线程
//    override val coroutineContext: CoroutineDispatcher
//        get() = Dispatchers.IO

    override suspend fun doWork(): Result {
        withContext(Dispatchers.Main){
            Toast.makeText(context,"证明CoroutineWorker可通过CoroutineContext来定义执行线程",Toast.LENGTH_SHORT).show()
        }
        return Result.success()
    }
}
```

#### 3.RxWorker

我们提供WorkManager和RxJava2之间的互操作性,首先继承 `RxWorker`,然后重写RxWorker.createWork()方法以返回指示执行结果的Single<Result>，

```kotlin
public class RxDownloadWorker extends RxWorker {

    public RxDownloadWorker(Context context, WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Single<Result> createWork() {
        return Observable.range(0, 100)
            .flatMap { download("https://www.google.com") }
            .toList()
            .map { Result.success() };
    }
}
```

请注意，在主线程上调用RxWorker.createWork（），但默认情况下在后台线程上订阅返回值。您可以覆盖RxWorker.getBackgroundScheduler（）以更改订阅线程。

停止RxWorker会正确处理Observers，因此您无需以任何特殊方式处理停止工作。

#### 4.ListenableWorker

[ListenableWorker](https://developer.android.google.cn/reference/androidx/work/ListenableWorker.html) 是`Worker`，`CoroutineWorker`和`RxWorker`的父类.

抽象方法`ListenableWorker.startWork()`返回`Result`的`ListenableFuture`。

如果你想基于异步回调执行一些工作，你会做这样的事情：

```
dependencies {
    def futures_version = "1.0.0-rc01"
    implementation "androidx.concurrent:concurrent-futures:$futures_version"
}
```
```kotlin
/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: worker中执行异步操作
 *  @author: East
 *  @date: 2019-08-20
 * |---------------------------------------------------------------------------------------------------------------|
 */
class AsynchronousWorker(context: Context,params:WorkerParameters) : ListenableWorker(context,params) {



    override fun startWork(): ListenableFuture<Result> {
//        val future = ResolvableFuture.create<Result>()
//        future.set(doWork())
//        return future

        return CallbackToFutureAdapter.getFuture<Result> { completer ->
            //做异步操作
        }

    }


    fun doWork() : Result{
        return Result.success()
    }
}
```































	   
	   
	

