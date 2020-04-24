package com.angela.lollipoptest.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.angela.lollipoptest.data.News

@Dao
interface LollipopDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsList: List<News>)


    @Query("SELECT * FROM news_in_table")
    fun getAllNews():
            LiveData<List<News>>

    @Query("DELETE FROM news_in_table")
    fun deleteTable()

}

