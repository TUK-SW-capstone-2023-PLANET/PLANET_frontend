package com.example.planet.presentation.ui.main.plogging.screen.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainTapRow(pagerState: PagerState, tabItems: List<String>) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        modifier = Modifier
            .height(45.dp)
            .padding(vertical = 8.dp),
        selectedTabIndex = pagerState.currentPage,
    ) {
        tabItems.forEachIndexed { index, title ->
            Card(
                modifier = Modifier
                    .padding(end = if (index != 3) 8.dp else 0.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (pagerState.currentPage == index) colorResource(id = R.color.main_color2)
                    else colorResource(id = R.color.font_background_color3),
                    contentColor = if (pagerState.currentPage == index) Color.White
                    else colorResource(id = R.color.font_background_color2)
                )
            ) {
                Tab(
                    selected = (pagerState.currentPage == index),
                    text = { Text(text = title, fontSize = 11.sp) },
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    })
            }
        }
    }
}