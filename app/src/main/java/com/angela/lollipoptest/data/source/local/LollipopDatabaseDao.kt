package com.angela.lollipoptest.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsResult

@Dao
interface LollipopDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News)


    @Query("SELECT * FROM news_in_table ORDER BY news_id ASC")
    fun getAllNews():
            LiveData<List<News>>



}

