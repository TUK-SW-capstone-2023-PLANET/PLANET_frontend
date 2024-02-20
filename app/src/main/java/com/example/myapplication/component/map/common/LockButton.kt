package com.example.myapplication.component.map.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LockButton(onClick:() -> Unit = {}) {
    Card(
        modifier = Modifier.size(50.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().clickable { onClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}