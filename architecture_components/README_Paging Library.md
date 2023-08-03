
[**Paging Library**](https://developer.android.com/topic/libraries/architecture/paging)

# 分页库概述
分頁庫可幫助您一次加載和顯示小塊數據。按需加載部分數據可減少網絡帶寬和系統資源的使用。

本指南提供了庫的幾個概念性示例，以及它如何工作的概述。要查看此庫如何運行的完整示例，請嘗試使用其他資源部分中的codelab和示例。[additional resources](https://developer.android.com/topic/libraries/architecture/paging#additional-resources)
### 依赖

```kotlin
dependencies {
  def paging_version = "2.1.0"

  implementation "androidx.paging:paging-runtime:$paging_version" // For Kotlin use paging-runtime-ktx

  // alternatively - without Android dependencies for testing
  testImplementation "androidx.paging:paging-common:$paging_version" // For Kotlin use paging-common-ktx

  // optional - RxJava support
  implementation "androidx.paging:paging-rxjava2:$paging_version" // For Kotlin use paging-rxjava2-ktxac
  
   implementation "androidx.paging:paging-runtime-ktx:2.1.0-rc01" // For Kotlin use paging-runtime-ktx
}
```

For information on using Kotlin extensions, see [ktx documentation](https://developer.android.com/kotlin/ktx#kotlin).

## Library architecture

本節描述並顯示了分頁庫的主要組件。
### [PagedList](https://developer.android.com/topic/libraries/architecture/paging#paged-list)
分頁庫的關鍵組件是PagedList類，它加載應用程序數據或頁面的塊。由於需要更多數據，因此將其分頁到現有的PagedList對像中.如果任何加載的數據發生更改，則會從LiveData或基於RxJava2的對象向可觀察數據持有者發出新的PagedList實例。生成PagedList對象,您的應用程序的UI顯示其內容，同時尊重您的UI控制器的生命週期。

以下代碼段顯示瞭如何使用PagedList對象的LiveData持有者配置應用程序的視圖模型以加載和顯示數據：  
kotlin:

``` kotlin
class ConcertViewModel(concertDao: ConcertDao) : ViewModel() {
    val concertList: LiveData<PagedList<Concert>> =
            concertDao.concertsByDate().toLiveData(pageSize = 50)
}
```
java:

``` java
public class ConcertViewModel extends ViewModel {
    private ConcertDao concertDao;
    public final LiveData<PagedList<Concert>> concertList;

    // Creates a PagedList object with 50 items per page.
    public ConcertViewModel(ConcertDao concertDao) {
        this.concertDao = concertDao;
        concertList = new LivePagedListBuilder<>(
                concertDao.concertsByDate(), 50).build();
    }
}
```

### [Data](https://developer.android.com/topic/libraries/architecture/paging#data)

PagedList的每個實例都從其相應的DataSource對象加載應用程序數據的最新快照。數據從應用程序的後端或數據庫流入PagedList對象。

以下示例使用Room持久性庫來組織應用程序的數據，但如果要使用其他方法存儲數據，還可以提供自己的數據源工廠。

kotlin:

``` kotlin
@Dao
interface ConcertDao {
    // The Int type parameter tells Room to use a PositionalDataSource object.
    @Query("SELECT * FROM concerts ORDER BY date DESC")
    fun concertsByDate(): DataSource.Factory<Int, Concert>
}
```
java:

``` java
@Dao
public interface ConcertDao {
    // The Integer type parameter tells Room to use a
    // PositionalDataSource object.
    @Query("SELECT * FROM concerts ORDER BY date DESC")
    DataSource.Factory<Integer, Concert> concertsByDate();
}
```

### [UI](https://developer.android.com/topic/libraries/architecture/paging#ui)
PagedList類使用PagedListAdapter將項目加載到RecyclerView中。這些類一起工作以在加載內容時獲取和顯示內容，預取視圖內容並動画內容更改。

### [支持不同的數據架構](https://developer.android.com/topic/libraries/architecture/paging#support-different-data-arch)

分頁庫支持以下數據體系結構:

- 僅從後端服務器提供。
- 僅存儲在設備上的數據庫中。
- 使用設備上數據庫作為緩存的其他源的組合。

圖1顯示了每種架構方案中數據的流動方式。對於僅限網絡或僅限數據庫的解決方案，數據直接流向應用程序的UI模型。如果您使用的是組合方法，則數據會從後端服務器流入設備上的數據庫，然後流入應用程序的UI模型。每隔一段時間，每個數據流的端點就會耗盡要加載的數據，此時它會從提供數據的組件請求更多數據.例如，當設備上數據庫用完數據時，它會從服務器請求更多數據。![图1]()

本節的其餘部分提供了配置每個數據流用例的建議。

### [Network only](https://developer.android.com/topic/libraries/architecture/paging#network-only-data-arch)

要显示来自后端服务器的数据，请使用[Retrofit API](https://square.github.io/retrofit/)的同步版本将信息加载到您自己的[自定义DataSource对象中](https://developer.android.com/topic/libraries/architecture/paging/data#custom-data-source)。

<table><tr><td bgcolor=#bfe1f1>注意：Paging Library的DataSource对象不提供任何错误处理，因为不同的应用程序以不同的方式处理和显示错误UI。
如果发生错误，请遵循结果回调，稍后重试该请求。有关此行为的示例，请参阅PagingWithNetwork示例。</td></tr></table>


### [Database only](https://developer.android.com/topic/libraries/architecture/paging#database-only-data-arch)

设置RecyclerView以观察本地存储，最好使用[Room persistence library](https://developer.android.com/topic/libraries/architecture/room)。这样，无论何时在应用程序的数据库中插入或修改数据，这些更改都会自动反映在显示此数据的`RecyclerView`中。

### [Network and database](https://developer.android.com/topic/libraries/architecture/paging#network-with-database-data-arch)

在开始观察数据库之后，您可以使用[PagedList.BoundaryCallback](https://developer.android.com/reference/androidx/paging/PagedList.BoundaryCallback)监听数据库何时没有数据。然后，您可以从网络中获取更多项目并将其插入数据库。如果您的UI正在观察数据库，那就是您需要做的。

### [处理网络错误](https://developer.android.com/topic/libraries/architecture/paging#handle-network-errors)

当使用网络获取或分页您正在使用分页库显示的数据时，重要的是不要将网络视为“可用”或“不可用”，因为许多连接是间歇性的或片状的：

- 特定服务器可能无法响应网络请求。
- 设备可能连接到缓慢或弱的网络。

相反，您的应用应检查每个失败请求，并在网络不可用的情况下尽可能优雅地恢复。例如，如果数据刷新步骤不起作用,您可以提供“重试”按钮供用户选择。如果在数据分页步骤期间发生错误，则最好自动重试分页请求。

## [Update your existing app](https://developer.android.com/topic/libraries/architecture/paging#update-existing-app)

如果您的应用已经使用了数据库或后端源中的数据，则可以直接升级到分页库提供的功能。本节介绍如何升级具有通用现有设计的应用程序。

### [定制分页解决方案](https://developer.android.com/topic/libraries/architecture/paging#custom-paging-solutions)

如果您使用自定义功能从应用程序的数据源加载小的数据子集，则可以将此逻辑替换为PagedList类中的逻辑.PagedList的实例提供与公共数据源的内置连接。这些实例还为您可能包含在应用程序UI中的RecyclerView对象提供适配器。

### [使用列表而不是页面加载数据](https://developer.android.com/topic/libraries/architecture/paging#data-loaded-lists)

如果使用内存列表作为UI适配器的后备数据结构，如果列表中的项目数可能变大，请考虑使用PagedList类观察数据更新.PagedList的实例可以使用LiveData <PagedList>或Observable <List>将数据更新传递到应用程序的UI，从而最大限度地减少加载时间和内存使用量。更好的是，在应用程序中用PagedList对象替换List对象不需要对应用程序的UI结构或数据更新逻辑进行任何更改。

### [使用CursorAdapter将数据光标与列表视图相关联](https://developer.android.com/topic/libraries/architecture/paging#associate-cursor-list)

您的应用程序可能使用CursorAdapter将Cursor中的数据与ListView相关联。在这种情况下，您通常需要从ListView迁移到RecyclerView作为应用程序的列表UI容器，然后将Cursor组件替换为Room或PositionalDataSource，具体取决于Cursor实例是否访问SQLite数据库。

在某些情况下，例如使用Spinner实例时，只提供适配器本身。然后，库将获取加载到该适配器中的数据并为您显示数据。在这些情况下，将适配器数据的类型更改为LiveData <PagedList>，然后在尝试让库类在UI中对这些项进行inflate之前，将此列表包装在ArrayAdapter对象中。

### [使用AsyncListUtil异步加载内容](https://developer.android.com/topic/libraries/architecture/paging#load-content-async)

如果您使用[AsyncListUtil](https://developer.android.com/reference/androidx/recyclerview/widget/AsyncListUtil)对象异步加载和显示信息组，则分页库可让您更轻松地加载数据：

- **您的数据不需要是位置的。**分页库允许您使用网络提供的密钥直接从后端加载数据。
- **您的数据可能非常大。**使用分页库，您可以将数据加载到页面中，直到没有剩余数据。
- **您可以更轻松地观察数据。** Paging库可以显示应用程序的ViewModel在可观察数据结构中保存的数据。

## [数据库示例](https://developer.android.com/topic/libraries/architecture/paging#database-examples)

以下代码片段显示了使所有部分协同工作的几种可能方法。

### [使用LiveData观察分页数据](https://developer.android.google.cn/topic/libraries/architecture/paging#ex-observe-livedata)

以下代码段显示了一起工作的所有部分。随着在数据库中添加，删除或更改Concert事件，RecyclerView中的内容将自动且高效地更新：

kotlin:

``` kotlin
	@Dao
	interface ConcertDao {
	    // The Int type parameter tells Room to use a PositionalDataSource
	    // object, with position-based loading under the hood.
	    @Query("SELECT * FROM concerts ORDER BY date DESC")
	    fun concertsByDate(): DataSource.Factory<Int, Concert>
	}
	
	class ConcertViewModel(concertDao: ConcertDao) : ViewModel() {
	    val concertList: LiveData<PagedList<Concert>> =
	            concertDao.concertsByDate().toLiveData(pageSize = 50)
	}
	
	class ConcertActivity : AppCompatActivity() {
	    public override fun onCreate(savedInstanceState: Bundle?) {
	        super.onCreate(savedInstanceState)
	        val viewModel = ViewModelProviders.of(this)
	                .get<ConcertViewModel>()
	        val recyclerView = findViewById(R.id.concert_list)
	        val adapter = ConcertAdapter()
	        viewModel.livePagedList.observe(this, PagedList(adapter::submitList))
	        recyclerView.setAdapter(adapter)
	    }
	}
	
	class ConcertAdapter() :
	        PagedListAdapter<Concert, ConcertViewHolder>(DIFF_CALLBACK) {
	    fun onBindViewHolder(holder: ConcertViewHolder, position: Int) {
	        val concert: Concert? = getItem(position)
	
	        // Note that "concert" is a placeholder if it's null.
	        holder.bindTo(concert)
	    }
	
	    companion object {
	        private val DIFF_CALLBACK = object :
	                DiffUtil.ItemCallback<Concert>() {
	            // Concert details may have changed if reloaded from the database,
	            // but ID is fixed.
	            override fun areItemsTheSame(oldConcert: Concert,
	                    newConcert: Concert) = oldConcert.id == newConcert.id
	
	            override fun areContentsTheSame(oldConcert: Concert,
	                    newConcert: Concert) = oldConcert == newConcert
	        }
	    }
	}
```

java : 

``` java
	@Dao
	public interface ConcertDao {
	    // The Integer type parameter tells Room to use a PositionalDataSource
	    // object, with position-based loading under the hood.
	    @Query("SELECT * FROM concerts ORDER BY date DESC")
	    DataSource.Factory<Integer, Concert> concertsByDate();
	}
	
	public class ConcertViewModel extends ViewModel {
	    private ConcertDao concertDao;
	    public final LiveData<PagedList<Concert>> concertList;
	
	    public ConcertViewModel(ConcertDao concertDao) {
	        this.concertDao = concertDao;
	        concertList = new LivePagedListBuilder<>(
	            concertDao.concertsByDate(), /* page size */ 50).build();
	    }
	}
	
	public class ConcertActivity extends AppCompatActivity {
	    @Override
	    public void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        ConcertViewModel viewModel =
	                ViewModelProviders.of(this).get(ConcertViewModel.class);
	        RecyclerView recyclerView = findViewById(R.id.concert_list);
	        ConcertAdapter adapter = new ConcertAdapter();
	        viewModel.concertList.observe(this, adapter::submitList);
	        recyclerView.setAdapter(adapter);
	    }
	}
	
	public class ConcertAdapter
	        extends PagedListAdapter<Concert, ConcertViewHolder> {
	    protected ConcertAdapter() {
	        super(DIFF_CALLBACK);
	    }
	
	    @Override
	    public void onBindViewHolder(@NonNull ConcertViewHolder holder,
	            int position) {
	        Concert concert = getItem(position);
	        if (concert != null) {
	            holder.bindTo(concert);
	        } else {
	            // Null defines a placeholder item - PagedListAdapter automatically
	            // invalidates this row when the actual object is loaded from the
	            // database.
	            holder.clear();
	        }
	    }
	
	    private static DiffUtil.ItemCallback<Concert> DIFF_CALLBACK =
	            new DiffUtil.ItemCallback<Concert>() {
	        // Concert details may have changed if reloaded from the database,
	        // but ID is fixed.
	        @Override
	        public boolean areItemsTheSame(Concert oldConcert, Concert newConcert) {
	            return oldConcert.getId() == newConcert.getId();
	        }
	
	        @Override
	        public boolean areContentsTheSame(Concert oldConcert,
	                Concert newConcert) {
	            return oldConcert.equals(newConcert);
	        }
	    };
	}
```

### [使用RxJava2观察分页数据](https://developer.android.google.cn/topic/libraries/architecture/paging#ex-observe-rxjava2)

如果您更喜欢使用[RxJava2](https://github.com/ReactiveX/RxJava)而不是[LiveData](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData.html)，则可以创建一个Observable或Flowable对象：

kotlin:

```kotlin
class ConcertViewModel(concertDao: ConcertDao) : ViewModel() {
    val concertList: Observable<PagedList<Concert>> =
            concertDao.concertsByDate().toObservable(pageSize = 50)
}
```

java:

```java
public class ConcertViewModel extends ViewModel {
    private ConcertDao concertDao;
    public final Observable<PagedList<Concert>> concertList;

    public ConcertViewModel(ConcertDao concertDao) {
        this.concertDao = concertDao;

        concertList = new RxPagedListBuilder<>(
                concertDao.concertsByDate(), /* page size */ 50)
                        .buildObservable();
    }
}
```

然后，您可以使用以下代码段中的代码开始和停止观察数据：

kotlin:

``` kotlin
	class ConcertActivity : AppCompatActivity() {
	    private val adapter: ConcertAdapter()
	    private lateinit var viewModel: ConcertViewModel
	
	    private val disposable = CompositeDisposable()
	
	    public override fun onCreate(savedInstanceState: Bundle?) {
	        super.onCreate(savedInstanceState)
	        val recyclerView = findViewById(R.id.concert_list)
	        viewModel = ViewModelProviders.of(this)
	                .get<ConcertViewModel>()
	        recyclerView.setAdapter(adapter)
	    }
	
	    override fun onStart() {
	        super.onStart()
	        disposable.add(viewModel.concertList
	                .subscribe(adapter::submitList)))
	    }
	
	    override fun onStop() {
	        super.onStop()
	        disposable.clear()
	    }
	}
```

java

``` java
	public class ConcertActivity extends AppCompatActivity {
	    private ConcertAdapter adapter = new ConcertAdapter();
	    private ConcertViewModel viewModel;
	
	    private CompositeDisposable disposable = new CompositeDisposable();
	
	    @Override
	    public void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        RecyclerView recyclerView = findViewById(R.id.concert_list);
	
	        viewModel = ViewModelProviders.of(this).get(ConcertViewModel.class);
	        recyclerView.setAdapter(adapter);
	    }
	
	    @Override
	    protected void onStart() {
	        super.onStart();
	        disposable.add(viewModel.concertList
	                .subscribe(adapter.submitList(flowableList)
	        ));
	    }
	
	    @Override
	    protected void onStop() {
	        super.onStop();
	        disposable.clear();
	    }
	}
```

对于基于RxJava2的解决方案，`ConcertDao`和`ConcertAdapter`的代码与基于LiveData的解决方案的代码相同。

# 显示分页列表

本指南以分页库概述为基础，描述了如何在应用程序UI中向用户显示信息列表，尤其是在此信息发生变化时。

## [将UI连接到View Model](https://developer.android.google.cn/topic/libraries/architecture/paging/ui#connect-ui-viewmodel)

您可以将LiveData <PagedList>的实例连接到PagedListAdapter，如以下代码段所示：

kotlin:

``` kotlin
private val adapter = ConcertAdapter()
private lateinit var viewModel: ConcertViewModel

override fun onCreate(savedInstanceState: Bundle?) {
    viewModel = ViewModelProviders.of(this).get(ConcertViewModel::class.java)
    viewModel.concerts.observe(this, Observer { adapter.submitList(it) })
}
```

java:

```java
public class ConcertActivity extends AppCompatActivity {
    private ConcertAdapter adapter = new ConcertAdapter();
    private ConcertViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ConcertViewModel.class);
        viewModel.concertList.observe(this, adapter::submitList);
    }
}
```

当DataSource提供PagedList的新实例时，活动会将这些对象发送到适配器。PagedListAdapter实现定义了如何计算更新，并自动处理分页和列表差异。因此，您的ViewHolder只需要绑定到特定提供的项目：

kotlin:

```kotlin
class ConcertAdapter() :
        PagedListAdapter<Concert, ConcertViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: ConcertViewHolder, position: Int) {
        val concert: Concert? = getItem(position)

        // Note that "concert" is a placeholder if it's null.
        holder.bindTo(concert)
    }

    companion object {
        private val DIFF_CALLBACK = ... // See Implement the diffing callback section.
    }
}
```

java:

```java
public class ConcertAdapter
        extends PagedListAdapter<Concert, ConcertViewHolder> {
    protected ConcertAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcertViewHolder holder,
            int position) {
        Concert concert = getItem(position);

        // Note that "concert" can be null if it's a placeholder.
        holder.bindTo(concert);
    }

    private static DiffUtil.ItemCallback<Concert> DIFF_CALLBACK
            = ... // See Implement the diffing callback section.
}
```

PagedListAdapter使用PagedList.Callback对象处理页面加载事件。当用户滚动时，PagedListAdapter调用PagedList.loadAround（）以向底层的PagedList提供关于它应该从DataSource获取哪些项的提示。

### [实现差异回调](https://developer.android.google.cn/topic/libraries/architecture/paging/ui#implement-diffing-callback)

以下示例显示了`areContentsTheSame()`的手动实现，它比较了相关的对象字段：

kotlin:

```kotlin
private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Concert>() {
    // The ID property identifies when items are the same.
    override fun areItemsTheSame(oldItem: Concert, newItem: Concert) =
            oldItem.id == newItem.id

    // If you use the "==" operator, make sure that the object implements
    // .equals(). Alternatively, write custom data comparison logic here.
    override fun areContentsTheSame(
            oldItem: Concert, newItem: Concert) = oldItem == newItem
}
```

java:

```java
private static DiffUtil.ItemCallback<Concert> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<Concert>() {

    @Override
    public boolean areItemsTheSame(Concert oldItem, Concert newItem) {
        // The ID property identifies when items are the same.
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(Concert oldItem, Concert newItem) {
        // Don't use the "==" operator here. Either implement and use .equals(),
        // or write custom data comparison logic here.
        return oldItem.equals(newItem);
    }
};
```

由于适配器包含比较项的定义，因此适配器会在加载新的PagedList对象时自动检测对这些项的更改。因此，适配器会在RecyclerView对象中触发高效的item动画。

### [使用不同的适配器类型进行区分](https://developer.android.google.cn/topic/libraries/architecture/paging/ui#diffing-different-adapter-type)

如果您选择不从PagedListAdapter继承 - 例如当您使用提供自己的适配器的库时 - 您仍然可以通过直接使用[AsyncPagedListDiffer](https://developer.android.google.cn/reference/androidx/paging/AsyncPagedListDiffer)对象来使用Paging Library适配器的diffing功能。

### [在UI中提供占位符](https://developer.android.google.cn/topic/libraries/architecture/paging/ui#provide-placeholders)

如果您希望UI在应用程序完成获取数据之前显示列表，您可以向用户显示占位符列表项。PagedList通过将列表项数据显示为null来处理这种情况，直到加载数据。

<table><tr><td bgcolor=#bfe1f1>注意：默认情况下，分页库启用此占位符行为。</td></tr></table>

占位符具有以下好处：

- **支持滚动条：**PagedList向PagedListAdapter提供列表项的数量。此信息允许适配器绘制一个滚动条，传达列表的完整大小。加载新页面时，滚动条不会跳转，因为列表不会更改大小。


- **无需加载微调器：**由于列表大小已知，因此无需提醒用户正在加载更多项目。占位符本身传达了这些信息。

但是，在添加对占位符的支持之前，请记住以下前提条件：

- **需要可数数据集：**Room持久性库中的DataSource实例可以有效地计算其项目。但是，如果您使用的是自定义本地存储解决方案或仅限网络的数据体系结构，则确定数据集中包含的项目数量可能很昂贵甚至无法实现。

- **需要适配器来考虑卸载的项目：**用于为通胀准备列表的适配器或表示机制需要处理空列表项。例如，将数据绑定到ViewHolder时，需要提供默认值来表示卸载的数据。

- **需要相同大小的项目视图：**如果列表项大小可以根据其内容（例如社交网络更新）而更改，则项之间的交叉淡化看起来不太好。我们强烈建议在这种情况下禁用占位符。

#收集分页数据

本指南以Paging Library概述为基础，讨论如何自定义应用程序的数据加载解决方案以满足应用程序的架构需求。

### [构造一个可观察的列表](https://developer.android.google.cn/topic/libraries/architecture/paging/data#construct-observable-list)

通常，您的UI代码会观察LiveData <PagedList>对象（或者，如果您使用的是RxJava2，则为Flowable <PagedList>或Observable <PagedList>对象），该对象位于应用程序的ViewModel中。此可观察对象在应用程序列表数据的表示和内容之间形成连接。

为了创建这些可观察的PagedList对象之一，将[DataSource.Factory](https://developer.android.google.cn/reference/androidx/paging/DataSource.Factory)的实例传递给[LivePagedListBuilder](https://developer.android.google.cn/reference/androidx/paging/LivePagedListBuilder)或[RxPagedListBuilder](https://developer.android.google.cn/reference/androidx/paging/RxPagedListBuilder)对象。DataSource对象加载单个PagedList的页面。工厂类创建PagedList的新实例以响应内容更新，例如数据库表失效和网络刷新。Room持久性库可以为您提供DataSource.Factory对象，或者您可以[构建自己的对象](https://developer.android.google.cn/topic/libraries/architecture/paging/data#custom-data-source)。

以下代码段显示了如何使用Room的DataSource.Factory构建功能在应用程序的ViewModel类中创建LiveData <PagedList>的新实例：

`ConcertDao:`

```kotlin
@Dao
interface ConcertDao {
    // The Int type parameter tells Room to use a PositionalDataSource
    // object, with position-based loading under the hood.
    @Query("SELECT * FROM concerts ORDER BY date DESC")
    fun concertsByDate(): DataSource.Factory<Int, Concert>
}
```

```java
@Dao
public interface ConcertDao {
    // The Integer type parameter tells Room to use a PositionalDataSource
    // object, with position-based loading under the hood.
    @Query("SELECT * FROM concerts ORDER BY date DESC")
    DataSource.Factory<Integer, Concert> concertsByDate();
}
```

`ConcertViewModel:`

```kotlin
// The Int type argument corresponds to a PositionalDataSource object.
val myConcertDataSource : DataSource.Factory<Int, Concert> =
       concertDao.concertsByDate()

val concertList = myConcertDataSource.toLiveData(pageSize = 50)
```

```java
// The Integer type argument corresponds to a PositionalDataSource object.
DataSource.Factory<Integer, Concert> myConcertDataSource =
       concertDao.concertsByDate();

LiveData<PagedList<Concert>> concertList =
        LivePagedListBuilder(myConcertDataSource, /* page size */ 50).build();
```

### [定义您自己的分页配置](https://developer.android.google.cn/topic/libraries/architecture/paging/data#define-paging-config)

要为高级案例进一步配置LiveData <PagedList>，您还可以定义自己的分页配置。特别是，您可以定义以下属性：

- [**Page size**](https://developer.android.google.cn/reference/androidx/paging/PagedList.Config.Builder#setPageSize(int)): 每页加载多少数据
- [**Prefetch distance:**](https://developer.android.google.cn/reference/androidx/paging/PagedList.Config.Builder#setPrefetchDistance(int))距底部还有几条数据时，加载下一页数据
- [**Placeholder presence:**](https://developer.android.google.cn/reference/androidx/paging/PagedList.Config.Builder#setEnablePlaceholders(boolean))确定UI是否显示尚未完成加载的列表项的占位符。有关使用占位符的优缺点的讨论，请了解如何[在UI中提供占位符](https://developer.android.google.cn/topic/libraries/architecture/paging/ui#provide-placeholders)。


如果您想更好地控制Paging Library何时从应用程序的数据库加载列表，请将自定义Executor对象传递给LivePagedListBuilder，如以下代码段所示：

`ConcertViewModel:`

```kotlin
val myPagingConfig = Config(
        pageSize = 50,
        prefetchDistance = 150,
        enablePlaceholders = true
)

// The Int type argument corresponds to a PositionalDataSource object.
val myConcertDataSource : DataSource.Factory<Int, Concert> =
        concertDao.concertsByDate()

val concertList = myConcertDataSource.toLiveData(
        pagingConfig = myPagingConfig,
        fetchExecutor = myExecutor
)
```

```java
PagedList.Config myPagingConfig = new PagedList.Config.Builder()
        .setPageSize(50)
        .setPrefetchDistance(150)
        .setEnablePlaceholders(true)
        .build();

// The Integer type argument corresponds to a PositionalDataSource object.
DataSource.Factory<Integer, Concert> myConcertDataSource =
        concertDao.concertsByDate();

LiveData<PagedList<Concert>> concertList =
        new LivePagedListBuilder<>(myConcertDataSource, myPagingConfig)
            .setFetchExecutor(myExecutor)
            .build();
```

### [选择正确的数据源类型](https://developer.android.google.cn/topic/libraries/architecture/paging/data#choose-data-source-type)

连接到最能处理源数据结构的数据源非常重要：

- 如果您加载的页面嵌入了下一个/上一个键，请使用[PageKeyedDataSource](https://developer.android.google.cn/reference/androidx/paging/PageKeyedDataSource)。例如，如果您从网络中获取社交媒体帖子，则可能需要将nextPage令牌从一个加载传递到后续加载。
- 如果需要使用项目N中的数据来获取项目N + 1，请使用[ItemKeyedDataSource](https://developer.android.google.cn/reference/androidx/paging/ItemKeyedDataSource)。例如，如果您要为讨论应用程序提取线程评论，则可能需要传递最后一条评论的ID以获取下一条评论的内容。
- 如果需要从数据存储中选择的任何位置获取数据页，请使用[PositionalDataSource](https://developer.android.google.cn/reference/androidx/paging/PositionalDataSource)。此类支持从您选择的任何位置开始请求一组数据项。例如，请求可能返回以位置1500开头的50个数据项。

### [数据无效时通知](https://developer.android.google.cn/topic/libraries/architecture/paging/data#custom-data-source)

使用分页库时，数据层当表或行变得陈旧时通知应用程序的其他层。为此，请从您为应用程序选择的DataSource类中调用invalidate（）。
<table><tr><td bgcolor=#bfe1f1>注意：您的应用的UI可以使用[滑动刷新(swip to refresh)](https://developer.android.google.cn/training/swipe)模型触发此数据失效功能。</td></tr></table>

### [构建自己的数据源](https://developer.android.google.cn/topic/libraries/architecture/paging/data#custom-data-source)

如果使用自定义本地数据解决方案，或者直接从网络加载数据，则可以实现其中一个DataSource子类。以下代码段显示了一个数据源，该数据源与给定Concert的start time相关联：

kotlin:

```kotlin
class ConcertTimeDataSource() :
        ItemKeyedDataSource<Date, Concert>() {
    override fun getKey(item: Concert) = item.startTime

    override fun loadInitial(
            params: LoadInitialParams<Date>,
            callback: LoadInitialCallback<Concert>) {
        val items = fetchItems(params.requestedInitialKey,
                params.requestedLoadSize)
        callback.onResult(items)
    }

    override fun loadAfter(
            params: LoadParams<Date>,
            callback: LoadCallback<Concert>) {
        val items = fetchItemsAfter(
            date = params.key,
            limit = params.requestedLoadSize)
        callback.onResult(items)
    }
}
```

java:

```java
public class ConcertTimeDataSource
        extends ItemKeyedDataSource<Date, Concert> {
    @NonNull
    @Override
    public Date getKey(@NonNull Concert item) {
        return item.getStartTime();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Date> params,
            @NonNull LoadInitialCallback<Concert> callback) {
        List<Concert> items =
            fetchItems(params.key, params.requestedLoadSize);
        callback.onResult(items);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Date> params,
            @NonNull LoadCallback<Concert> callback) {
        List<Concert> items =
            fetchItemsAfter(params.key, params.requestedLoadSize);
        callback.onResult(items);
    }
```

然后，您可以通过创建DataSource.Factory的具体子类将此自定义数据加载到PagedList对象中。以下代码段显示了如何生成前面代码段中定义的自定义数据源的新实例：

kotlin:

```kotlin
class ConcertTimeDataSourceFactory :
        DataSource.Factory<Date, Concert>() {
    val sourceLiveData = MutableLiveData<ConcertTimeDataSource>()
    var latestSource: ConcertDataSource?
    override fun create(): DataSource<Date, Concert> {
        latestSource = ConcertTimeDataSource()
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}
```

java

```java
public class ConcertTimeDataSourceFactory
        extends DataSource.Factory<Date, Concert> {
    private MutableLiveData<ConcertTimeDataSource> sourceLiveData =
            new MutableLiveData<>();

    private ConcertDataSource latestSource;

    @Override
    public DataSource<Date, Concert> create() {
        latestSource = new ConcertTimeDataSource();
        sourceLiveData.postValue(latestSource);
        return latestSource;
    }
}
```

### [考虑内容更新的工作原理](https://developer.android.google.cn/topic/libraries/architecture/paging/data#consider-content-updates)

在构造可观察的PagedList对象时，请考虑内容更新的工作方式。如果您直接从Room数据库加载数据，则会自动将更新推送到您应用的UI。

使用分页网络API时，您通常会进行用户交互，例如“滑动以刷新”，作为使您最近使用的数据源无效的信号。然后，您请求该数据源的新实例。以下代码段演示了此行为：

kotlin

```kotlin:
class ConcertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // ...
        concertTimeViewModel.refreshState.observe(this, Observer {
            // Shows one possible way of triggering a refresh operation.
            swipeRefreshLayout.isRefreshing =
                    it == MyNetworkState.LOADING
        })
        swipeRefreshLayout.setOnRefreshListener {
            concertTimeViewModel.invalidateDataSource()
        }
    }
}

class ConcertTimeViewModel(firstConcertStartTime: Date) : ViewModel() {
    val dataSourceFactory = ConcertTimeDataSourceFactory(firstConcertStartTime)
    val concertList: LiveData<PagedList<Concert>> =
            dataSourceFactory.toLiveData(
                pageSize = 50,
                fetchExecutor = myExecutor
            )

    fun invalidateDataSource() =
            dataSourceFactory.sourceLiveData.value?.invalidate()
}
```

java:

```java
public class ConcertActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // ...
        viewModel.getRefreshState()
                .observe(this, new Observer<NetworkState>() {
            // Shows one possible way of triggering a refresh operation.
            @Override
            public void onChanged(@Nullable MyNetworkState networkState) {
                swipeRefreshLayout.isRefreshing =
                        networkState == MyNetworkState.LOADING;
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.invalidateDataSource();
            }
        });
    }
}

public class ConcertTimeViewModel extends ViewModel {
    private LiveData<PagedList<Concert>> concertList;
    private DataSource<Date, Concert> mostRecentDataSource;

    public ConcertTimeViewModel(Date firstConcertStartTime) {
        ConcertTimeDataSourceFactory dataSourceFactory =
                new ConcertTimeDataSourceFactory(firstConcertStartTime);
        mostRecentDataSource = dataSourceFactory.create();
        concertList = new LivePagedListBuilder<>(dataSourceFactory, 50)
                .setFetchExecutor(myExecutor)
                .build();
    }

    public void invalidateDataSource() {
        mostRecentDataSource.invalidate();
    }
}
```

### [提供数据映射](https://developer.android.google.cn/topic/libraries/architecture/paging/data#provide-data-mapping)

分页库支持由DataSource加载的项目的基于项目和基于页面的转换。

在以下代码段中，Concert名称和Concert日期的组合映射到包含名称和日期的单个字符串：

kotlin:

```kotlin
class ConcertViewModel : ViewModel() {
    val concertDescriptions : LiveData<PagedList<String>>
        init {
            val concerts = database.allConcertsFactory()
                    .map "${it.name} - ${it.date}" }
                    .toLiveData(pageSize = 50)
        }
    }
}
```

java:

```java
public class ConcertViewModel extends ViewModel {
    private LiveData<PagedList<String>> concertDescriptions;

    public ConcertViewModel(MyDatabase database) {
        DataSource.Factory<Integer, Concert> factory =
                database.allConcertsFactory().map(concert ->
                    concert.getName() + "-" + concert.getDate());
        concertDescriptions = new LivePagedListBuilder<>(
            factory, /* page size */ 50).build();
    }
}
```

如果要在项目加载后进行换行，转换或准备，这可能很有用。由于此工作是在获取执行程序上完成的，因此您可以执行可能昂贵的工作，例如从磁盘读取或查询单独的数据库。

<table><tr><td bgcolor=#bfe1f1>注意：JOIN查询总是更有效，可以作为map（）的一部分进行重新查询。</td></tr></table>






 








	   
	   
	

