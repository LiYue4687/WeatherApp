package com.example.weatherapp.ui.add

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.room.dao.CitySearch
import com.example.weatherapp.data.room.entity.CityEntity
import com.example.weatherapp.data.room.entity.CityListEntity
import com.example.weatherapp.data.room.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    application: Application,
    private val weatherRepository: WeatherRepository
) : AndroidViewModel(application) {
    val addCityList: MutableState<List<CitySearch>> = mutableStateOf(listOf())

    fun updateCityList(name :String){
        viewModelScope.launch(Dispatchers.IO) {
            addCityList.apply {
                addCityList.value = weatherRepository.getSearchCityByName(name)
            }
        }
    }

}