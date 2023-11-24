package com.example.weatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.data.room.entity.CityEntity
import com.example.weatherapp.data.room.entity.ForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDAO {
    @Query("SELECT name FROM city")
    fun getCities(): List<String>

    @Query("SELECT * FROM forecast WHERE forecast.code = :code ORDER BY date LIMIT 3")
    fun getWeatherByCode(code: String): List<ForecastEntity>

    @Query("SELECT * FROM forecast WHERE " +
            "forecast.code = (SELECT code FROM city WHERE city.name = :name LIMIT 1)" +
            "ORDER BY date LIMIT 3")
    fun getWeatherByCityName(name: String): List<ForecastEntity>

    @Query("SELECT * FROM forecast WHERE uid = :uid")
    fun getWeatherByUid(uid: String): ForecastEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(cityEntity: CityEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeatherForecast(forecastEntity: ForecastEntity)

    @Update
    fun updateWeatherForecast(forecastEntity: ForecastEntity)
}