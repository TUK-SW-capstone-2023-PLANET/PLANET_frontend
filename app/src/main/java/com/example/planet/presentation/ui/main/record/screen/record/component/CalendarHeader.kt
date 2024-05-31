package com.example.planet.presentation.ui.main.record.screen.record.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.presentation.util.noRippleClickable

@Composable
fun CalendarHeader(yearMonth: String, onNextMonth: () -> Unit, onPreviousMonth: () -> Unit) {
    val titleStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
    )

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = yearMonth, style = titleStyle)
        Row {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.noRippleClickable { onPreviousMonth() })
            Icon(imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.noRippleClickable { onNextMonth() })

        }
    }
}
