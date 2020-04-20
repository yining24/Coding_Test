# Infinite scroll by using Paging Library

## Why to use it?
Infinitely scroll only download partial data and then load more data on demand when the scroll reaches the bottom.
<img src=https://github.com/yining24/Coding_Test/blob/master/demo.png width="250"/>

Paging library makes it efficiently and faster to load data gradually in app.
Here we use Paging on the idea of sending lists to the UI with the live data that is observed by RecyclerView.Adapter.
There are three class(Following will mention how to implement):
1. PagedListAdapter 
2. PagedList 
3. DataSource


## Step 1 : Add dependencies

```kotlin
dependencies {
implementation "androidx.paging:paging-runtime-ktx:$version_paging"
}
```

## Step 2 : Establish repository to setup retrofit to fetch data

```kotlin
    override suspend fun getHome(after: String): Result<HomeResult> {

        val getResultDeferred = LollipopApi.RETROFIT_SERVICE.getHome(after)
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

## Step 3 : PagedListAdapter
PagedListAdapter is an implementation of RecyclerView.Adapter that presents data from a PagedList.

```kotlin
class HomePagingAdapter : PagedListAdapter<NewsResult, RecyclerView.ViewHolder>(DiffCallback) {

//mothod of RecyclerView.Adapter

}
```

## Step 4 : DataSource
This is the base class for data loading.
DataSource can be implemented using these 3 classes:

PageKeyedDataSource : When we need to load data based on the number of previous pages until all the pages are fetched and displayed.

ItemKeyedDataSource : To define the key(N) that will be used to determine the next page(N+1) of data.

PositionalDataSource : Can be fetched data with arbitrary positions and sizes.

In this sample, we use PageKeyedDataSource. 

```kotlin
class PagingDataSource : PageKeyedDataSource<String, NewsResult>() {

    private val _statusInitialLoad = MutableLiveData<LoadApiStatus>()

    val statusInitialLoad: LiveData<LoadApiStatus>
        get() = _statusInitialLoad

    private val _errorInitialLoad = MutableLiveData<String>()

    val errorInitialLoad: LiveData<String>
        get() = _errorInitialLoad

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

//Hewe will load the data initially.
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, NewsResult>) {

        coroutineScope.launch {
            _statusInitialLoad.value = LoadApiStatus.LOADING

            val result = LollipopApplication.INSTANCE.lollipopRepository.getOldHome("")
            when (result) {
                is Result.Success -> {
                    _errorInitialLoad.value = null
                    _statusInitialLoad.value = LoadApiStatus.DONE
                    result.data.homeData.children?.let {
                   
//callback will pass next page key to loadAfter.
                        callback.onResult(it, null, result.data.homeData.after) }
                }
                is Result.Fail -> {
                    _errorInitialLoad.value = result.error
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _errorInitialLoad.value = result.exception.toString()
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
                else -> {
                    _errorInitialLoad.value = getString(R.string.something_wrong)
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
            }
        }
    }
    
//Hewe will load the data after initial.
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, NewsResult>) {

        coroutineScope.launch {
            _statusInitialLoad.value = LoadApiStatus.LOADING

            val result = LollipopApplication.INSTANCE.lollipopRepository.getOldHome(params.key)
            when (result) {
                is Result.Success -> {
                    _statusInitialLoad.value = LoadApiStatus.DONE
                    result.data.homeData.children?.let {
//Pass next page key util all data fetched. 
                        callback.onResult(it, result.data.homeData.after) }
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, NewsResult>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
```

## Step 5 : PagedList and PagingDataSourceFactory
Use PagingDataSourceFactory to send value to PagedList in the viewModel.
So UI can observe the LiveData<PagedList<NewsResult>> and pass it to the adapter.

```kotlin
class HomeViewModel(private val repository: LollipopRepository) : ViewModel() {

    private val sourceFactory = PagingDataSourceFactory()

    val pagingDataNews: LiveData<PagedList<NewsResult>> = sourceFactory.toLiveData(1, null)

    // Handle load api status
    val status: LiveData<LoadApiStatus> = Transformations.switchMap(sourceFactory.sourceLiveData) {
        it.statusInitialLoad
    }

    val error: LiveData<String> = Transformations.switchMap(sourceFactory.sourceLiveData) {
        it.errorInitialLoad
    }
    //...
}
```

```kotlin
class PagingDataSourceFactory : DataSource.Factory<String, NewsResult>() {

    val sourceLiveData = MutableLiveData<PagingDataSource>()

    override fun create(): DataSource<String, NewsResult> {
        val source = PagingDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}
```

That's all! When the scroll reaches the bottom, Paging will automatically download more data to show.
You can add progress bar in xml. for showing loading status.

Hope this file will help:)
