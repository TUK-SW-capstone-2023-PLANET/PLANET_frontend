package com.example.planet.presentation.ui.main.plogging.screen.message.navigation

import androidx.compose.runtime.Composable

const val MESSAGELOG = "messageLogScreen"
const val SEND = "sendMessageScreen"


sealed class MessageNavItem(
    val screenRoute: String,
) {
    data object MessageLogScreen : MessageNavItem(
        screenRoute = MESSAGELOG,
    )
    data object SendMessageScreen : MessageNavItem(
        screenRoute = SEND,
    )

}