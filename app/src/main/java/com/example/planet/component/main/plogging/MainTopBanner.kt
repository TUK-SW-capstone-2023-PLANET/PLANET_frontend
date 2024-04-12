package com.example.planet.component.main.plogging

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainTopBanner(imageUrlList: List<String>) {
    Log.d(TAG, "MainTopBanner 실행")

    val pagerState = rememberPagerState {
        imageUrlList.size
    }
    val context = LocalContext.current

    val painterList by remember(imageUrlList) {
        mutableStateOf(
            imageUrlList.map {
                ImageRequest.Builder(context)
                    .data(it)
                    .placeholder(R.drawable.main_banner3)
                    .crossfade(false)
                    .build()
            }
        )
    }


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

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { index ->
            painterList.getOrNull(index)?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth().height(150.dp)
                )
            }
        }
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
                .align(Alignment.BottomEnd),
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
                        append(" / ${imageUrlList.size}")
                    },
                    fontSize = 8.sp,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }

}