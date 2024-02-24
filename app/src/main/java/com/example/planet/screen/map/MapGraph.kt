package com.example.planet.screen.map

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.TAG
import com.example.planet.component.map.common.LockButton
import com.example.planet.component.map.trashList.TrashCard
import com.example.planet.data.map.Tabltem
import com.example.planet.viewmodel.MapViewModel
import com.naver.maps.map.MapView
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MapGraph(mapViewModel: MapViewModel = viewModel()) {

    val tabltems = listOf(
        Tabltem(
            unselectedIcon = Icons.Outlined.Delete,
            selectedIcon = Icons.Filled.Delete
        ),
        Tabltem(
            unselectedIcon = Icons.Outlined.Schedule,
            selectedIcon = Icons.Filled.Schedule
        ),
        Tabltem(
            unselectedIcon = Icons.Outlined.Map,
            selectedIcon = Icons.Filled.Map
        ),
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0) // or use mutableStateOf(0)
    }
    val pagerState = rememberPagerState { tabltems.size }
    val coroutineScope = rememberCoroutineScope()

    Log.d(TAG, "MapGraph lock실행: ${mapViewModel.lockScreenState.value}")

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }
    DisposableEffect(Unit) {
        mapViewModel.startTimer()
        mapViewModel.getAllTrashCanLocation()
        mapViewModel.getPloggingId()
        onDispose {
            mapViewModel.endTimer()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(
                selectedTabIndex = selectedTabIndex, // pagerState.currentPage
            ) {
                tabltems.forEachIndexed { index, item ->
                    Tab(
                        selected = (selectedTabIndex == index),
                        icon = {
                            Icon(
                                imageVector = if (index == selectedTabIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        },
                        onClick = {
                            selectedTabIndex = index
                            coroutineScope.launch { pagerState.animateScrollToPage(selectedTabIndex) }
                        })
                }
            }
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { index ->
                when (index) {
                    0 -> TrashListScreen()
                    1 -> RecordScreen(mapViewModel = mapViewModel)
                    2 -> MapScreen(mapViewModel = mapViewModel)
                }
            }
        }
        if (mapViewModel.lockScreenState.value) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black.copy(alpha = 0.5f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "잠금 상태입니다.",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                    LockButton(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp), imgaeVector = Icons.Default.LockOpen, unlock = { mapViewModel.unlockScreen() })


                }
            }
        }
    }
}