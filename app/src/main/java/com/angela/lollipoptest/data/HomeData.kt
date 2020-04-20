package com.angela.lollipoptest.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class HomeData (
    @Json(name = "children")
    val children: List<NewsResult>? = null,
    @Json(name = "dist")
    val dist: Long? = null,
    @Json(name = "after")
    val after: String? = null
) : Parcelable