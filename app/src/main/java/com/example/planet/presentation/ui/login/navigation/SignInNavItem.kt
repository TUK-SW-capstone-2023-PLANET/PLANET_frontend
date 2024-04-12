package com.example.planet.presentation.ui.login.navigation

const val SIGNIN = "signInScreen"
const val AUTH = "authScreen"
const val NAME = "nameScreen"
const val PASSWD = "passwdScreen"
const val HEIGHT = "heightScreen"
const val FINAL = "finalScreen"


sealed class SignInNavItem(val screenRoute: String) {
    data object SignInScreen : SignInNavItem(
        screenRoute = SIGNIN,
    )

    data object AuthScreen : SignInNavItem(
        screenRoute = AUTH,
    )

    data object NameScreen : SignInNavItem(
        screenRoute = NAME,
    )

    data object PasswdScreen : SignInNavItem(
        screenRoute = PASSWD,
    )

    data object HeightScreen : SignInNavItem(
        screenRoute = HEIGHT,
    )

    data object FinalScreen : SignInNavItem(
        screenRoute = FINAL,
    )
}