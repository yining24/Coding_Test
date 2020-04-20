package com.angela.lollipoptest.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NewsResult (
    @Json(name = "data") var news: News
) : Parcelable
