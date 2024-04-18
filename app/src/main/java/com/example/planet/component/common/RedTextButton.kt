package com.example.planet.component.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun RedTextButton(modifier: Modifier = Modifier, text: () -> String, textColor: () -> Color, onClick: () -> Unit) {
    TextButton(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.font_background_color4)
        ),
        onClick = { onClick() }
    ) {
        Text(text = text(), color = textColor(), fontSize = 10.sp)
    }
}