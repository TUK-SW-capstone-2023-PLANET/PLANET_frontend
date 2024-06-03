package com.example.planet.presentation.ui.main.record.screen.map.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.util.noRippleClickable

@Composable
fun RecordMapTab(
    modifier: Modifier,
    selected: () -> Boolean,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Card(
        shape = CircleShape,
        modifier = modifier.noRippleClickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected()) colorResource(id = R.color.main_color2)
            else Color.White,
            contentColor = if (selected()) Color.White
            else Color.Black
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(25.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
            )
        }

    }
}