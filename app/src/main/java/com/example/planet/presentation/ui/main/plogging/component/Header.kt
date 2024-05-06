package com.example.planet.presentation.ui.main.plogging.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planet.component.common.TripleArrowIcon
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav

@Composable
fun Header(title: String, description: String, navController: NavHostController, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            verticalAlignment = Alignment.Bottom,
        ) {
            SubTitle(title = title, modifier = Modifier.padding(end = 4.dp))
            SubTitleDescription(description)
        }
        TripleArrowIcon { navController.navigate(ScreenNav.UniversityRankingScreen.screenRoute) }
    }
}