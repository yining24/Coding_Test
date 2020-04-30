package com.angela.lollipoptest.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.angela.lollipoptest.data.News

/**
 * A database that stores information.
 * And a global method to get access to the database.
 *
 */
@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class LollipopDatabase : RoomDatabase() {

    abstract val lollipopDatabaseDao: LollipopDatabaseDao

    companion object {
        /**
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: LollipopDatabase? = null

        fun getInstance(context: Context): LollipopDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LollipopDatabase::class.java,
                        "lollipop_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
