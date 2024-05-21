package com.example.planet.presentation.ui.main.plogging.screen.user.screen.mypost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planet.data.remote.dto.response.post.MyPostedInfo
import com.example.planet.presentation.ui.main.plogging.screen.user.component.MyPostingCard
import com.example.planet.presentation.ui.main.plogging.screen.user.component.MyReportCard

@Composable
fun AllReportScreen(postedList: List<MyPostedInfo>) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        repeat(10) {
            MyReportCard(
                type = "게시글",
                title = "asd",
                content = "qwerty...",
                name = "my name",
                report = 2019156029123456
            ) {}

        }
    }
}