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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.weatherapp.ui.widget.HorizontalPagerIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun WeatherMainScreen(
    toAddScreen: () -> Unit,
    toManageScreen: () -> Unit,
    weatherViewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {
    var cityList = weatherViewModel.cityList
    var weatherState = weatherViewModel.weatherState

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    )

    // 在更新内容后调用 animateScrollToPage 来切换到第一个的页面
    LaunchedEffect(cityList) {
        pagerState.animateScrollToPage(0)
    }

    var refreshing by remember {
        mutableStateOf(false)
    }
    // 用协程模拟一个耗时加载
    val scope = rememberCoroutineScope()
    val state = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        scope.launch(Dispatchers.IO) {
            refreshing = true
            val result = async { weatherViewModel.fresh() }
            result.await()
            refreshing = false
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state)
    ) {
        // use coil to load image
//        AsyncImage(
//            model = "https://pic-go-bed.oss-cn-beijing.aliyuncs.com/img/20220316151929.png",
//            contentDescription = null,
//            modifier = Modifier.matchParentSize()
//        )

        Image(
            painter = painterResource(id = R.drawable.sunbackground),
            contentDescription = "",
            contentScale = ContentScale.FillBounds, // or some other scale
            modifier = Modifier.matchParentSize()
        )

        HorizontalPager(modifier = modifier,
            state = pagerState,
            pageCount = cityList.value.size,
            pageContent = { page ->
                val curPosition = cityList.value[page].name
                WeatherMainBody(
                    curPosition, weatherState[curPosition]!!,
                    modifier = Modifier.absolutePadding(
                        left = 15.dp, right = 15.dp
                    )
                )
            })


        // need to in the after of HorizontalPager
        // for on the upper layer of HorizontalPager
        WeatherTopBar(toAddScreen, toManageScreen)

        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = cityList.value.size,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
        )

        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))

    }
}

@Composable
fun WeatherMainBody(
    curPosition: String,
    weatherState: MutableState<WeatherState>,
    modifier: Modifier = Modifier
) {

    val weatherByDay = weatherState.value.weatherByDay
    val weatherByHour = weatherState.value.weatherByHour
    val curWeather = weatherState.value.weatherByDay[0]
    val airScore = weatherState.value.airScore
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$curPosition", fontSize = 30.sp)
        Text(text = "${curWeather.first}  ${curWeather.second}°C", fontSize = 40.sp)
//        Image(
//            painter = painterResource(id = R.drawable.icon_rain),
//            contentDescription = "",
//            modifier = Modifier.size(100.dp)
//        )
        WeatherAnimation(curWeather.first)
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
fun WeatherAnimation(weather: String) {
    var raw: Int = when (weather) {
        "晴" -> R.raw.sun
        "阴", "多云" -> R.raw.cloud
        "雨", "小雨", "中雨", "大雨" -> R.raw.rain_day
        "雷雨" -> R.raw.rain_thunder
        "雪", "小雪", "中雪", "大雪", "雨夹雪" -> R.raw.snow
        else -> R.raw.sun
    }
    // load Lottie resource
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(raw)
    )
    var animationLoaded by remember { mutableStateOf(false) }
    if (animationLoaded) {
        LottieAnimation(
            lottieComposition,
            restartOnPlay = true,// 暂停后重新播放是否从头开始
            iterations = LottieConstants.IterateForever, // 设置循环播放次数
            modifier = Modifier
                .size(200.dp) // Set the size you want for your Lottie animation
        )
    } else {
        // Placeholder size
        Box(modifier = Modifier.size(200.dp))
    }

    // Trigger the flag once the Lottie animation is loaded
    LaunchedEffect(lottieComposition) {
        animationLoaded = true
    }
}


