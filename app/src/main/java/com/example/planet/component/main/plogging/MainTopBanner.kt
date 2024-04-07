package com.example.planet.component.main.plogging

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainTopBanner(imageUrlList: List<String>) {
    val bannerCount = 4
    val pagerState = rememberPagerState {
        bannerCount
    }
    val coroutineScope = rememberCoroutineScope()
    val painterList = mutableListOf<ImageRequest>()

    // 드래그 도중 자동 스와이프가 안되기 위함
    val isDraggedState: State<Boolean> =
        pagerState.interactionSource.collectIsDraggedAsState()
    LaunchedEffect(key1 = isDraggedState) {
        snapshotFlow { isDraggedState.value }
            .collectLatest { isDragged ->
                if (isDragged) return@collectLatest
                while (true) {
                    delay(3_000)
                    pagerState.animateScrollToPage((pagerState.currentPage.inc()) % 4)
                }
            }
    }
//    DisposableEffect(Unit) {
//        val job = coroutineScope.launch {
//            while (true) {
//                delay(2000)
//                pagerState.animateScrollToPage((pagerState.currentPage + 1) % 4)
//            }
//        }
//        onDispose { job.cancel() }
//    }
    imageUrlList.forEach {
        painterList.add(
            ImageRequest.Builder(LocalContext.current)
            .data(it)
            .crossfade(true)
            .build()
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { index ->
            when (index) {
                0 -> AsyncImage(
                    model = painterList[0],
                    contentDescription = null,
                )
                1 -> AsyncImage(
                    model = painterList[1],
                    contentDescription = null,
                )
                2 -> AsyncImage(
                    model = painterList[2],
                    contentDescription = null,
                )
                3 -> AsyncImage(
                    model = painterList[3],
                    contentDescription = null,
                )
            }
        }
        Card(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = colorResource(id = R.color.font_background_color2)
            )
        ) {
            Box(modifier = Modifier) {
                Text(
                    text = buildAnnotatedString {
                        append((pagerState.currentPage + 1).toString())
                        append(" / $bannerCount")
                    },
                    fontSize = 8.sp,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }

}