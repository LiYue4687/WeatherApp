package com.example.weatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.room.entity.CityInfoEntity

@Dao
interface CityInfoDAO {
    @Query("SELECT * FROM city_info")
    fun getAll(): List<CityInfoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cityInfoEntity:CityInfoEntity)
}