package com.example.planet.presentation.ui.main.plogging.navigation
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
    data object MessageScreen : BottomNavItem(
        screenRoute = MESSAGE,
        bottomIcon = Icons.Rounded.Chat,
        bottomTitle = "쪽지",
    )
    data object CommunityScreen : BottomNavItem(
        screenRoute = COMMUNITY,
        bottomIcon = Icons.Rounded.Hub,
        bottomTitle = "커뮤니티",
    )
    data object HomeScreen : BottomNavItem(
        screenRoute = HOME,
        bottomIcon = Icons.Rounded.Home,
        bottomTitle = "홈",
    )
    data object RankingScreen : BottomNavItem(
        screenRoute = RANKING,
        bottomIcon = Icons.Rounded.BarChart,
        bottomTitle = "랭킹",
    )
    data object UserScreen : BottomNavItem(
        screenRoute = USER,
        bottomIcon = Icons.Rounded.Person,
        bottomTitle = "유저",
    )

}