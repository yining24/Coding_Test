package com.angela.lollipoptest.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "home_items", primaryKeys = ["id","title","image"])
@Parcelize
data class HomeItem (
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "id")
    val id: String
) : Parcelable