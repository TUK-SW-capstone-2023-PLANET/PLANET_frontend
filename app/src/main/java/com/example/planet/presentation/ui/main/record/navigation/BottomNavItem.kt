package com.example.planet.presentation.ui.main.record.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddChart
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Hub
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.planet.presentation.ui.main.record.navigation.BottomNavItem

const val HOME = "homeScreen"
const val STATISTICS = "statisticsScreen"
const val RECORD = "recordScreen"
const val SCORE = "scoreScreen"
const val MAP = "mapScreen"

sealed class BottomNavItem(
    val screenRoute: String,
    var bottomIcon: ImageVector,
    val bottomTitle: String,
) {
    data object StatisticsScreen : BottomNavItem(
        screenRoute = STATISTICS,
        bottomIcon = Icons.Rounded.AddChart,
        bottomTitle = "통계",
    )

    data object RecordScreen : BottomNavItem(
        screenRoute = RECORD,
        bottomIcon = Icons.Rounded.ReceiptLong,
        bottomTitle = "기록",
    )

    data object HomeScreen : BottomNavItem(
        screenRoute = HOME,
        bottomIcon = Icons.Rounded.Home,
        bottomTitle = "홈",
    )

    data object ScoreScreen : BottomNavItem(
        screenRoute = SCORE,
        bottomIcon = Icons.Rounded.DeleteSweep,
        bottomTitle = "점수",
    )

    data object MapScreen : BottomNavItem(
        screenRoute = MAP,
        bottomIcon = Icons.Rounded.Map,
        bottomTitle = "지도",
    )

}