package com.example.planet.presentation.ui.main.plogging.screen.home.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.component.main.plogging.MainTopBanner
import com.example.planet.presentation.ui.main.plogging.component.MyTabRow
import com.example.planet.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    onClick: () -> Unit
) {
    Log.d(TAG, "MainScreen 실행")

    val tabItems = listOf("나의 플로깅", "대학교", "시즌", "이벤트")
    val pagerState = rememberPagerState(initialPage = 0) { tabItems.size }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                onClick = { onClick() }) {
                Icon(
                    imageVector = Icons.Rounded.DirectionsRun,
                    contentDescription = null,
                    modifier = Modifier.size(33.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /* TODO("스켈레톤 ui 적용할 것") */
            if (mainViewModel.imageUrlList.isEmpty()) {
                CircularProgressIndicator(color = Color.Black)
            } else {
                MainTopBanner { mainViewModel.imageUrlList }
            }
            MyTabRow(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                pagerState = pagerState,
                tabItems = tabItems
            )

            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> PloggingHelpScreen()
                    1 -> UniversityScreen(
                        mainViewModel = mainViewModel,
                        universityUserList = { mainViewModel.higherMyUniversityUsers },
                        universityList = { mainViewModel.higherUniversity },
                        graphHeightList = { mainViewModel.universityGraphHeightList },
                        goUniversityScreen = { screen -> mainViewModel.showRankingScreen = screen }
                    ) { screen -> mainViewModel.showRankingScreen = screen }

                    2 -> SeasonScreen(navController = navController, mainViewModel = mainViewModel)
                    3 -> TestScreen()
                }
            }
        }
    }
}

@Composable
fun TestScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "테스트")
    }
}