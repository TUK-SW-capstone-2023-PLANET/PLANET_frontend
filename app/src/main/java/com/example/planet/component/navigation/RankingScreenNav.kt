package com.example.planet.component.navigation

const val TIER = "tierScreen"
const val PlANET_RANKING = "planetRankingScreen"
const val SEASON_RANKING = "seasonRankingScreen"
const val UNIVERSITY_RANKING = "universityRankingScreen"
const val UNIVERSITY_INDIVIDUAL_RANKING = "universityIndividualRankingScreen"

sealed class ScreenNav(val screenRoute: String) {
    data object TierScreen : ScreenNav(
        screenRoute = TIER,
    )
    data object PlanetRankingScreen : ScreenNav(
        screenRoute = PlANET_RANKING,
    )
    data object SeasonRankingScreen : ScreenNav(
        screenRoute = SEASON_RANKING,
    )
    data object UniversityRankingScreen : ScreenNav(
        screenRoute = UNIVERSITY_RANKING,
    )
    data object UniversityIndividualRankingScreen : ScreenNav(
        screenRoute = UNIVERSITY_INDIVIDUAL_RANKING,
    )
}