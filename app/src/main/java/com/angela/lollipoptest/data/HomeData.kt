package com.angela.lollipoptest.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

//@Entity(tableName = "home_items", primaryKeys = ["title"])
@Parcelize
data class HomeData (
    @Json(name = "children")
    val children: List<NewsResult>,
    @Json(name = "dist")
    val dist: Long
) : Parcelable