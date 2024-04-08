package com.example.planet.component.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun RedTextButton(modifier: Modifier = Modifier, text: String) {
    TextButton(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.font_background_color4)
        ),
        onClick = { /*TODO*/ }
    ) {
        Text(text = text, color = colorResource(id = R.color.red), fontSize = 10.sp)
    }
}