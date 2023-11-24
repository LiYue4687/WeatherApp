package com.example.weatherapp.data.room.repository

import android.app.Application
import com.example.weatherapp.data.room.dao.WeatherDAO
import com.example.weatherapp.data.room.database.WeatherDatabase
import com.example.weatherapp.data.room.entity.CityEntity
import com.example.weatherapp.data.room.entity.ForecastEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class WeatherRepository @Inject constructor(private val context: Application) {
    private val weatherDAO: WeatherDAO = WeatherDatabase.getDatabase(context).weatherDAO()
    fun getCities(): List<String> {
        return weatherDAO.getCities()
    }

    fun getWeatherByCode(code: String): List<ForecastEntity> {
        return weatherDAO.getWeatherByCode(code)
    }

    fun getWeatherByCityName(name: String): List<ForecastEntity> {
        return weatherDAO.getWeatherByCityName(name)
    }

    fun insertCity(cityEntity: CityEntity){
        weatherDAO.insertCity(cityEntity)
    }

    fun insertWeatherForecast(forecastEntity: ForecastEntity) {
        weatherDAO.insertWeatherForecast(forecastEntity)
    }

    fun getWeatherByUid(uid: String): ForecastEntity {
        return weatherDAO.getWeatherByUid(uid)
    }

    fun updateWeatherForecast(forecastEntity: ForecastEntity) {
        weatherDAO.updateWeatherForecast(forecastEntity)
    }

}