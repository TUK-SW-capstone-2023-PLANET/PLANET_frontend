package com.example.planet.component.main.plogging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TripleArrow() {
    Box(modifier = Modifier.wrapContentSize()) {
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 7.dp)
                .size(20.dp),
            tint = Color(0xFFF2F2F2)
        )
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 14.dp)
                .size(20.dp),
            tint = Color(0xFFD2D2D2)
        )
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color(0xFFFAFAFA)
        )
    }
}