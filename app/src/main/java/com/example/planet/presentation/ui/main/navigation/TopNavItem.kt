package com.example.planet.presentation.ui.main.navigation


const val PlOGGING = "messageScreen"
const val RECORD = "communityScreen"


sealed class TopNavItem(
    val screenRoute: String,
) {
    data object PloggingNavigationGraph : TopNavItem(
        screenRoute = PlOGGING,
    )

    data object RecordNavigationGraph : TopNavItem(
        screenRoute = RECORD,
    )
}