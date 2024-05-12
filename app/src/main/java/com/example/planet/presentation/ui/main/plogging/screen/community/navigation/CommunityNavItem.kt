package com.example.planet.presentation.ui.main.plogging.screen.community.navigation


const val FREE = "freeBoardScreen"
const val UNIVERSITY = "universityBoardScreen"
const val FREE_SEARCH = "freeBoardSearchScreen"
const val UNIVERSITY_SEARCH = "universityBoardSearchScreen"
const val POSTED = "postedInfoScreen"


sealed class CommunityNavItem(
    val screenRoute: String,
) {
    data object FreeBoardScreen : CommunityNavItem(
        screenRoute = FREE,
    )
    data object UniversityBoardScreen : CommunityNavItem(
        screenRoute = UNIVERSITY,
    )
    data object PostedInfoScreen : CommunityNavItem(
        screenRoute = POSTED,
    )
    data object FreeBoardSearchScreen : CommunityNavItem(
        screenRoute = FREE_SEARCH,
    )
    data object UniversityBoardSearchScreen : CommunityNavItem(
        screenRoute = UNIVERSITY_SEARCH,
    )
}