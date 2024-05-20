package com.example.planet.presentation.ui.main.plogging.screen.user.screen.mypost

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.planet.presentation.ui.main.plogging.screen.user.component.MyPostingCard
import com.example.planet.presentation.viewmodel.MyWritedViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@Composable
fun AllPostedScreen(myWritedViewModel: MyWritedViewModel) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        listOf(
            async { myWritedViewModel.readAllMyPosted(type = "all", userId = myWritedViewModel.userId) }
        ).awaitAll()
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState), ) {
        myWritedViewModel.allMyPosted.forEach { posted ->
            MyPostingCard(
                type = posted.type,
                title = posted.title,
                content = posted.content,
                data = posted.uploadTime,
                name = posted.nickName,
                imageCount = posted.imageCount,
                heartCount = posted.heartCount,
                commentCount = posted.commentCount,
                viewCount = posted.viewCount
            ) {

            }
        }

    }
}