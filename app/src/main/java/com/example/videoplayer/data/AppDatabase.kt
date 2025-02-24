package com.example.videoplayer.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun videoDao(): VideoDao
}