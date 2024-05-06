package com.example.planet.presentation.ui.main.plogging.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.component.common.TripleArrowIcon

@Composable
fun Header(title: String, description: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            SubTitle(title = title, modifier = Modifier.padding(end = 4.dp))
            SubTitleDescription(description)
        }
        TripleArrowIcon { onClick() }
    }
}

@Composable
fun SubTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        modifier = modifier,
        lineHeight = 15.sp,
    )
}

@Composable
fun SubTitleDescription(description: String) {
    Text(
        text = description,
        textAlign = TextAlign.End,
        color = colorResource(id = R.color.font_background_color2),
        fontSize = 10.sp,
        lineHeight = 10.sp,
    )
}