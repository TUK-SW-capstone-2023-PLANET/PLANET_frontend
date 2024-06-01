package com.example.planet.presentation.ui.main.record.screen.record.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.presentation.util.noRippleClickable
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDay(
    day: LocalDate,
    isToday: Boolean,
    isSelected: () -> Boolean,
    isPlogging: Boolean,
    modifier: Modifier,
    onClick: () -> Unit = {}
) {
    val dayStyle = androidx.compose.ui.text.TextStyle(
        color = if (isToday || isSelected()) Color.White else Color.Black
    )
    Column(
        modifier = modifier.noRippleClickable {
//            if (isPlogging) onClick()
                                              },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = if (isPlogging) R.drawable.green_sprout else R.drawable.grey_sprout),
            modifier = Modifier
                .weight(0.6f)
                .fillMaxSize(),
            contentDescription = null
        )

        Box(modifier = Modifier.weight(0.4f)) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .align(Alignment.Center),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected()) {
//                        Log.d(TAG, "isSelected, day: $day")
                        colorResource(id = R.color.red)
                    } else if (isToday) {
//                        Log.d(TAG, "isToday, day: $day")
                        colorResource(id = R.color.font_background_color1)
                    } else {
                        Color.Transparent
                    },
                )
            ) {}
            Text(
                text = day.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.Center),
//                modifier = Modifier
//                    .fillMaxSize(),
//                textAlign = TextAlign.Center,
                style = dayStyle
            )

        }
    }

}
