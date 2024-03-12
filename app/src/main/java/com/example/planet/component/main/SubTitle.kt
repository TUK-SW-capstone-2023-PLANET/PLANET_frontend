package com.example.planet.component.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SubTitle(title: String, modifier: Modifier = Modifier) {
    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = modifier)
}