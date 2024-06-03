package com.example.planet.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun EmptyRecentlySearch() {
    val testStyle = TextStyle(
        color = colorResource(id = R.color.font_background_color2),
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = colorResource(id = R.color.font_background_color3),
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .aspectRatio(1f)
        )
        Text(text = "최근 검색어가 없습니다.", style = testStyle)
    }
}