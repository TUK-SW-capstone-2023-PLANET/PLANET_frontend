package com.example.planet.screen.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun MainTopBanner() {
    val bannerCount = 4
    val pagerState = rememberPagerState {
        bannerCount
    }
    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        val job = coroutineScope.launch {
            while (true) {
                delay(4000)
                pagerState.animateScrollToPage((pagerState.currentPage + 1) % 4)
            }
        }
        onDispose { job.cancel() }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { index ->
            when (index) {
                0 -> Image(
                    painter = painterResource(id = R.drawable.main_banner1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                1 -> Image(
                    painter = painterResource(id = R.drawable.main_banner2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                2 -> Image(
                    painter = painterResource(id = R.drawable.main_banner3),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                3 -> Image(
                    painter = painterResource(id = R.drawable.main_banner4),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
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