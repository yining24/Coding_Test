package com.angela.lollipoptest.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NewsPage (
    @Json(name = "children")
    val newsPageList: List<NewsResult>? = null,
    @Json(name = "after")
    val after: String? = null
) : Parcelable