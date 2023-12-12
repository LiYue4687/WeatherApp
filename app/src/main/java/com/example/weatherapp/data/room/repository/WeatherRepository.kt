package com.example.weatherapp.data.room.repository

import android.app.Application
import android.util.Log
import com.example.weatherapp.data.room.dao.CityInfoDAO
import com.example.weatherapp.data.room.dao.CityListDAO
import com.example.weatherapp.data.room.dao.CitySearch
import com.example.weatherapp.data.room.dao.CitySearchDAO
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
    private val citySearchDAO: CitySearchDAO = WeatherDatabase.getDatabase(context).citySearchDAO()

    fun getCities(): List<CityEntity> {
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

    fun insertCityList(cityListEntity: CityListEntity){
        cityListDAO.insert(cityListEntity)
    }

    fun insertCityInfo(cityInfoEntity: CityInfoEntity){
        cityInfoDAO.insert(cityInfoEntity)
    }
    fun insertWeatherForecast(forecastEntity: ForecastEntity) {
        weatherDAO.insertWeatherForecast(forecastEntity)
    }

    fun updateWeatherForecast(forecastEntity: ForecastEntity) {
        weatherDAO.updateWeatherForecast(forecastEntity)
    }

    fun delCity(cityEntity: CityEntity){
        weatherDAO.delCity(cityEntity)
    }

    fun getTotalCity(): List<CityListEntity>{
        return cityListDAO.getAll()
    }

    fun getTotalCityByName(name:String): List<CityListEntity>{
        return cityListDAO.selectByName(name)
    }

    fun getSearchCityByName(name:String): List<CitySearch>{
        return citySearchDAO.selectByName(name)
    }

}