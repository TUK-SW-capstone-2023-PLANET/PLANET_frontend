package com.example.planet.presentation.ui.signup.navigation

const val AUTH = "authScreen"
const val NAME = "nameScreen"
const val PASSWD = "passwdScreen"
const val HEIGHT = "heightScreen"
const val FINAL = "finalScreen"


sealed class SignUpNavItem(val screenRoute: String) {

    data object AuthScreen : SignUpNavItem(
        screenRoute = AUTH,
    )

    data object NameScreen : SignUpNavItem(
        screenRoute = NAME,
    )

    data object PasswdScreen : SignUpNavItem(
        screenRoute = PASSWD,
    )

    data object MyInfoScreen : SignUpNavItem(
        screenRoute = HEIGHT,
    )

    data object FinalScreen : SignUpNavItem(
        screenRoute = FINAL,
    )
}