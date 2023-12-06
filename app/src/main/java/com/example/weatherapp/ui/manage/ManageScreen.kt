package com.example.weatherapp.ui.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weatherapp.ui.weather.WeatherViewModel
import com.example.weatherapp.ui.widget.CityManageItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageScreen(
    weatherViewModel: WeatherViewModel,
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cityList = weatherViewModel.cityList


    Scaffold(
        topBar = {
            ManageTopBar(onUpClick = onUpClick)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = com.example.weatherapp.R.drawable.background),
                contentDescription = "",
                contentScale = ContentScale.FillBounds, // or some other scale
                modifier = Modifier.matchParentSize()
            )
        }
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            itemsIndexed(cityList.value) { index, it ->
                CityManageItem(it) { weatherViewModel.delCity(it) }
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManageTopBar(
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(text = "左滑删除城市")
        },
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
    )
}



