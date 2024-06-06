package com.example.planet.presentation.ui.main.plogging.screen.user.screen.mypost

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.presentation.ui.component.SearchTextField
import com.example.planet.presentation.ui.main.plogging.component.MyTabRow
import com.example.planet.presentation.ui.main.plogging.screen.user.component.MyWritedTopAppBar
import com.example.planet.presentation.viewmodel.MyWritedViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyReportScreen(
    myWritedViewModel: MyWritedViewModel,
    callAllAPIs: () -> Unit,
    onBack: () -> Unit,
) {
    val tabItems = listOf("전체", "게시글", "댓글")
    val pagerState = rememberPagerState(initialPage = 0) { tabItems.size }

    LaunchedEffect(Unit) {
        callAllAPIs()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyWritedTopAppBar(
            modifier = Modifier.padding(16.dp),
            title = "내 신고내역",
            onBack = { onBack() })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            SearchTextField(
                text = { myWritedViewModel.postedInput },
                onValueChange = { myWritedViewModel.postedInput = it },
                placeholder = "글 제목",
                fontSize = 13.sp,
                modifier = Modifier.height(55.dp),
            )
        }
        MyTabRow(modifier = Modifier.padding(20.dp), pagerState = pagerState, tabItems = tabItems)
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> AllReportScreen(myWritedViewModel.allMyPosted)

                1 -> AllReportScreen(myWritedViewModel.freeMyPosted)

                2 -> AllReportScreen(myWritedViewModel.universityMyPosted)
            }
        }
    }


}