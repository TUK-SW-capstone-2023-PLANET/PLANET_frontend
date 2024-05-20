package com.example.planet.presentation.ui.main.plogging.screen.user.screen.mypost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planet.data.remote.dto.response.post.MyPostedInfo
import com.example.planet.presentation.ui.main.plogging.screen.user.component.MyPostingCard

@Composable
fun AllPostedScreen(postedList: List<MyPostedInfo>, startPostedInfoActivity: (Long, String) -> Unit) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState), ) {
        postedList.forEach { posted ->
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
                startPostedInfoActivity(posted.postId, posted.type)
            }
        }

    }
}