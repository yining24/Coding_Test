package com.angela.lollipoptest.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
data class HomeResult(
    val error: String? = null,
    @Json(name = "data") val homeData: HomeData
) : Parcelable

