package com.example.weatherapp.ui.weather

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
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
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    application: Application,
    private val weatherRepository: WeatherRepository
) : AndroidViewModel(application) {
    val cityList: MutableState<List<String>> = mutableStateOf(listOf())

    init {
        init()
    }

    private var weatherState:MutableMap<String, MutableState<WeatherState>> =
        mutableStateMapOf()

    fun addPosition() {
        /* TODO */
    }

    fun getCurWeather(curPosition: String): String {
//        Log.i("myTest", curPosition)
        val weather: String =
            weatherState[curPosition]?.value?.weatherByHour?.get(0)?.first ?: "unknown"
        val value: Int = weatherState[curPosition]?.value?.weatherByHour?.get(0)?.second ?: 0
        return "$weather  ${value}°C"
    }

    fun getAirScore(curPosition: String): Int {
        return weatherState[curPosition]?.value?.airScore ?: 0
    }

    fun getWeatherByHour(curPosition: String): List<Pair<String, Int>> {
        return weatherState[curPosition]?.value?.weatherByHour ?: listOf<Pair<String, Int>>()
    }

    fun getWeatherByDay(curPosition: String): List<Triple<String, Int, Int>> {
        return weatherState[curPosition]?.value?.weatherByDay ?: listOf<Triple<String, Int, Int>>()
    }


    // quest weather information by retrofit
    fun getWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherResponse = WeatherClient.weatherAPI.getWeather(
                "9ae5b2161dee449c6594537c48394902",
                "110101", "all"
            )
//            Log.d("myTest", "weatherResponse: $weatherResponse")
            for (forecast in weatherResponse.forecasts) {
//                Log.d("myTest", "weatherResponse: $forecast")
                buildAndInsertCity(forecast)
                for (i in forecast.casts.indices) {
//                    Log.d("myTest", "weatherResponse: ${forecast.casts[i]}")
                    insetOrUpdateWeatherForecast(
                        ClassTransUtil.buildWeatherForecast(
                            i,
                            forecast,
                            forecast.casts[i]
                        )
                    )
                }
            }

            init()
        }
    }

    private suspend fun buildAndInsertCity(forecast: Forecast) {
        val name = forecast.province + "  " + forecast.city
        if (!cityList.value.contains(name)) {
            weatherRepository.insertCity(
                CityEntity(
                    name = name,
                    code = forecast.adcode
                )
            )
        }
    }


    private suspend fun insetOrUpdateWeatherForecast(forecastEntity: ForecastEntity) {
        if (weatherRepository.getWeatherByUid(forecastEntity.uid) != null) {
            weatherRepository.updateWeatherForecast(forecastEntity)
        } else {
            weatherRepository.insertWeatherForecast(forecastEntity)
        }
    }

    private fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            val cities = weatherRepository.getCities()
            val stateList: MutableList<WeatherState> = mutableListOf()
            for (city in cities) {
//                Log.i("myTest", "")
                stateList.add(
                    ClassTransUtil.translateSqlResultToSate(
                        city,
                        weatherRepository.getWeatherByCityName(city)
                    )
                )
            }

            viewModelScope.launch(Dispatchers.Main) {
                cityList.apply {
                    cityList.value = cities
                }
                for ((index, city) in cities.withIndex()) {
                    weatherState.apply {
                        weatherState[city] = mutableStateOf(stateList[index])
                    }
                }
            }
        }
    }

}