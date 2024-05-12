package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planet.presentation.ui.main.plogging.screen.community.component.CommunityTopAppBar

@Composable
fun UniversityBoardScreen(onBack: () -> Unit, onSearch: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        CommunityTopAppBar(title = "대학교 게시판", onBack = { onBack() }) {
            onSearch()
        }
    }
}