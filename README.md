# Infinite scroll with pagination

## Purpose
1. Efficient

Infinitely scroll only download partial data and then load more data on demand when the scroll reaches the bottom.
<img src=https://github.com/yining24/Coding_Test/blob/master/demo.png width="250"/>

2. Android Architecture : Handle NetWork + Database

Here we download data through retrofit first, and save data lists in the database.
In order to drive UI from model, we create live data which is getting from database and observed by View.

## Step 1 : Establish repository to setup retrofit to fetch data

```kotlin
    override suspend fun getNewsPage(after: String): Result<NewsPageResult> {

        val getResultDeferred = LollipopApi.retrofitService.getNewsPage(after)
        return try {
            val listResult = getResultDeferred.await()

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }
```

## Step 2 : Creat Databese and DatabaseDao

```kotlin
@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class LollipopDatabase : RoomDatabase() {
    abstract val lollipopDatabaseDao: LollipopDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: LollipopDatabase? = null

        fun getInstance(context: Context): LollipopDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LollipopDatabase::class.java,
                        "lollipop_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

@Dao
interface LollipopDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: List<News>)

    @Query("SELECT * FROM news_in_table")
    fun getAllNews():
            LiveData<List<News>>

    @Query("DELETE FROM news_in_table")
    fun deleteTable()

}
```

## Step 3 : Use RecyclerView.OnScrollListener to check recyclerView position
When user scrolls to the bottom, View will notify ViewModel to load more data.

```kotlin
//in Activity or Fragment
        binding.recyclerNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = linearLayoutManager.childCount
                val pastVisiblePostion = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount

                if (viewModel.status.value != LoadApiStatus.LOADING) {
                    if (visibleItemCount + pastVisiblePostion >= total - 1) {
                        Logger.w("total itemCount size = $total")

                        viewModel.getNews(false)
                        adapter.notifyDataSetChanged()
                        //Here we use notifyDataSetChanged to let UI updata data without refreshing. 
                    }
                }
            }
        })
        
        viewModel.newsInLocal.observe(this, Observer {
            adapter.submitList(it)
        })
```

## Step 4 : ViewModel and Live data for fetching data
Don't forget to establish adapter, we do not mention here.

```kotlin
class NewsViewModel(private val repository: LollipopRepository) : ViewModel() {

    val newsInLocal = repository.getNewsInLocal()
    //...
       
       fun getNews(isInitial: Boolean = false) {

        if (!Utility.isInternetConnected()) {
            _status.value = LoadApiStatus.ERROR
            isInternetConnected.value = false
        } else {
            coroutineScope.launch {
                if (isInitial) {
                    deleteTable()
                    nextPage = ""
                }
                if (refreshStatus.value != true) _status.value = LoadApiStatus.LOADING

                when (val result = repository.getNewsPage(nextPage)) {
                    is Result.Success -> {
                        _status.value = LoadApiStatus.DONE

                        nextPage = result.data.newsPage.after ?: ""

                        val list = mutableListOf<News>()
                        result.data.newsPage.newsPageList?.forEach {
                            list.add(it.news)
                        }
                        repository.insertNewsInLocal(list)
                    }
                    is Result.Fail -> {
                        _status.value = LoadApiStatus.ERROR
                    }
                    is Result.Error -> {
                        _status.value = LoadApiStatus.ERROR
                    }
                    else -> {
                        _status.value = LoadApiStatus.ERROR
                    }
                }
                _refreshStatus.value = false
            }
        }
    }
    
```


That's all!  When the scroll reaches the bottom, Paging will automatically download more data to show.
You can add progress bar in xml. for showing loading status.

Hope this file will help :)
