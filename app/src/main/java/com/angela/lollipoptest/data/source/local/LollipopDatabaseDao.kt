package com.angela.lollipoptest.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsResult

@Dao
interface LollipopDatabaseDao {

    @Query("SELECT * FROM news_in_table")
    fun post() : DataSource.Factory<Int, News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: List<News>)


    @Query("SELECT * FROM news_in_table")
    fun getAllNews():
            LiveData<List<News>>

    @Query("DELETE FROM news_in_table")
    fun deleteTable()

}

