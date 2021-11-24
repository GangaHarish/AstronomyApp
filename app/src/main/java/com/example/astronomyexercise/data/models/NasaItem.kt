package com.example.astronomyexercise.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_images")
data class NasaItem(
    @ColumnInfo(name = "title")
    var name: String?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "url")
    var url: String?,
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int? = 1
}
