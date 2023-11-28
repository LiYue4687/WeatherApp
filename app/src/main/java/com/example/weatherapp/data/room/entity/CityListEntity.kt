package com.example.weatherapp.data.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "city_list", indices = [Index(value = ["name"], unique = true)])
class CityListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "unknown",
    val code: String = "unknown",
) {
}