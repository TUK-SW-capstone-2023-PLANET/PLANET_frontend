package com.example.planet.component.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun SubTitleDescription(description: String) {
    Text(text = description, color = colorResource(id = R.color.font_background_color2), fontSize = 10.sp)
}