package com.example.planet.presentation.ui.main.plogging.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.planet.R

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