package com.example.planet.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.R
import com.example.planet.component.common.MyScrollableTabRow
import com.example.planet.component.main.MainTopSwitch
import com.example.planet.screen.main.plogging.PloggingHelpScreen
import com.example.planet.screen.main.plogging.UniversityScreen
import com.example.planet.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel(), onClick: () -> Unit) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabItems = listOf("나의 플로깅", "대학교", "시즌", "이벤트")

    LaunchedEffect(Unit) {
        mainViewModel.getAdvertisement()
        mainViewModel.getUniversityPeople()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.White,
                containerColor = colorResource(id = R.color.main_color1),
                shape = CircleShape,
                onClick = { onClick() }) {
                Icon(
                    imageVector = Icons.Rounded.DirectionsRun,
                    contentDescription = null,
                    modifier = Modifier.size(33.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainTopSwitch(mainViewModel = mainViewModel)
            /* TODO("스켈레톤 ui 적용할 것") */
            if (mainViewModel.imageUrlList.isNotEmpty()) {
                MainTopBanner(imageUrlList = mainViewModel.imageUrlList)
            }
            MyScrollableTabRow(
                modifier = Modifier
                    .height(45.dp)
                    .padding(vertical = 8.dp),
                edgePadding = 16.dp,
                selectedTabIndex = pagerState.currentPage,
                minItemWidth = 0.dp,
                indicator = {},
                divider = {}) {
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
                            modifier = Modifier,
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
            Divider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            HorizontalPager(count = tabItems.count(), state = pagerState) { page ->
                when (page) {
                    0 -> PloggingHelpScreen()
                    1 -> UniversityScreen(universityPersonList = mainViewModel.universityPerson)
                    2 -> Text(text = "$page", modifier = Modifier.fillMaxSize())
                    3 -> Text(text = "$page", modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
