package com.example.planet.presentation.ui.main.plogging.screen.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planet.R
import com.example.planet.component.main.plogging.PloggingHelpCard

@Preview(showBackground = true)
@Composable
fun PloggingHelpScreen() {
    val verticalScroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(verticalScroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PloggingHelpCard(
                modifier = Modifier.weight(1f),
                image = painterResource(id = R.drawable.plogginghelp_card1),
                title = "초급자를 위한 플랜",
                subTitle = "PLANET 초급자를 위한 계획"
            ) {}
            PloggingHelpCard(
                modifier = Modifier.weight(1f),
                image = painterResource(id = R.drawable.plogginghelp_card2),
                title = "누구나 편하게 할 수 있는 플랜",
                subTitle = "누구나 쉽게 할 수 있는 PLANET 계획"
            ) {}
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PloggingHelpCard(
                modifier = Modifier.weight(1f),
                image = painterResource(id = R.drawable.plogginghelp_card3),
                title = "중급자를 위한 플랜",
                subTitle = "플로깅 숙련자를 위한 계획"
            ) {}
            PloggingHelpCard(
                modifier = Modifier.weight(1f),
                image = null,
                title = "",
                subTitle = ""
            ) {

            }
        }
    }
}