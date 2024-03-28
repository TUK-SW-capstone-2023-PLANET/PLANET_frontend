package com.example.planet.component.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Hub
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

const val HOME = "homeScreen"
const val RANKING = "rankingScreen"
const val USER = "userScreen"
const val MESSAGE = "messageScreen"
const val COMMUNITY = "communityScreen"


sealed class BottomNavItem(
    val screenRoute: String,
    var bottomIcon: ImageVector,
    val bottomTitle: String,
) {
    data object HomeScreen : BottomNavItem(
        screenRoute = HOME,
        bottomIcon = Icons.Rounded.Home,
        bottomTitle = "Home",
    )
    data object RankingScreen : BottomNavItem(
        screenRoute = RANKING,
        bottomIcon = Icons.Rounded.BarChart,
        bottomTitle = "Statistics",
    )
    data object UserScreen : BottomNavItem(
        screenRoute = USER,
        bottomIcon = Icons.Rounded.Person,
        bottomTitle = "Setting",
    )
    data object MessageScreen : BottomNavItem(
        screenRoute = MESSAGE,
        bottomIcon = Icons.Rounded.Chat,
        bottomTitle = "Setting",
    )
    data object CommunityScreen : BottomNavItem(
        screenRoute = COMMUNITY,
        bottomIcon = Icons.Rounded.Hub,
        bottomTitle = "Setting",
    )
}