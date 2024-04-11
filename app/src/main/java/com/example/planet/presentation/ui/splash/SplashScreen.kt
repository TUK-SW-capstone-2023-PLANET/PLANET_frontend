package com.example.planet.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R


@Preview(showBackground = true)
@Composable
fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.planet_logo), contentDescription = null)
            Text(text = "나와 세상을 바꾸는 한 걸음", fontSize = 20.sp)
        }
        Image(
            painter = painterResource(id = R.drawable.splash_icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .size(70.dp)
        )
        Text(
            text = "준비 운동 중...",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
        )
    }

}