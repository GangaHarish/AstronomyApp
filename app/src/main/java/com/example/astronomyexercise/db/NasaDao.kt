package com.example.astronomyexercise.db

import androidx.room.*
import com.example.astronomyexercise.data.models.NasaItem

@Dao
interface NasaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: NasaItem?)

    @Delete
    suspend fun delete(item: NasaItem?)

    @Query("SELECT * FROM saved_images")
    suspend fun getAllImages(): List<NasaItem>?
}