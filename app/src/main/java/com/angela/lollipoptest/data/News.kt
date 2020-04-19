package com.angela.lollipoptest.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.angela.lollipoptest.data.source.local.LollipopConverters
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "news_in_table", primaryKeys = ["news_id"])
@TypeConverters(LollipopConverters::class)
@Parcelize
data class News (
    @ColumnInfo(name = "news_title")
    @Json(name = "title")
    val title: String,
    @ColumnInfo(name = "news_thumbnail")
    @Json(name = "thumbnail")
    val image: String,
    @ColumnInfo(name = "news_id")
    @Json(name = "id")
    val id: String
) : Parcelable
