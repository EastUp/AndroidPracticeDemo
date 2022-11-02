```
    ROOM
```
#Room
- 如果您的应用程序在单个进程中运行，则在实例化AppDatabase 对象时应遵循单例设计模式。  
- 每个实例都相当昂贵，您很少需要在单个进程中访问多个实例。 RoomDatabase  
- 通常，在整个APP中，只需要一个Room database实例。

###依赖
```
dependencies {
    def room_version = "2.2.0-alpha01" // 2.1.0 for latest stable version

    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version" // For Kotlin use kapt instead of annotationProcessor

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation "androidx.room:room-ktx:$room_version"

    // optional - RxJava support for Room
    implementation "androidx.room:room-rxjava2:$room_version"

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation "androidx.room:room-guava:$room_version"

    // Test helpers
    testImplementation "androidx.room:room-testing:$room_version"
}
```
###Room包含以下三个重要组成部分：

- [Database](https://developer.android.com/reference/android/arch/persistence/room/Database.html) 创建数据库。

- [Entities](https://developer.android.com/training/data-storage/room/defining-data.html) 数据库表中对应的Java对象

- [DAOs](https://developer.android.com/training/data-storage/room/accessing-data.html) 访问数据库

 详细的结构关系可以看下图：![Figure](https://developer.android.com/images/training/data-storage/room_architecture.png)

###Entities

[@Entity](https://developer.android.com/reference/android/arch/persistence/room/Entity.html)

@Entity注解包含的属性有：

- tableName：设置表名字。默认是类的名字。
- indices：设置索引。
- inheritSuperIndices：父类的索引是否会自动被当前类继承。
- primaryKeys：设置主键。
- foreignKeys：设置外键。

####设置表的名字  

&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;默认情况下Entity类的名字就是表的名字(不区分大小写)。但是我们也可以通过@Entity的**tableName**属性来自定义表名字。如下代码所示user表对应的实体类。

```kotlin
@Entity(tableName = "user")
class RoomAutoKeyUser {
	...
}
 
```

####设置列的名字  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;默认情况下Entity类中字段的名字就是表中列的名字。我们也是可以通过**@ColumnInfo**注解来自定义表中列的名字

``` kotlin
@Entity(tableName = "user")
class RoomAutoKeyUser {
   	...

    @ColumnInfo( name = "first_name")
    var firstName : String? = null
    
    ...
}
```

####设置主键  

&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;每个Entity都需要至少一个字段设置为主键。即使这个Entity只有一个字段也需要设置为主键。Entity设置主键的方式有两种:
 	
+ 通过@Entity的primaryKeys属性来设置主键(primaryKeys是数组可以设置单个主键，也可以设置复合主键)。

```kotlin
	@Entity(primaryKeys = {"firstName",
	                       "lastName"})
	public class User {
	
	    public String firstName;
	    public String lastName;
	}
```
+ 用@PrimaryKey注解来设置主键。(如果希望主键自增.可以设置`autoGnerate`属性)

```kotlin
	@Entity(tableName = "user")
	class RoomAutoKeyUser {
	
	    @PrimaryKey(autoGenerate = true)
	    @NonNull
	    var uid:Int ?= null
	    
	    ...
	}
```

####设置索引  

&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;数据库索引用于提高数据库表的数据访问速度的。数据库里面的索引有单列索引和组合索引。Room里面可以通过@Entity的indices属性来给表格添加索引。  

``` java
	@Entity(indices = {@Index("firstName"),
	        @Index(value = {"last_name", "address"})})
	public class User {
	    @PrimaryKey
	    public int id;
	
	    public String firstName;
	    public String address;
	
	    @ColumnInfo(name = "last_name")
	    public String lastName;
	
	    @Ignore
	    Bitmap picture;
	}
```
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;索引也是分两种的唯一索引和非唯一索引。唯一索引就想主键一样重复会报错的。可以通过的@Index的unique数学来设置是否唯一索引。

``` java
	@Entity(indices = {@Index(value = {"first_name", "last_name"},
	        unique = true)})
	public class User {
	    @PrimaryKey
	    public int id;
	
	    @ColumnInfo(name = "first_name")
	    public String firstName;
	
	    @ColumnInfo(name = "last_name")
	    public String lastName;
	
	    @Ignore
	    Bitmap picture;
	}
```
####设置外键
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;因为SQLite是关系形数据库，表和表之间是有关系的。这也就是我们数据库中常说的外键约束(FOREIGN KEY约束)。Room里面可以通过@Entity的foreignKeys属性来设置外键。我们用一个具体的例子来说明。  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;正常情况下，数据库里面的外键约束。子表外键于父表。当父表中某条记录子表有依赖的时候父表这条记录是不能删除的，删除会报错。一般大型的项目很少会采用外键的形式。一般都会通过程序依赖业务逻辑来保证的。

**`定义一对多关系`**

<font color=#ff0000 size = 4>注意:一对多的关系:注意添加book的时候 book一定属于某个RoomAutoKeyUser 即userId一定属于某个RoomAutoKeyUser  
否则会报错 : FOREIGN KEY constraint failed (code 787)</font>

``` kotlin 
/**
 * /**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 外键依赖RoomAutoKeyUser表,RoomAutoUser是父表,Book是字表
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Entity(tableName = "book",foreignKeys = arrayOf(ForeignKey(entity = RoomAutoKeyUser::class,
                                                            parentColumns = arrayOf("uid"),
                                                            childColumns = arrayOf("user_id"),
                                                            onUpdate = ForeignKey.CASCADE, //父表更新时,字表跟着更新
                                                            onDelete = ForeignKey.RESTRICT)))//当parent中的key有依赖的时候禁止对parent做动作，做动作就会报错。
class Book {
    @PrimaryKey(autoGenerate = true)
    var bookId :Int = 0

    @ColumnInfo(name = "user_id")
    var userId : Int = 0

    var title : String ?= null
}
```
上述代码通过foreignKeys之后Book表中的userId来源于User表中的id。  
  @ForeignKey属性介绍：  

1. entity：parent实体类(引用外键的表的实体)。  

2. parentColumns：parent外键列(要引用的外键列)。  

3. childColumns：child外键列(要关联的列)。  

4. onDelete：默认NO_ACTION，当parent里面有删除操作的时候，child表可以做的Action动作有：  

   - NO_ACTION：当parent中的key有变化的时候child不做任何动作。  

   - RESTRICT：当parent中的key有依赖的时候禁止对parent做动作，做动作就会报错。  

   - SET_NULL：当paren中的key有变化的时候child中依赖的key会设置为NULL。  

   - SET_DEFAULT：当parent中的key有变化的时候child中依赖的key会设置为默认值。  

   - CASCADE：当parent中的key有变化的时候child中依赖的key会跟着变化。

5. onUpdate：默认NO_ACTION，当parent里面有更新操作的时候，child表需要做的动作。Action动作方式和onDelete是一样的。

6. deferred：默认值false，在事务完成之前，是否应该推迟外键约束。这个怎么理解，当我们启动一个事务插入很多数据的时候，事务还没完成之前。当parent引起key变化的时候。可以设置deferred为ture。让key立即改变。
   
   
   

<font color=#ff0000> 注：SQLite的处理 @Insert(onConflict = REPLACE) 为一组REMOVE和REPLACE操作，而不是一个单一的UPDATE 操作。这种替换冲突值的方法可能会影响您的外键约束。有关更多详细信息，请参阅该 子句的 [SQLite documentation](https://sqlite.org/lang_conflict.html)ON_CONFLICT。 </font>


**`创建嵌套对象`**  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;有些情况下，你会需要将多个对象组合成一个对象。对象和对象之间是有嵌套关系的。Room中你就可以使用@Embedded注解来表示嵌入。然后，您可以像查看其他单个列一样查询嵌入字段。比如有一个这样的例子，User表包含的列有：id, firstName, street, state, city, and post_code。这个时候我们的嵌套关系可以用如下代码来表示。

``` kotlin
data class Address(
    val street: String?,
    val state: String?,
    val city: String?,
    @ColumnInfo(name = "post_code") val postCode: Int
)

@Entity
data class User(
    @PrimaryKey val id: Int,
    val firstName: String?,
    @Embedded val address: Address?
)
```

   @Embedded注解属性：  

prefix：如果实体具有多个相同类型的嵌入字段，则可以通过设置前缀属性来保持每个列的唯一性。然后Room会将提供的值添加到嵌入对象中每个列名的开头。


**`定义多对多关系`**  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;您经常要在关系数据库中建模另一种关系：两个实体之间的多对多关系，其中每个实体可以链接到另一个实体的零个或多个实例。例如，考虑一个音乐流媒体应用程序，用户可以将他们喜欢的歌曲组织成播放列表。每个播放列表可以具有任意数量的歌曲，并且每首歌曲可以包括在任意数量的播放列表中。

要建立此关系，您需要创建三个对象：

- 播放列表的实体类。
- 歌曲的实体类。
- 一个中间类，用于保存每个播放列表中有哪些歌曲的信息。  

您可以将实体类定义为独立单元：

``` kotlin
@Entity
data class Playlist(
    @PrimaryKey var id: Int,
    val name: String?,
    val description: String?
)

@Entity
data class Song(
    @PrimaryKey var id: Int,
    val songName: String?,
    val artistName: String?
)
```

然后，将中间类定义为包含对两者的外键引用的实体，`Song and Playlist`：

``` kotlin
@Entity(tableName = "playlist_song_join",
        primaryKeys = arrayOf("playlistId","songId"),
        foreignKeys = arrayOf(
                         ForeignKey(entity = Playlist::class,
                                    parentColumns = arrayOf("id"),
                                    childColumns = arrayOf("playlistId")),
                         ForeignKey(entity = Song::class,
                                    parentColumns = arrayOf("id"),
                                    childColumns = arrayOf("songId"))
                              )
        )
data class PlaylistSongJoin(
    val playlistId: Int,
    val songId: Int
)
```

这会生成一个多对多关系模型，允许您使用DAO按播放列表按歌曲和歌曲查询播放列表：

``` kotlin
@Dao
interface PlaylistSongJoinDao {
    @Insert
    fun insert(playlistSongJoin: PlaylistSongJoin)

    @Query("""
           SELECT * FROM playlist
           INNER JOIN playlist_song_join
           ON playlist.id=playlist_song_join.playlistId
           WHERE playlist_song_join.songId=:songId
           """)
    fun getPlaylistsForSong(songId: Int): Array<Playlist>

    @Query("""
           SELECT * FROM song
           INNER JOIN playlist_song_join
           ON song.id=playlist_song_join.songId
           WHERE playlist_song_join.playlistId=:playlistId
           """)
    fun getSongsForPlaylist(playlistId: Int): Array<Song>
}
```


####获取关联的Entity
Entity之间可能也有一对多之间的关系。比如一个User有多个Pet，通过一次查询获取多个关联的Pet。

``` java
public class UserAndAllPets {
    @Embedded
    public User user;
    @Relation(parentColumn = "id", entityColumn = "user_id")
    public List<Pet> pets;
}

 @Dao
 public interface UserPetDao {
     @Query("SELECT * from User")
     public List<UserAndAllPets> loadUserAndPets();
 }
```
使用 [@Relation](https://developer.android.com/reference/android/arch/persistence/room/Relation.html) 注解的field必须是一个List或者一个Set。通常情况下， Entity 的类型是从返回类型中推断出来的，可以通过定义 entity()来定义特定的返回类型。  

``` java
 public class User {
     int id;
     // other fields
 }
 public class PetNameAndId {
     int id;
     String name;
 }
 public class UserAllPets {
   @Embedded
   public User user;
   @Relation(parentColumn = "id", entityColumn = "userId", entity = Pet.class)
   public List<PetNameAndId> pets;
 }
 @Dao
 public interface UserPetDao {
     @Query("SELECT * from User")
     public List<UserAllPets> loadUserAndPets();
 }
```

在上面的示例中，PetNameAndId是一个常规Pojo，但所有字段都是从注释（Pet）中entity定义的。 也可以定义自己的关系，所有关系也将自动获取。 @RelationPetNameAndId  

如果要指定从子项中提取哪些列，则Entity可以projection()在Relation注释中使用属性。

``` java
 public class UserAndAllPets {
   @Embedded
   public User user;
   @Relation(parentColumn = "id", entityColumn = "userId", entity = Pet.class,
           projection = {"name"})
   public List<String> petNames;
 }
```

###在数据库中创建视图
Room持久性库的 2.1.0及更高版本提供对SQLite数据库视图的支持，允许您将查询封装到类中。Room将这些查询支持的类称为视图，并且在DAO中使用时它们的行为与简单数据对象相同 。

#### 创建视图
要创建视图，请将@DatabaseView注释添加 到类中。将注释的值设置为类应表示的查询。  

``` kotlin
@DatabaseView("SELECT user.id, user.name, user.departmentId," +
        "department.name AS departmentName FROM user " +
        "INNER JOIN department ON user.departmentId = department.id")
data class UserDetail(
    val id: Long,
    val name: String?,
    val departmentId: Long,
    val departmentName: String?
)
```

####将视图与数据库关联
要将此视图作为应用程序数据库的一部分,在应用程序的@Database注释中包含views，请包含该 属性 ：

``` kotlin
@Database(entities = arrayOf(User::class),
          views = arrayOf(UserDetail::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

###2.Data Access Objects（DAOs）
DAOs是数据库访问的抽象层。  
`Dao`可以是一个接口也可以是一个抽象类。如果是抽象类，那么它可以接受一个`RoomDatabase`作为构造器的唯一参数。  
Room不允许在主线程中访问数据库，除非在builder里面调用`allowMainThreadQueries()` 。因为访问数据库是耗时的，可能阻塞主线程，引起UI卡顿。

####2.1 Insert
使用 @Insert注解的方法，Room将会生成插入的代码。

@Insert注解可以设置一个属性：

`onConflict`：默认值是OnConflictStrategy.ABORT，表示当插入有冲突的时候的处理策略。OnConflictStrategy封装了Room解决冲突的相关策略：  

1. OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务。  
2. OnConflictStrategy.ROLLBACK：冲突策略是回滚事务。  
3. OnConflictStrategy.ABORT：冲突策略是终止事务。  
4. OnConflictStrategy.FAIL：冲突策略是事务失败。  
5. OnConflictStrategy.IGNORE：冲突策略是忽略冲突。  
一个简单的实例如下：  

``` java
@Dao
public interface UserDao {  
    @Insert(onConflict = OnConflictStrategy.REPLACE)  
    void insertUsers(User... users);  
}
```

####2.2、Update(更新)  
       当DAO里面的某个方法添加了@Update注解。Room会把对应的参数信息更新到数据库里面去(会根据参数里面的primary key做更新操作)。  
       @Update和@Insert一样也是可以设置onConflict来表明冲突的时候的解决办法。  

``` java
@Dao
public interface UserDao {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateUsers(User... users);

}
```


@Update注解的方法也可以返回int变量。表示更新了多少行。

####2.3、Delete(删除)
       当DAO里面的某个方法添加了@Delete注解。Room会把对应的参数信息指定的行删除掉(通过参数里面的primary key找到要删除的行)。  
       @Delete也是可以设置onConflict来表明冲突的时候的解决办法。  

``` java
@Dao
public interface UserDao {
    @Delete
    void deleteUsers(User... users);

}
```

@Delete对应的方法也是可以设置int返回值来表示删除了多少行。

####2.4、Query(查询)  
       @Query注解是DAO类中使用的主要注释。它允许您对数据库执行读/写操作。@Query在编译的时候会验证准确性，所以如果查询出现问题在编译的时候就会报错。  
       Room还会验证查询的返回值，如果返回对象中的字段名称与查询响应中的相应列名称不匹配的时候，Room会通过以下两种方式之一提醒您：  

- 如果只有一些字段名称匹配，它会发出警告。  
- 如果没有字段名称匹配，它会发生错误。  

       @Query注解value参数：查询语句，这也是我们查询操作最关键的部分。

##### 2.4.1、简单的查询
       查询所有的信息。  
       
``` java
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    User[] loadAllUsers();

}
```

返回结果可以是数组，也可以是List。

##### 2.4.2、带参数的查询
       大多数情况下我们都需要查询满足特定的查询条件的信息。

``` java
@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE firstName == :name")
    User[] loadAllUsersByFirstName(String name);

}
```

       查询需要多个参数的情况

``` java
@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
    User[] loadAllUsersBetweenAges(int minAge, int maxAge);

    @Query("SELECT * FROM user WHERE firstName LIKE :search " + "OR lastName LIKE :search")
    List<User> findUserWithName(String search);

}
```

##### 2.4.3、查询返回列的子集
       有的时候可能指向返回某些特定的列信息。

下来的例子只查询user表中的firstName和lastName信息。

``` java
@Entity
public class User {

    @PrimaryKey
    public String firstName;
    @PrimaryKey
    public String lastName;
    public int    age;
}

public class NameTuple {

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

@Dao
public interface UserDao {

    @Query("SELECT firstName, lastName FROM user")
    List<NameTuple> loadFullName();

}
```

##### 2.4.4、查询的时候传递一组参数
       在查询的时候您可能需要传递一组(数组或者List)参数进去。

``` java
@Dao
public interface UserDao {

    @Query("SELECT firstName, lastName FROM user WHERE region IN (:regions)")
    public List<NameTuple> loadUsersFromRegions(List<String> regions);

}
```

##### 2.4.5、Observable的查询
       意思就是查询到结果的时候，UI能够自动更新。Room为了实现这一效果，查询的返回值的类型为LiveData。

``` java
@Dao
public interface UserDao {

    @Query("SELECT firstName, lastName FROM user WHERE region IN (:regions)")
    LiveData<List<NameTuple>> loadUsersFromRegionsSync(List<String> regions);

}
```


关于LiveData的具体用法，我们这里就不做过多的讨论了。

##### 2.4.6、使用RxJava作为查询的返回值
       Room的查询也可以返回RxJava2的Publisher或者Flowable对象。当然了想要使用这一功能需要在build.gradle文件添加 implementation "android.arch.persistence.room:rxjava2:1.1.1" 依赖。

``` java
@Dao
public interface UserDao {

    @Query("SELECT * from user")
    Flowable<List<User>> loadUser();

}
```

拿到Flowable<List<User>>之后就可以去调用

``` java
mAppDatabase.userDao()
            .loadUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<List<User>>() {
                @Override
                public void accept(List<User> entities) {

                }
            });
```

##### 2.4.7、查询结果直接返回Cursor
       查询结果直接返回cursor。然后通过cursor去获取具体的结果信息。

``` java
@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE age > :minAge LIMIT 5")
    Cursor loadRawUsersOlderThan(int minAge);

}
```


关于怎么从Cursor里面去获取结果，大家肯定都非常熟悉。

##### 2.4.8、多表查询
       有的时候可能需要通过多个表才能获取查询结果。这个就涉及到数据的多表查询语句了。

``` java
@Dao
public interface MyDao {
    @Query("SELECT * FROM book "
           + "INNER JOIN loan ON loan.book_id = book.id "
           + "INNER JOIN user ON user.id = loan.user_id "
           + "WHERE user.name LIKE :userName")
   public List<Book> findBooksBorrowedByNameSync(String userName);
}
```

       也可以查询指定的某些列。

``` java
@Dao
public interface MyDao {
   @Query("SELECT user.name AS userName, pet.name AS petName "
          + "FROM user, pet "
          + "WHERE user.id = pet.user_id")
   public LiveData<List<UserPet>> loadUserAndPetNames();


   // You can also define this class in a separate file, as long as you add the
   // "public" access modifier.
   static class UserPet {
       public String userName;
       public String petName;
   }
}
```

### 三、Database(数据库)

       @Database注解可以用来创建数据库的持有者。该注解定义了实体列表，该类的内容定义了数据库中的DAO列表。这也是访问底层连接的主要入口点。注解类应该是抽象的并且扩展自RoomDatabase。

       Database对应的对象(RoomDatabase)必须添加@Database注解，@Database包含的属性：

- entities：数据库相关的所有Entity实体类，他们会转化成数据库里面的表。
- version：数据库版本。
- exportSchema：默认true，也是建议传true，这样可以把Schema导出到一个文件夹里面。同时建议把这个文件夹上次到VCS。

       在运行时，你可以通过调用Room.databaseBuilder()或者Room.inMemoryDatabaseBuilder()获取实例。因为每次创建Database实例都会产生比较大的开销，所以应该将Database设计成单例的，或者直接放在Application中创建。

> 两种方式获取Database对象的区别:
> 
> - **Room.databaseBuilder()：**生成Database对象，并且创建一个存在文件系统中的数据库。
> - **Room.inMemoryDatabaseBuilder()：**生成Database对象并且创建一个存在内存中的数据库。当应用退出的时候(应用进程关闭)数据库也消失。


#####        我们用一个简单的实例来说明Database的创建。先定义一个abstract类AppDatabase继承RoomDatabase。

``` java
@Database(entities = {User.class, Book.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract BookDao bookDao();

}
```

#####        创建RoomDatabase实例(AppDatabase)。这里我们把RoomDatabase实例的创建放在Application里面。


``` java
public class AppApplication extends Application {

    private AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "android_room_dev.db")
                           .allowMainThreadQueries()
                           .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                           .build();
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }

    /**
     * 数据库版本 1->2 user表格新增了age列
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE User ADD COLUMN age integer");
        }
    };

    /**
     * 数据库版本 2->3 新增book表格
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `book` (`uid` INTEGER PRIMARY KEY autoincrement, `name` TEXT , `userId` INTEGER, 'time' INTEGER)");
        }
    };
}
```

#####        创建RoomDatabase实例的时候，RoomDatabase.Builder类里面主要方法的介绍：


``` java
    /**
     * 默认值是FrameworkSQLiteOpenHelperFactory，设置数据库的factory。比如我们想改变数据库的存储路径可以通过这个函数来实现
     */
    public RoomDatabase.Builder<T> openHelperFactory(@Nullable SupportSQLiteOpenHelper.Factory factory);

    /**
     * 设置数据库升级(迁移)的逻辑
     */
    public RoomDatabase.Builder<T> addMigrations(@NonNull Migration... migrations);

    /**
     * 设置是否允许在主线程做查询操作
     */
    public RoomDatabase.Builder<T> allowMainThreadQueries();

    /**
     * 设置数据库的日志模式
     */
    public RoomDatabase.Builder<T> setJournalMode(@NonNull JournalMode journalMode);

    /**
     * 设置迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
     */
    public RoomDatabase.Builder<T> fallbackToDestructiveMigration();

    /**
     * 设置从某个版本开始迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
     */
    public RoomDatabase.Builder<T> fallbackToDestructiveMigrationFrom(int... startVersions);

    /**
     * 监听数据库，创建和打开的操作
     */
    public RoomDatabase.Builder<T> addCallback(@NonNull RoomDatabase.Callback callback);
```

       RoomDatabase除了必须添加@Database注解也可以添加`@TypeConverter`注解。用于提供一个把自定义类转化为一个Room能够持久化的已知类型的，比如我们想持久化日期的实例，我们可以用如下代码写一个TypeConverter去存储相等的Unix时间戳在数据库中。

``` java
public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
```

### 四使用类型转换器


如果想要在数据库中存储Date，可以存储等价的Unix时间戳。通过 TypeConverter 可以很方便的做到这一点：

``` java
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
```

这里定义了两个方法，将Date和Unix时间戳相互转换。Room支持存储Long类型的对象，这样就可以通过这种方法存储Date。  
接下来将 [TypeConverters](https://developer.android.com/reference/android/arch/persistence/room/TypeConverters.html)添加到AppDatabase中，这样Room就能识别这种转换：  
`AppDatabase.java`

``` java
@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

接下来就可以像使用基本类型一样使用自定义类型的查询，比如：
`User.java`

``` java
@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

`UserDao.java`

```java
@Dao
public interface UserDao {
    ...
    @Query("SELECT * FROM user WHERE birthday BETWEEN :from AND :to")
    List<User> findUsersBornBetweenDates(Date from, Date to);
}
```

关于更多 [@TypeConverters](https://developer.android.com/reference/android/arch/persistence/room/TypeConverters.html)的用法，可以参考[这里](https://developer.android.com/reference/android/arch/persistence/room/TypeConverters.html)。


### 五、数据迁移(升级)
       大部分情况下设计的数据库在版本的迭代过程中经常是会有变化的。比如突然某个表需要新加一个字段，需要新增一个表。这个时候我们又不想失去之前的数据。Room里面以Migration类的形式提供可一个简化SQLite迁移的抽象层。Migration提供了从一个版本到另一个版本迁移的时候应该执行的操作。


       当数据库里面表有变化的时候(不管你是新增了表，还是改变了某个表)有如下几个场景。

- 如果database的版本号不变。app操作数据库表的时候会直接crash掉。(错误的做法)  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果我们保持数据库版本不变，直接运行我们的App，Room在背后所做的事情如下：
   1. 第一步：尝试打开数据库
      1. 比对数据库的identity值：当前的版本的 identity hash与在 room_master_table 中 identity hash比较。但是因为identity hash没有被存储，因此app 将会奔溃：
         ```
         java.lang.IllegalStateException: Room cannot verify the data integrity. Looks like you’ve changed schema 
         but forgot to update the version number. You can simply fix this by increasing the version number.
         ```
      2. >如果你修改了数据库的架构，但是没有更新数据库的版本，Room 总会报 IllegalStateException。
      3. 我们听从它的意见，将版本修改为2：
         ```
         @Database(entities = {User.class},version = 2}
         public abstract class UserDatabase extends RoomDatabase
         ```
- 如果增加database的版本号。但是不提供Migration迁移策略。app操作数据库表的时候会直接crash掉。（错误的做法）  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我们按照以下步骤再次运行 Room：
  1. 第一步：更新数据库版本从1（已经安装到设备上了）到2  
     因为没有迁移策略，所以应用崩溃报 IllegalStateException.❌
     ```
     java.lang.IllegalStateException: A migration from 1 to 2 is necessary. 
     Please provide a Migration in the builder or call fallbackToDestructiveMigration in the builder
     in which case Room will re-create all of the tables.
     ```
  2. >如果你没有提供迁移策略，Room 就会报 IllegalStateException 异常
- 如果增加database的版本号。但是不提供Migration迁移策略。同时启用fallbackToDestructiveMigration(回退到破坏性迁移)。这个时候之前的数据会被清空掉。如下- fallbackToDestructiveMigration()设置。(不推荐的做法)  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果你不想提供迁移策略，而且你特别指定了在更新数据库版本时，数据库数据将会被清空，那么调用fallbackToDestructiveMigration可以满足你的要求：
``` java
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "android_room_dev.db")
                           .allowMainThreadQueries()
                           .fallbackToDestructiveMigration()
                           .build();
```

   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;接下来我们再次运行，Room 将会做一下动作：  
   1. 第一步：尝试从版本1更新到版本2
      - 因为现在没有迁移策略，而且我们还设定了回退到破坏性迁移，那么所有的数据库表将会被删除掉，新的identity hash 将会被插入。
   2. 第二步：尝试重新打开数据库
      1. 因为当前版本的identity hash 和 插入的identity hash是同一个，数据库打开。✅
      2. 现在打开时，我们的app没有奔溃，但是我们丢失了所有数据。做这种操作时，先看看是不是真要这么操作😄。


- 增加database的版本号，同时提供Migration。这要是Room数据迁移的重点。(**最正确的做法**)

       所以在数据库有变化的时候，我们任何时候都应该尽量提供Migrating。Migrating让我们可以自己去处理数据库从某个版本过渡到另一个版本的逻辑。我们用一个简单的实例来说明。有这么个情况，数据库开始设计的时候我们就一个user表(数据库版本 1)，第一次变化来了我们需要给user表增加一个age的列(数据库版本 2)，过了一段时间又有变化了我们需要新增加一个book表。三个过程版本1->2->3。


#####        数据库版本为1的时候的代码

``` java
@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}

public class AppApplication extends Application {

    private AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "android_room_dev.db")
                           .allowMainThreadQueries()
                           .build();
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
    
}
```

#####        数据库版本为2的时候的代码，User增加了age列

``` java
@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private long    uid;
    private String  name;
    private String  address;
    private String  phone;
    private Integer age;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

@Database(entities = {User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}

public class AppApplication extends Application {

    private AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "android_room_dev.db")
                           .allowMainThreadQueries()
                           .addMigrations(MIGRATION_1_2)
                           .build();
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }

    /**
     * 数据库版本 1->2 user表格新增了age列
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user " + " ADD COLUMN age INTEGER");
        }
    };

}
```

#####        数据库版本为3的时候的代码，新增了一个Book表

``` java
@Entity
public class Book {

    @PrimaryKey(autoGenerate = true)
    private Long   uid;
    private String name;
    private Date   time;
    private Long   userId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}

@Database(entities = {User.class, Book.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract BookDao bookDao();

}

public class AppApplication extends Application {

    private AppDatabase mAppDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "android_room_dev.db")
                           .allowMainThreadQueries()
                           .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                           .build();
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }

    /**
     * 数据库版本 1->2 user表格新增了age列
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE User ADD COLUMN age integer");
        }
    };

    /**
     * 数据库版本 2->3 新增book表格
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `book` (`uid` INTEGER PRIMARY KEY autoincrement, `name` TEXT , `userId` INTEGER, 'time' INTEGER)");
        }
    };
}
```



>Migrating使用过程中也有碰到一些坑，这里告诫大家一个经验Entity中能用Integer的时候不用int。

### 五、数据库信息的导出
       Room也允许你会将你数据库的表信息导出为一个json文件。你应该在版本控制系统中保存该文件，该文件代表了你的数据库表历史记录，这样允许Room创建旧版本的数据库用于测试。只需要在build.gradle文件中添加如下配置。编译的时候就会导出json文件。

``` java
android {
    ...
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = ["room.schemaLocation":
                             "$projectDir/schemas".toString()]
            }
        }
    }
    // 用于测试
    sourceSets {
        androidTest.assets.srcDirs += files("$projectDir/schemas".toString())
    }
}
```


>json文件文件会导出在工程目录下的schemas文件夹下面。里面会有各个版本数据库的信息。

**问题:**  
       


      修改了数据表结构,如果不进行数据库的迁移会报如下错误


      1. java.lang.IllegalStateException: Room cannot verify the data integrity. Looks like you’ve changed schema but forgot to update the version number. You can simply fix this by increasing the version number.  

      数据库迁移出错,如何查找错误

      2. Migration didn't properly handle: user(com.east.architecture_components.db.entity.RoomAutoKeyUser).  
       **`代表migration方法内的sql代码写的有问题,通过下面提示进行查找(蓝色部分)`**  

       Expected:  
       TableInfo{name='user', columns={uid=Column{name='uid', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='null'}, first_name=Column{name='first_name', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, last_name=Column{name='last_name', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}}, foreignKeys=[], <font color=#0000ff>indices=[Index{name='index_user_uid', unique=false, columns=[uid]}]}</font>  
       Found:  
       TableInfo{name='user', columns={uid=Column{name='uid', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='null'}, last_name=Column{name='last_name', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, first_name=Column{name='first_name', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}}, foreignKeys=[], <font color=#0000ff>indices=[Index{name='uid', unique=false, columns=[uid]}]}</font>  
















​	   
​	

