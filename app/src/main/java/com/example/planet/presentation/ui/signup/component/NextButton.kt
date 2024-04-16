package com.example.planet.presentation.ui.signup.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R

@Composable
fun NextButton(enable: () -> Boolean, onNextPage: () -> Unit) {
    OutlinedButton(
        onClick = { onNextPage() },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = colorResource(id = R.color.main_color1),
            disabledContentColor = colorResource(id = R.color.font_background_color2),
            disabledContainerColor = Color.Transparent
        ),
        enabled = enable(),
        shape = RoundedCornerShape(9.dp),
        border = if (enable()) {
            BorderStroke(width = 1.dp, color = colorResource(id = R.color.main_color1))
        } else {
            BorderStroke(
                width = 1.dp,
                color = colorResource(id = R.color.font_background_color2)
            )
        }
    ) {
        Text(text = "다음으로", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
    }
}