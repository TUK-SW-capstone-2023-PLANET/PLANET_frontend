package com.example.planet.component.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.planet.R


@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf<BottomNavItem>(
        BottomNavItem.MessageScreen,
        BottomNavItem.CommunityScreen,
        BottomNavItem.HomeScreen,
        BottomNavItem.RankingScreen,
        BottomNavItem.UserScreen,

    )
    val color = colorResource(id = R.color.font_background_color2)
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(20.dp.toPx(), 20.dp.toPx()),
                    style = Stroke(width = 2f)
                )
            }
            .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color.White,
        contentColor = Color.Black,
        tonalElevation = 100.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val selected = item.screenRoute == currentRoute
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.bottomIcon,
                        contentDescription = item.bottomTitle,
                        modifier = Modifier.size(26.dp),
                    )
                },
                label = {
                    Text(
                        text = item.bottomTitle,
                        fontSize = 9.sp,
                    )
                },
//                selectedContentColor = Color(0xFF007AFF),
//                unselectedContentColor = Color.Black,
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.main_color1),
                    selectedTextColor = colorResource(id = R.color.main_color1),
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = Color.White
                ),
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}