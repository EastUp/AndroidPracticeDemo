# RxJava2

## Rxjava2入门和实战

### Github地址

- https://github.com/ReactiveX/Rxjava

### 预习资料

- https://www.jianshu.com/p/a51aa39c30ab

### 什么是RxJava

- 一个可观测的序列来组成异步的、基于事件的程序的库。（简单来说：它就是一个实现异步操作的库）

### RxJava 好在哪?

- RxJava 其实就是提供一套异步编程的 API，这套 API 是基于观察者模式的，而且是链式调用的，所以使用 RxJava 编写的代码的逻辑会非常简洁。

### 官方支持：更新至支持 2020年12月31日

### 适用场景

- 数据库的读写、大图片的载入、文件压缩/解压等各种需要放在后台工作的耗时操作，都可以用 RxJava 来实现

### 三个基本的元素

- 被观察者（Observable）
- 观察者（Observer

	- onSubscribe()

		- 订阅观察者的时候被调用

	- onNext()

		- 发送该事件时，观察者会回调 onNext() 方法

	- onError()

		- 发送该事件时，观察者会回调 onError() 方法，当发送该事件之后，其他事件将不会继续发送

	- onComplete()

		- 发送该事件时，观察者会回调 onComplete() 方法，当发送该事件之后，其他事件将不会继续发送

- 订阅（subscribe）

### 五种被观察者

- Observable

	- Observable即被观察者，决定什么时候触发事件以及触发怎样的事件

- Flowable

	- Flowable可以看成是Observable的实现，只是它支持背压

- Single

	- 只有onSuccess可onError事件，只能用onSuccess发射一个数据或一个错误通知，之后再发射数据也不会做任何处理，直接忽略

- Completable

	- 只有onComplete和onError事件，不发射数据，没有map，flatMap操作符。常常结合andThen操作符使用

- Maybe

	- 没有onNext方法，同样需要onSuccess发射数据，且只能发射0或1个数据，多发也不再处理

- 五种被观察者可通过toObservable，toFlowable,toSingle,toCompletable,toMaybe相互转换
- Single、Completable、Maybe——简化版的Observable

### 操作符

- 创建操作符

	- 创建被观察者的各种操作符
	- create()

		- 创建一个被观察者

	- just()

		- 创建一个被观察者，并发送事件，发送的事件不可以超过10个以上

	- From 操作符

		- fromArray()

			- 这个方法和 just() 类似，只不过 fromArray 可以传入多于10个的变量，并且可以传入一个数组

		- fromCallable()

			- 这里的 Callable 是 java.util.concurrent 中的 Callable，Callable 和 Runnable 的用法基本一致，只是它会返回一个结果值，这个结果值就是发给观察者的

		- fromFuture()

			- 参数中的 Future 是 java.util.concurrent 中的 Future，Future 的作用是增加了 cancel() 等方法操作 Callable，它可以通过 get() 方法来获取 Callable 返回的值

		- fromIterable()

			- 直接发送一个 List 集合数据给观察者

	- defer()

		- 这个方法的作用就是直到被观察者被订阅后才会创建被观察者。

	- timer()

		- 当到指定时间后就会发送一个 0L 的值给观察者。

	- interval()

		- 每隔一段时间就会发送一个事件，这个事件是从0开始，不断增1的数字。

	- intervalRange()

		- 可以指定发送事件的开始值和数量，其他与 interval() 的功能一样。

	- range()

		- 同时发送一定范围的事件序列。

	- rangeLong()

		- 作用与 range() 一样，只是数据类型为 Long

	- empty() & never() & error()

		- empty() ： 直接发送 onComplete() 事件
		- never()：不发送任何事件
		- error()：发送 onError() 事件

- 转换操作符

	- map()

		- map 可以将被观察者发送的数据类型转变成其他的类型

	- flatMap()

		- 这个方法可以将事件序列中的元素进行整合加工，返回一个新的被观察者。

			- flatMap() 其实与 map() 类似，但是 flatMap() 返回的是一个 Observerable

	- concatMap()

		- concatMap() 和 flatMap() 基本上是一样的，只不过 concatMap() 转发出来的事件是有序的，而 flatMap() 是无序的

	- buffer()

		- 从需要发送的事件当中获取一定数量的事件，并将这些事件放到缓冲区当中一并发出

	- groupBy()

		- 将发送的数据进行分组，每个分组都会返回一个被观察者

	- scan()

		- 将数据以一定的逻辑聚合起来

	- window()

		- 发送指定数量的事件时，就将这些事件分为一组。window 中的 count 的参数就是代表指定的数量，例如将 count 指定为2，那么每发2个数据就会将这2个数据分成一组。

- 组合操作符

	- concat()

		- 可以将多个观察者组合在一起，然后按照之前发送顺序发送事件。需要注意的是，concat() 最多只可以发送4个事件。

	- concatArray()

		- 与 concat() 作用一样，不过 concatArray() 可以发送多于 4 个被观察者。

	- merge()

		- 这个方法月 concat() 作用基本一样，知识 concat() 是串行发送事件，而 merge() 并行发送事件。

	- concatArrayDelayError() & mergeArrayDelayError()

		- 在 concatArray() 和 mergeArray() 两个方法当中，如果其中有一个被观察者发送了一个 Error 事件，那么就会停止发送事件，如果你想 onError() 事件延迟到所有被观察者都发送完事件后再执行的话，就可以使用  concatArrayDelayError() 和 mergeArrayDelayError()

	- zip()

		- 会将多个被观察者合并，根据各个被观察者发送事件的顺序一个个结合起来，最终发送的事件数量会与源 Observable 中最少事件的数量一样。

	- combineLatest() & combineLatestDelayError()

		- combineLatest() 的作用与 zip() 类似，但是 combineLatest() 发送事件的序列是与发送的时间线有关的，当 combineLatest() 中所有的 Observable 都发送了事件，只要其中有一个 Observable 发送事件，这个事件就会和其他 Observable 最近发送的事件结合起来发送

	- reduce()

		- 与 scan() 操作符的作用也是将发送数据以一定逻辑聚合起来，这两个的区别在于 scan() 每处理一次数据就会将事件发送给观察者，而 reduce() 会将所有数据聚合在一起才会发送事件给观察者。

	- collect()

		- 将数据收集到数据结构当中

	- startWith() & startWithArray()

		- 在发送事件之前追加事件，startWith() 追加一个事件，startWithArray() 可以追加多个事件。追加的事件会先发出。

	- count()

		- 返回被观察者发送事件的数量。

- 功能操作符

	- delay()

		- 延迟一段时间发送事件。

	- doOnEach()

		- Observable 每发送一件事件之前都会先回调这个方法。

	- doOnNext()

		- Observable 每发送 onNext() 之前都会先回调这个方法。

	- doAfterNext()

		- Observable 每发送 onNext() 之后都会回调这个方法。

	- doOnComplete()

		- Observable 每发送 onComplete() 之前都会回调这个方法。

	- doOnError()

		- Observable 每发送 onError() 之前都会回调这个方法。

	- doOnSubscribe()

		- Observable 每发送 onSubscribe() 之前都会回调这个方法。

			- 子主题 1

	- doOnDispose()

		- 当调用 Disposable 的 dispose() 之后回调该方法

	- doOnLifecycle()

		- 在回调 onSubscribe 之前回调该方法的第一个参数的回调方法，可以使用该回调方法决定是否取消订阅

	- doOnTerminate() & doAfterTerminate()

		- doOnTerminate 是在 onError 或者 onComplete 发送之前回调，而 doAfterTerminate 则是 onError 或者 onComplete 发送之后回调

	-  doFinally()

		- 在所有事件发送完毕之后回调该方法。

	- onErrorReturn()

		- 当接受到一个 onError() 事件之后回调，返回的值会回调 onNext() 方法，并正常结束该事件序列

	- onErrorResumeNext()

		- 当接收到 onError() 事件时，返回一个新的 Observable，并正常结束事件序列

	- onExceptionResumeNext()

		- 与 onErrorResumeNext() 作用基本一致，但是这个方法只能捕捉 Exception。

	- retry()

		- 如果出现错误事件，则会重新发送所有事件序列。times 是代表重新发的次数

	- retryUntil()

		- 出现错误事件之后，可以通过此方法判断是否继续发送事件。

	- retryWhen()

		- 当被观察者接收到异常或者错误事件时会回调该方法，这个方法会返回一个新的被观察者。如果返回的被观察者发送 Error 事件则之前的被观察者不会继续发送事件，如果发送正常事件则之前的被观察者会继续不断重试发送事件

	- repeat()

		- 重复发送被观察者的事件，times 为发送次数

	- repeatWhen()

		- 这个方法可以会返回一个新的被观察者设定一定逻辑来决定是否重复发送事件。

	- subscribeOn()

		- 指定被观察者的线程，要注意的时，如果多次调用此方法，只有第一次有效。

	- observeOn()

		- 指定观察者的线程，每指定一次就会生效一次。

- 过滤操作符

	- filter()

		- 通过一定逻辑来过滤被观察者发送的事件，如果返回 true 则会发送事件，否则不会发送

	- ofType()

		- 可以过滤不符合该类型事件

	- skip()

		- 跳过正序某些事件，count 代表跳过事件的数量

	- distinct()

		- 过滤事件序列中的重复事件。

	- distinctUntilChanged()

		- 过滤掉连续重复的事件

	- take()

		- 控制观察者接收的事件的数量。

	- debounce()

		- 如果两件事件发送的时间间隔小于设定的时间间隔则前一件事件就不会发送给观察者。

	- firstElement() && lastElement()

		- firstElement() 取事件序列的第一个元素，lastElement() 取事件序列的最后一个元素。

	- elementAt() & elementAtOrError()

		- elementAt() 可以指定取出事件序列中事件，但是输入的 index 超出事件序列的总数的话就不会出现任何结果。这种情况下，你想发出异常信息的话就用 elementAtOrError() 。

- 条件操作符

	- all()

		- 判断事件序列是否全部满足某个事件，如果都满足则返回 true，反之则返回 false。

	- takeWhile()

		- 可以设置条件，当某个数据满足条件时就会发送该数据，反之则不发送

	- skipWhile()

		- 可以设置条件，当某个数据满足条件时不发送该数据，反之则发送。

	- takeUntil()

		- 可以设置条件，当事件满足此条件时，下一次的事件就不会被发送了。

	- skipUntil()

		- 当 skipUntil() 中的 Observable 发送事件了，原来的 Observable 才会发送事件给观察者。

	- sequenceEqual()

		- 判断两个 Observable 发送的事件是否相同。

	- contains()

		- 判断事件序列中是否含有某个元素，如果有则返回 true，如果没有则返回 false。

	- isEmpty()

		- 判断事件序列是否为空。

	- amb()

		- amb() 要传入一个 Observable 集合，但是只会发送最先发送事件的 Observable 中的事件，其余 Observable 将会被丢弃

	- defaultIfEmpty()

		- 如果观察者只发送一个 onComplete() 事件，则可以利用这个方法发送一个值

### 观察者模式

- 定义：定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖于它的对象都会得到通知并被自动更新
- 作用是：解耦

	- UI层与具体的业务逻辑解耦

## Rxjava2线程切换 与RxJava2背压原理

### 背压

- 出现原因：

	- 当上下游在不同的线程中，通过Observable发射，处理，响应数据流时，如果上游发射数据的速度快于下游接收处理数据的速度，这样对于那些没来得及处理的数据就会造成积压，这些数据既不会丢失，也不会被垃圾回收机制回收，而是存放在一个异步缓存池中，如果缓存池中的数据一直得不到处理，越积越多，最后就会造成内存溢出，这便是响应式编程中的背压（backpressure）问题

## 高阶泛型详解与手写RxJava2

## 手写Rxjava2核心架构之变换操作符与线程操作符