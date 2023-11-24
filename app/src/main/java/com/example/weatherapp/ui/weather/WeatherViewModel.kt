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
import com.example.weatherapp.ui.data.retrofit.WeatherClient
import com.example.weatherapp.ui.data.retrofit.entity.CastItem
import com.example.weatherapp.ui.data.retrofit.entity.Forecast
import com.example.weatherapp.ui.data.room.entity.CityEntity
import com.example.weatherapp.ui.data.room.entity.ForecastEntity
import com.example.weatherapp.ui.data.room.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    application: Application,
    private val weatherRepository: WeatherRepository
) : AndroidViewModel(application) {
    val cityList = mutableStateOf(listOf("北京"))

    private var weatherState =
        mutableStateMapOf(
            "北京" to
                    mutableStateOf(
                        WeatherState(
                            "北京",
                            10,
                            0.40f,
                            0.40f,
                            Pair<String, String>("6:00", "18:00"),
                            listOf(
                                Pair<String, Int>("sun", 10),
                                Pair<String, Int>("sun", 12),
                                Pair<String, Int>("sun", 12)
                            ),
                            listOf(
                                Triple<String, Int, Int>("sun", 10, 20),
                                Triple<String, Int, Int>("sun", 8, 20)
                            )
                        )
                    )
        )

    fun addPosition(
        newPosition: String = "天津", weather: MutableState<WeatherState> = mutableStateOf(
            WeatherState(
                "天津",
                20,
                0.60f,
                0.40f,
                Pair<String, String>("6:00", "18:00"),
                listOf(
                    Pair<String, Int>("sun", 30),
                    Pair<String, Int>("sun", 22),
                    Pair<String, Int>("sun", 32)
                ),
                listOf(
                    Triple<String, Int, Int>("sun", 10, 20),
                    Triple<String, Int, Int>("sun", 8, 20)
                )
            )
        )
    ) {
        weatherState.apply { weatherState[newPosition] = weather }
        Log.i("myTest", weatherState.toString())
        if (!cityList.value.contains(newPosition)) cityList.apply {
            cityList.value = cityList.value + newPosition
        }
    }

    fun getCurWeather(curPosition: String): String {
        Log.i("myTest", curPosition)
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
            Log.d("myTest", "weatherResponse: $weatherResponse")
            for (forecast in weatherResponse.forecasts) {
                Log.d("myTest", "weatherResponse: $forecast")
                buildAndInsertCity(forecast)
                for (i in forecast.casts.indices) {
                    Log.d("myTest", "weatherResponse: ${forecast.casts[i]}")
                    insetOrUpdateWeatherForecast(buildWeatherForecast(i, forecast, forecast.casts[i]))
                }
            }

        }
    }

    private suspend fun buildAndInsertCity(forecast: Forecast){
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

    private fun buildWeatherForecast(count:Int, forecast: Forecast, cast: CastItem): ForecastEntity {
        return ForecastEntity(
            forecast.province + "_" + forecast.city + "_" + count,
            forecast.adcode,
            cast.date,
            cast.dayweather,
            cast.nightweather,
            cast.daytemp,
            cast.nighttemp,
            cast.daywind,
            cast.nightwind,
            cast.daypower,
            cast.nightpower
        )
    }

    private suspend fun insetOrUpdateWeatherForecast(forecastEntity: ForecastEntity){
        if(weatherRepository.getWeatherByUid(forecastEntity.uid)!=null){
            weatherRepository.updateWeatherForecast(forecastEntity)
        }
        else{
            weatherRepository.insertWeatherForecast(forecastEntity)
        }
    }

}