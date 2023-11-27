package com.example.weatherapp.ui.weather

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WeatherMainScreen(
    toAddScreen: () -> Unit,
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
            painter = painterResource(id = R.drawable.background),
            contentDescription = "",
            contentScale = ContentScale.FillBounds, // or some other scale
            modifier = Modifier.matchParentSize()
        )
    }

    HorizontalPager(modifier = modifier,
        state = pagerState,
        pageCount = weatherViewModel.cityList.value.size,
        pageContent = { page ->
            val curPosition = cityList.value[page]
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
    WeatherTopBar(toAddScreen, toAddScreen)

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
        Image(
            painter = painterResource(id = R.drawable.icon_rain),
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
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


