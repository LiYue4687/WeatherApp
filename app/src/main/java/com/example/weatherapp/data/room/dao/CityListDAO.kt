package com.example.weatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.room.entity.CityListEntity

@Dao
interface CityListDAO {
    @Query("SELECT * FROM city_list")
    fun getAll(): List<CityListEntity>

    @Query("SELECT * FROM city_list WHERE city_list.name = :name" )
    fun selectByName(name:String): List<CityListEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cityListEntity: CityListEntity)
}