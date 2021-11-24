package com.example.astronomyexercise.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.astronomyexercise.data.models.NasaItem

@Database(
    entities = [NasaItem::class],
    version = 1,
    exportSchema = false
)
abstract class NasaDatabase : RoomDatabase() {

    abstract fun getNasaDao(): NasaDao
}