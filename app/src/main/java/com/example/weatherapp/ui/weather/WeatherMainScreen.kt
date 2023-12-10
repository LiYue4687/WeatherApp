package com.example.weatherapp.ui.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherMainScreen(
    toAddScreen: () -> Unit,
    toManageScreen: () -> Unit,
    weatherViewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    val cityList = weatherViewModel.cityList

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sunbackground),
            contentDescription = "",
            contentScale = ContentScale.FillBounds, // or some other scale
            modifier = Modifier.matchParentSize()
        )
    }

    HorizontalPager(modifier = modifier,
        state = pagerState,
        pageCount = weatherViewModel.cityList.value.size,
        pageContent = { page ->
            val curPosition = cityList.value[page].name
            WeatherMainBody(
                curPosition, weatherViewModel.getCurWeather(curPosition),
                weatherViewModel.getAirScore(curPosition),
                weatherViewModel.getWeatherByHour(curPosition),
                weatherViewModel.getWeatherByDay(curPosition),
                modifier = Modifier.absolutePadding(
                    left = 15.dp, right = 15.dp
                )
            )
        })
    // need to in the after of HorizontalPager
    // for on the upper layer of HorizontalPager
    WeatherTopBar(toAddScreen,toManageScreen)

}

@Composable
fun WeatherMainBody(
    curPosition: String,
    curWeather: String,
    airScore: Int,
    weatherByHour: List<Pair<String, Int>>,
    weatherByDay: List<Triple<String, Int, Int>>,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$curPosition", fontSize = 30.sp)
        Text(text = "$curWeather", fontSize = 40.sp)
//        Image(
//            painter = painterResource(id = R.drawable.icon_rain),
//            contentDescription = "",
//            modifier = Modifier.size(100.dp)
//        )
        WeatherAnimation(curWeather)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "空气质量")
                Text(text = "$airScore")
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "今日天气")
                Divider()
                LazyRow {
                    itemsIndexed(weatherByHour) { index, pair ->
                        Column(
                            modifier = Modifier.width(60.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (index == 0) {
                                Text(text = "现在")
                            } else {

                                Text(text = "$index")
                            }
                            Text(text = "${pair.first}")
                            Text(text = "${pair.second}°C")
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "7日天气")
                Divider()
                LazyColumn {
                    itemsIndexed(weatherByDay) { index, triple ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (index == 0) {
                                Text(
                                    text = "今天",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                Text(
                                    text = "$index",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Text(
                                text = "${triple.first}",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "${triple.second}°C",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "${triple.third}°C",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

        }


    }
}

@Composable
fun WeatherTopBar(
    onAddClick: () -> Unit,
    onManageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .systemBarsPadding(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { onAddClick.invoke() }) {
            Icon(Icons.Filled.Add, null)
        }
        IconButton(onClick = { onManageClick.invoke() }) {
            Icon(Icons.Filled.Menu, null)
        }
    }
}



@Composable
fun WeatherAnimation(weather:String) {
    var raw:Int = when (weather) {
        "晴" -> R.raw.sun
        "阴" -> R.raw.cloud
        "多云" -> R.raw.cloud
        "雨", "小雨", "大雨" -> R.raw.rain_day
        "雷雨" -> R.raw.rain_thunder
        "雪" -> R.raw.snow
        else -> R.raw.sun
    }
    // 加在 Lottie资源
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(raw)
    )
    LottieAnimation(
        lottieComposition,
        restartOnPlay = true,// 暂停后重新播放是否从头开始
        iterations = LottieConstants.IterateForever, // 设置循环播放次数
    )
}


