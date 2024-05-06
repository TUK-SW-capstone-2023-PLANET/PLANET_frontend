package com.example.planet.presentation.ui.plogging.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planet.TAG
import com.example.planet.component.common.PloggingDialog
import com.example.planet.component.map.common.LockButton
import com.example.planet.data.map.Tabltem
import com.example.planet.data.remote.dto.response.plogging.Trash
import com.example.planet.presentation.viewmodel.PloggingViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MapGraph(ploggingViewModel: PloggingViewModel = viewModel(), onClick: (Int) -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) Log.d(TAG, "take picture 성공")
            // 사진 저장하는 API 호출
            ploggingViewModel.postImage()
        }

    val tabltems = listOf(
        Tabltem(
            unselectedIcon = Icons.Outlined.Map,
            selectedIcon = Icons.Filled.Map,
        ),
        Tabltem(
            unselectedIcon = Icons.Outlined.Schedule,
            selectedIcon = Icons.Filled.Schedule
        ),
        Tabltem(
            unselectedIcon = Icons.Outlined.Delete,
            selectedIcon = Icons.Filled.Delete
        ),
    )


    val pagerState = rememberPagerState {
        tabltems.size
    }

    LaunchedEffect(Unit) {
        while (true) {
            ploggingViewModel.pastLatLng = ploggingViewModel.currentLatLng
            delay(5000)
//            Log.d(TAG, "계산중: ${mapViewModel.distance.value}")
//            Log.d(TAG, "pastLocation: ${mapViewModel.pastLatLng}")
//            Log.d(TAG, "currentLocation: ${mapViewModel.currentLatLng}")
            ploggingViewModel.distanceCalculate()

        }
    }
    LaunchedEffect(ploggingViewModel.kcal.value) {
        ploggingViewModel.kcalCalculate()
    }

    DisposableEffect(Unit) {
        ploggingViewModel.startTimer()
        ploggingViewModel.getAllTrashCanLocation()
        onDispose {
            ploggingViewModel.endTimer()
        }
    }

    BackHandler {
        ploggingViewModel.displayOnDialog()
        ploggingViewModel.pauseTimer()
    }

    // 탑 앱바 적용하면 RecordScreen 화면 카메라, 잠금 버튼 뭉게짐
//    Scaffold(topBar = {
//        CenterAlignedTopAppBar(title = {
//            Text(
//                text = "2023년 10월 24일",
//                color = colorResource(id = R.color.font_background_color1),
//                fontSize = 14.sp
//            )
//        })
//    })
    if (ploggingViewModel.dialogState.value) {
        PloggingDialog(ploggingViewModel = ploggingViewModel) { ploggingId ->
            onClick(ploggingId)
        }
        ploggingViewModel.pauseTimer()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = pagerState.currentPage,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    )
                }) {
                tabltems.forEachIndexed { index, item ->
                    Tab(
                        selected = (pagerState.currentPage == index),
                        icon = {
                            Icon(
                                imageVector = if (index == pagerState.currentPage) item.selectedIcon else item.unselectedIcon,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        },
                        onClick = {
//                            selectedTabIndex = index
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    index
                                )
                            }
                        })
                }
            }
            HorizontalPager(state = pagerState) { index ->
                when (index) {
                    0 -> MapScreen(ploggingViewModel = ploggingViewModel, cameraLauncher = cameraLauncher)
                    1 -> RecordScreen(
                        ploggingViewModel = ploggingViewModel,
                        cameraLauncher = cameraLauncher
                    )

                    2 -> TrashListScreen(trashList = ploggingViewModel.trashList.value)
                }
            }
        }
        if (ploggingViewModel.lockScreenState.value) {
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
                    LockButton(modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                        imgaeVector = Icons.Default.LockOpen,
                        unlock = { ploggingViewModel.unlockScreen() })
                }
            }
        }
    }

}