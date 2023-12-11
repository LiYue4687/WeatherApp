package com.example.weatherapp.ui.weather

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.retrofit.WeatherClient
import com.example.weatherapp.data.retrofit.entity.CastItem
import com.example.weatherapp.data.retrofit.entity.Forecast
import com.example.weatherapp.data.room.entity.CityEntity
import com.example.weatherapp.data.room.entity.CityListEntity
import com.example.weatherapp.data.room.entity.ForecastEntity
import com.example.weatherapp.data.room.repository.WeatherRepository
import com.example.weatherapp.ui.weather.util.ClassTransUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    application: Application,
    private val weatherRepository: WeatherRepository
) : AndroidViewModel(application) {
    var cityList: MutableState<List<CityEntity>> = mutableStateOf(listOf())

    val totalCity: MutableState<List<CityListEntity>> = mutableStateOf(listOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fresh()
        }
    }


    var weatherState: MutableMap<String, MutableState<WeatherState>> =
        mutableStateMapOf()

    // quest weather information by retrofit
    suspend fun getWeather(adCode: String) {
        val weatherResponse = WeatherClient.weatherAPI.getWeather(
            "9ae5b2161dee449c6594537c48394902",
            adCode, "all"
        )
        for (forecast in weatherResponse.forecasts) {
            buildAndInsertCity(forecast)
            for (i in forecast.casts.indices) {
                insetOrUpdateWeatherForecast(
                    ClassTransUtil.buildWeatherForecast(
                        i,
                        forecast,
                        forecast.casts[i]
                    )
                )
            }
        }
    }

    private suspend fun buildAndInsertCity(forecast: Forecast) {
        val name = forecast.province + "  " + forecast.city
        if (!containCity(name)) {
            weatherRepository.insertCity(
                CityEntity(
                    name = name,
                    code = forecast.adcode
                )
            )
        }
    }

    private fun containCity(name: String): Boolean {
        cityList.value.forEach {
            if (it.name == name) return true
        }
        return false
    }


    private suspend fun insetOrUpdateWeatherForecast(forecastEntity: ForecastEntity) {
        if (weatherRepository.getWeatherByUid(forecastEntity.uid) != null) {
            weatherRepository.updateWeatherForecast(forecastEntity)
        } else {
            weatherRepository.insertWeatherForecast(forecastEntity)
        }
    }

    suspend fun fresh() {
        val cities = weatherRepository.getCities()
        val stateList: MutableList<WeatherState> = mutableListOf()
        val total = getTotalCity()
        for (city in cities) {
            getWeather(city.code)
        }
        for (city in cities) {
            stateList.add(
                ClassTransUtil.translateSqlResultToSate(
                    city.name,
                    weatherRepository.getWeatherByCityName(city.name)
                )
            )
        }

        viewModelScope.launch(Dispatchers.Main) {
            cityList.apply {
                cityList.value = cities
            }
            totalCity.apply {
                totalCity.value = total
            }
            weatherState.apply {
                weatherState.clear()
            }
            for ((index, city) in cities.withIndex()) {
                weatherState.apply {
                    weatherState[city.name] = mutableStateOf(stateList[index])
                }
            }
        }
    }

    fun delCity(city: CityEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.delCity(city)
        }
    }

    fun getTotalCity(): List<CityListEntity> {
        return weatherRepository.getTotalCity()
    }

}