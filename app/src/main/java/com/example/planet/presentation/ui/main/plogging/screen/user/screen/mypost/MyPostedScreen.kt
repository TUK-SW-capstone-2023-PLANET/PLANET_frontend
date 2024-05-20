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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPostedScreen(
    myWritedViewModel: MyWritedViewModel,
    title: String,
    callAllAPIs: () -> Unit,
    onBack: () -> Unit,
    startPostedInfoActivity: (Long, String) -> Unit
) {
    val scroll = rememberScrollState()
    val tabItems = listOf("전체", "자유", "대학교")
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
            title = title,
            onBack = { onBack() })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            SearchTextField(
                text = myWritedViewModel.postedInput,
                onValueChange = { myWritedViewModel.postedInput = it },
                placeholder = "글 제목",
                fontSize = 13.sp,
                modifier = Modifier.height(55.dp),
            )
        }
        MyTabRow(modifier = Modifier.padding(20.dp), pagerState = pagerState, tabItems = tabItems)
//        MainTapRow(pagerState = pagerState, tabItems = tabItems)
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> AllPostedScreen(myWritedViewModel.allMyPosted) { postId, board ->
                    startPostedInfoActivity(
                        postId,
                        board
                    )
                }

                1 -> AllPostedScreen(myWritedViewModel.freeMyPosted) { postId, board ->
                    startPostedInfoActivity(
                        postId,
                        board
                    )
                }

                2 -> AllPostedScreen(myWritedViewModel.universityMyPosted) { postId, board ->
                    startPostedInfoActivity(
                        postId,
                        board
                    )
                }
            }
        }
    }


}
