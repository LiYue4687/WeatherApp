package com.example.weatherapp.data.room.repository

import android.app.Application
import com.example.weatherapp.data.room.dao.CityInfoDAO
import com.example.weatherapp.data.room.dao.CityListDAO
import com.example.weatherapp.data.room.dao.WeatherDAO
import com.example.weatherapp.data.room.database.WeatherDatabase
import com.example.weatherapp.data.room.entity.CityEntity
import com.example.weatherapp.data.room.entity.CityInfoEntity
import com.example.weatherapp.data.room.entity.CityListEntity
import com.example.weatherapp.data.room.entity.ForecastEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class WeatherRepository @Inject constructor(private val context: Application) {
    private val weatherDAO: WeatherDAO = WeatherDatabase.getDatabase(context).weatherDAO()
    private val cityListDAO: CityListDAO = WeatherDatabase.getDatabase(context).cityListDAO()
    private val cityInfoDAO: CityInfoDAO = WeatherDatabase.getDatabase(context).cityInfoDAO()

    fun getCities(): List<String> {
        return weatherDAO.getCities()
    }

    fun getWeatherByCode(code: String): List<ForecastEntity> {
        return weatherDAO.getWeatherByCode(code)
    }

    fun getWeatherByCityName(name: String): List<ForecastEntity> {
        return weatherDAO.getWeatherByCityName(name)
    }


    fun getWeatherByUid(uid: String): ForecastEntity {
        return weatherDAO.getWeatherByUid(uid)
    }

    fun insertCity(cityEntity: CityEntity){
        weatherDAO.insertCity(cityEntity)
    }

    fun insertWeatherForecast(forecastEntity: ForecastEntity) {
        weatherDAO.insertWeatherForecast(forecastEntity)
    }

    fun insertCityList(cityListEntity: CityListEntity){
        cityListDAO.insert(cityListEntity)
    }

    fun insertCityInfo(cityInfoEntity: CityInfoEntity){
        cityInfoDAO.insert(cityInfoEntity)
    }

    fun updateWeatherForecast(forecastEntity: ForecastEntity) {
        weatherDAO.updateWeatherForecast(forecastEntity)
    }

}