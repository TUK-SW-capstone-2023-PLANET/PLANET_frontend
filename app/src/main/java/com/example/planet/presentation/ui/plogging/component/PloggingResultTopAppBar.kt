package com.example.planet.presentation.ui.plogging.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import androidx.compose.ui.text.TextStyle

@Composable
fun PloggingResultTopAppBar() {
    val textStyle = TextStyle(
        color = colorResource(id = R.color.font_background_color1),
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
    )
    val iconColor = colorResource(id = R.color.font_background_color1)

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            text = "플로깅 결과",
            modifier = Modifier.align(Alignment.Center),
            style = textStyle
        )
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(13.dp))
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
    HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = colorResource(id = R.color.font_background_color3))
}