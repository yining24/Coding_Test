package com.angela.lollipoptest.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NewsPageResult(
    val error: String? = null,
    @Json(name = "data") val newsPage: NewsPage
) : Parcelable

