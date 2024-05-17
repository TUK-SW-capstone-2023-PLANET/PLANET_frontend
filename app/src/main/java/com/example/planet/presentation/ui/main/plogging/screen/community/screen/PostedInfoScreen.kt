package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.data.remote.dto.request.post.PostId
import com.example.planet.presentation.ui.component.DialogComponent
import com.example.planet.presentation.ui.main.plogging.screen.community.component.CommentCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostedContent
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostedMyProfileCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostedTopAppBar
import com.example.planet.presentation.viewmodel.CommunityViewModel
import kotlinx.coroutines.launch

@Composable
fun PostedInfoScreen(
    viewModel: CommunityViewModel,
    navController: NavHostController,
    appBarTitle: String
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    if (viewModel.postedDialogState) {
        DialogComponent(
            title = "게시판 메뉴",
            text1 = "신고하기",
            text2 = if (viewModel.postedInfo.userId == viewModel.userId) "삭제하기" else "",
            text2Color = colorResource(id = R.color.red),
            closeDialog = { viewModel.postedDialogState = false },
            onClick1 = { },
            onClick2 = { }
        )
    }

    BackHandler {
        if (viewModel.postedDialogState) viewModel.postedDialogState = false
        else navController.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PostedTopAppBar(
            modifier = Modifier,
            title = appBarTitle,
            onBack = { navController.popBackStack() }) {
            viewModel.postedDialogState = true
        }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 18.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 15.dp)
                    .fillMaxWidth()
            ) {
                PostedMyProfileCard(name = viewModel.postedInfo.nickName, date = viewModel.postedInfo.uploadTime)
                PostedContent(
                    title = viewModel.postedInfo.title,
                    image = viewModel.postedInfo.imageUrl,
                    content = viewModel.postedInfo.content,
                    heartCount = viewModel.postedInfo.heartCount,
                    commentCount = viewModel.postedInfo.commentCount,
                    personCount = viewModel.postedInfo.viewCount,
                    isFavorite = viewModel.postedInfo.heart
                ) {
                    val postId = PostId(viewModel.userId, viewModel.testPostId)
                    scope.launch {
                        if (!viewModel.postedInfo.heart) {
                            viewModel.boardHeartSave(postId)
                        } else {
                            viewModel.postedInfo = viewModel.postedInfo.copy(heart = it)
                        }
                    }
                }
            }

            Column {
                repeat(viewModel.postedInfo.commentCount) {
                    CommentCard(
                        image = painterResource(id = R.drawable.temporary_user_icon),
                        name = "행복한 티노",
                        content = "49 육회포자로 와라",
                        date = "35분 전",
                        heartCount = 21
                    )
                }
            }
        }

    }
}

