package com.example.weatherapp.ui.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.ui.widget.SwipeDeleteLayout

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CityItem(
    cityInfo: String,
    isShowDelete: Boolean = true,
    onDeleteListener: (String) -> Unit
) {
    val swipeState = rememberSwipeableState(0)
    val coroutineScope = rememberCoroutineScope()

    SwipeDeleteLayout(swipeState = swipeState, isShowChild = isShowDelete, childContent = {

    }) {
        Card( modifier = Modifier.fillMaxWidth().height(30.dp)) {
            Text(text = "$cityInfo")
        }
    }

}