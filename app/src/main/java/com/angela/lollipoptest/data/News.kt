package com.angela.lollipoptest.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class News (
    @Json(name = "title")
    val title: String,
    @Json(name = "thumbnail")
    val image: String,
    @Json(name = "id")
    val id: String
) : Parcelable
