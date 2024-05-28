package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.presentation.ui.component.DialogComponent
import com.example.planet.presentation.ui.main.plogging.screen.community.component.BulletinBoardTopAppBar
import com.example.planet.presentation.ui.main.plogging.screen.community.component.HeartPostingCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostingCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.VisitPostingCard
import com.example.planet.presentation.ui.main.plogging.screen.community.navigation.CommunityNavItem
import com.example.planet.presentation.viewmodel.CommunityViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

@Composable
fun UniversityBoardScreen(
    viewModel: CommunityViewModel,
    onPosting: (String) -> Unit,
    onBack: () -> Unit,
    onSearch: (String) -> Unit,
    startMyWritedActivity: (Long) -> Unit,
    startPostedInfoActivity: (Long, String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    BackHandler {
        if (viewModel.boardDialogState) viewModel.boardDialogState = false
        else { onBack() }
    }

    LaunchedEffect(Unit) {
        listOf(
            async { viewModel.readAllPosted(viewModel.universityName) },
            async { viewModel.readViewPosted(viewModel.universityName) },
            async { viewModel.readHotPosted(viewModel.universityName) }
        ).awaitAll()
    }

    if (viewModel.boardDialogState) {
        DialogComponent(
            title = "게시판 메뉴",
            text1 = "글 쓰기",
            text2 = "내가 쓴 글 보기",
            closeDialog = { viewModel.boardDialogState = false },
            onClick1 = { onPosting("university") },
            onClick2 = { startMyWritedActivity(viewModel.userId) }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        BulletinBoardTopAppBar(
            title = "대학교 게시판",
            onBack = { onBack() },
            onSearch = { onSearch("대학교 게시판") }) {
            viewModel.boardDialogState = true
        }

        if (viewModel.viewPosted != null) {
            VisitPostingCard(
                text = viewModel.viewPosted!!.title,
                count = viewModel.viewPosted!!.viewCount,
                modifier = Modifier.padding(start = 19.dp, end = 19.dp, bottom = 10.dp)
            ) {
                startPostedInfoActivity(viewModel.viewPosted!!.postId, "대학교 게시판")
            }
        }
        if (viewModel.hotPosted != null) {
            HeartPostingCard(
                text = viewModel.hotPosted!!.title,
                count = viewModel.hotPosted!!.heartCount,
                modifier = Modifier.padding(horizontal = 19.dp)
            ) {
                startPostedInfoActivity(viewModel.hotPosted!!.postId, "대학교 게시판")
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3),
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.postedList.size) {
                PostingCard(
                    title = viewModel.postedList[it].title,
                    content = viewModel.postedList[it].content,
                    data = viewModel.postedList[it].uploadTime,
                    name = viewModel.postedList[it].nickName,
                    heartCount = viewModel.postedList[it].heartCount,
                    commentCount = viewModel.postedList[it].commentCount,
                    viewCount = viewModel.postedList[it].viewCount,
                    imageCount = viewModel.postedList[it].imageCount
                ) {
                    startPostedInfoActivity(viewModel.postedList[it].postId, "대학교 게시판")
                }
            }
        }
    }
}