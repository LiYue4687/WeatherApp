package com.example.weatherapp.ui.weather

class WeatherState(val position:String, val airScore:Int, val rainProbability:Float, val humidity:Float,
                   val sunRiseAndFall:Pair<String, String>,
                   var weatherByHour:List<Pair<String,Int>>,
                   var weatherByDay:List<Triple<String,Int,Int>>) {
}

data class WeatherStateMap(val weatherMap:Map<String, WeatherState>){
//    val weatherMap:Map<String, WeatherState>
}