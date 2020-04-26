package com.angela.lollipoptest.network

import com.angela.lollipoptest.BuildConfig
import com.angela.lollipoptest.data.NewsPageResult
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val HOST_NAME = "www.reddit.com/r"
private const val JSON_ROUTE = "hot.json"
private const val BASE_URL = "https://$HOST_NAME/Taiwan/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = when (BuildConfig.LOGGER_VISIABLE) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
    })
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface LollipopApiService {
    @GET(JSON_ROUTE)
    fun getNewsPage(@Query("after") after: String? = null):
            Deferred<NewsPageResult>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object LollipopApi {
    val retrofitService: LollipopApiService by lazy { retrofit.create(LollipopApiService::class.java) }
}