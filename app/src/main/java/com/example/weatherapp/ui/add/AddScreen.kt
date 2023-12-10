package com.example.weatherapp.ui.add

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.room.entity.CityListEntity
import com.example.weatherapp.ui.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    weatherViewModel: WeatherViewModel,
    addViewModel: AddViewModel,
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalCity = addViewModel.addCityList.value
    var searchCity = remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            AddTopBar(onUpClick = onUpClick)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                value = searchCity.value,
                onValueChange = {
                    searchCity.apply { searchCity.value = it }
//                    addViewModel.updateCityList(searchCity.value)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    addViewModel.updateCityList(searchCity.value)
                }),
                placeholder = {
                    Text(
                        "input to search city",
                        fontSize = 16f.sp,
                        color = Color.Black
                    )
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, null)
                },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .background(Color.White)
                    .defaultMinSize(minHeight = 40.dp),
                shape = RoundedCornerShape(8.dp)
            )

            LazyColumn() {
                itemsIndexed(totalCity) { index, item ->
                    AddCityItem(
                        item = item,
                        weatherViewModel = weatherViewModel
                    )
                }
            }

        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTopBar(
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(text = "请搜索城市")
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

@Composable
fun AddCityItem(
    item: CityListEntity,
    weatherViewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp)
            .clickable { weatherViewModel.getWeather(item.ad_code) }

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "${item.ad_code}", color = Color.DarkGray, fontSize = 10.sp,
                    modifier = Modifier.padding(5.dp))

                Text(
                    text = "${item.name}",
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 20.sp
                )
            }
            Icon(
                Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.padding(end = 5.dp)
            )
        }


    }


}