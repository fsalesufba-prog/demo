package com.demo.streamflix.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.streamflix.data.local.dao.ChannelDao
import com.demo.streamflix.data.local.dao.CategoryDao
import com.demo.streamflix.data.local.dao.FavoriteDao
import com.demo.streamflix.data.local.dao.UserDao
import com.demo.streamflix.data.local.entity.CategoryEntity
import com.demo.streamflix.data.local.entity.ChannelEntity
import com.demo.streamflix.data.local.entity.FavoriteEntity
import com.demo.streamflix.data.local.entity.UserEntity
import com.demo.streamflix.data.local.converters.DateConverter
import com.demo.streamflix.util.Constants

@Database(
    entities = [
        ChannelEntity::class,
        CategoryEntity::class,
        FavoriteEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun channelDao(): ChannelDao
    abstract fun categoryDao(): CategoryDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}