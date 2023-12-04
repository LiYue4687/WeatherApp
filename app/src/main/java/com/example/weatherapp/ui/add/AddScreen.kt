package com.example.weatherapp.ui.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        TextField(
            value = searchCity.value,
            onValueChange = {
                searchCity.apply { searchCity.value = it }
                addViewModel.updateCityList(searchCity.value)
            },
            singleLine = true,
            placeholder = { Text("input to search cit", fontSize = 16f.sp, color = Color.Black) },
//            leadingIcon = {  },
//            trailingIcon = { },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .background(Color.White)
                .defaultMinSize(minHeight = 40.dp),
            shape = RoundedCornerShape(8.dp)
        )
        LazyColumn(modifier = Modifier.padding(paddingValues)) {


            itemsIndexed(totalCity) { index, item ->
                Card(modifier = Modifier.clickable { weatherViewModel.getWeather(item.ad_code) }) {
                    Text(text = "$index  ${item.name}")
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