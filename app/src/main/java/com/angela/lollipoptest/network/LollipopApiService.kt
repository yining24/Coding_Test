package com.angela.lollipoptest.network

import com.angela.lollipoptest.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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

//interface LollipopApiService {
//    @GET(JSON_ROUTE)
//    fun getDramas():
//    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
//            Deferred<Result>
//}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
//object LollipopApi {
//    val RETROFIT_SERVICE : LollipopApiService by lazy { retrofit.create(LollipopApiService::class.java) }
//}