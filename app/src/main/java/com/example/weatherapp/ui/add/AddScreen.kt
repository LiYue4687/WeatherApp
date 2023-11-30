package com.example.weatherapp.ui.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.weatherapp.ui.weather.WeatherViewModel

@Composable
fun AddScreen(
    weatherViewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(){
        val totalCity = weatherViewModel.totalCity.value
        itemsIndexed(totalCity){index, item ->
            Card(modifier = Modifier.clickable { weatherViewModel.getWeather(item.ad_code) }) {
                Text(text = "$index  ${item.name}")
            }
        }
    }
}