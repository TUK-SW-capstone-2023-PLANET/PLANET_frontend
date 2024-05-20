package com.example.planet.presentation.ui.main.plogging.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyTabRow(modifier: Modifier, pagerState: PagerState, tabItems: List<String>) {
    val scope = rememberCoroutineScope()

    Row(modifier = modifier.fillMaxWidth()) {
        tabItems.forEachIndexed { index, title ->
            MyTab(
                modifier = Modifier.padding(end = if (index < tabItems.size - 1) 8.dp else 0.dp),
                text = title,
                selected = { pagerState.currentPage == index },
            ) {
                scope.launch { pagerState.animateScrollToPage(index) }
            }
        }
    }
}

@Composable
fun MyTab(modifier: Modifier, selected: () -> Boolean, text: String, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected()) colorResource(id = R.color.main_color2)
            else colorResource(id = R.color.font_background_color3),
            contentColor = if (selected()) Color.White
            else colorResource(id = R.color.font_background_color2)
        )
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 5.dp)
        )
    }
}