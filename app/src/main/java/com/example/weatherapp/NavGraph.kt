package com.example.weatherapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.add.AddScreen
import com.example.weatherapp.ui.weather.WeatherMainScreen
import com.example.weatherapp.ui.weather.WeatherViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "WeatherMainScreen") {
        composable("WeatherMainScreen"){
            val weatherViewModel = hiltViewModel<WeatherViewModel>()
            WeatherMainScreen({navController.navigate("AddScreen")}, weatherViewModel)
        }
        //声明名为MainPage的页面路由
        composable("AddScreen"){
            //页面路由对应的页面组件
            AddScreen()
        }
    }

}