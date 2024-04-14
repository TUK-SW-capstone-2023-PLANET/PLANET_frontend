package com.example.planet.presentation.ui.signup.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.planet.R
import com.example.planet.TAG

@Composable
fun TopIndicator(page: Int, currentPage: Int = 1) {
    Log.d(TAG,"TopIndicator, currentPage: ${currentPage}")

    val inactive = colorResource(id = R.color.font_background_color3)
    val active = colorResource(id = R.color.main_color1)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        repeat(page) {
            if (it + 1 == currentPage) {
                Canvas(modifier = Modifier.size(15.dp)) {
                    drawCircle(color = active)
                }
            } else {
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = inactive)
                }
            }
            if (page != it + 1) {
                Spacer(modifier = Modifier.width(19.dp).background(color= Color.Black))
            }
        }
    }
}