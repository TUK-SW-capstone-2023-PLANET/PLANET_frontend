package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.planet.R
import com.example.planet.data.remote.dto.response.post.CommentInfo
import com.example.planet.data.remote.dto.response.post.PostedInfo
import com.example.planet.presentation.ui.component.DialogComponent
import com.example.planet.presentation.ui.main.plogging.screen.community.component.CommentCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostedContent
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostedMyProfileCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostedTopAppBar
import com.example.planet.presentation.util.noRippleClickable
import com.example.planet.presentation.viewmodel.CommunityViewModel
import kotlinx.coroutines.launch

@Composable
fun PostedInfoScreen(
    viewModel: CommunityViewModel,
    appBarTitle: String,
    onBack: () -> Unit,
    startMessageActivity: (Long, Long) -> Unit
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val keyBoardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.readCommentList(viewModel.currentPostId)
    }

    if (viewModel.postedDialogState) {
        DialogComponent(
            title = "게시판 메뉴",
            text1 = "신고하기",
            text2 = if (viewModel.postedInfo.userId == viewModel.userId) "삭제하기" else "",
            text2Color = colorResource(id = R.color.red),
            closeDialog = { viewModel.postedDialogState = false },
            onClick1 = { },
            onClick2 = {
                scope.launch {
                    viewModel.deletePosted(postId = viewModel.postedInfo.postId) { onBack() }
                }
            }
        )
    }

    BackHandler {
        if (viewModel.postedDialogState) viewModel.postedDialogState = false
        else onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PostedTopAppBar(
            modifier = Modifier,
            title = appBarTitle,
            onBack = { onBack() }) {
            viewModel.postedDialogState = true
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(top = 18.dp)
                    .weight(1f)
            ) {
                PostedArea(
                    postedInfo = { viewModel.postedInfo },
                    onFavorite = { viewModel.savePostedHeart(it) },
                    onNotFavorite = { viewModel.deletePostedHeart(it) },
                )

                CommentArea(
                    viewModel = viewModel,
                    comment = { viewModel.commentList }) { userId, recieverId ->
                    startMessageActivity(userId, recieverId)
                }
            }
            CommentTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
//                    .align(Alignment.BottomCenter)
                    .zIndex(1f),
                postId = viewModel.postedInfo.postId,
                text = viewModel.postingCommentInput,
                onTextChange = { viewModel.postingCommentInput = it }
            ) {
                scope.launch { viewModel.saveComment(it) { keyBoardController?.hide() } }
            }
        }


    }
}

@Composable
fun CommentArea(
    viewModel: CommunityViewModel,
    comment: () -> List<CommentInfo>,
    startMessageActivity: (Long, Long) -> Unit
) {
    Column {
        comment().forEach { comment ->
            CommentCard(
                viewModel = viewModel,
                commentId = comment.commentId,
                userId = comment.userId,
                myUserId = viewModel.userId,
                image = comment.imageUrl,
                name = comment.nickName,
                content = comment.content,
                date = comment.uploadTime,
                isHeart = comment.heart,
                heartCount = comment.heartCount
            ) { userId, recieverId ->
                startMessageActivity(userId, recieverId)
            }
        }
    }
}

@Composable
fun PostedArea(
    postedInfo: () -> PostedInfo,
    onFavorite: suspend (Long) -> Unit,
    onNotFavorite: suspend (Long) -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 15.dp)
            .fillMaxWidth()
    ) {
        PostedMyProfileCard(
            image = postedInfo().profileUrl,
            name = postedInfo().nickName,
            date = postedInfo().uploadTime
        )
        PostedContent(
            title = postedInfo().title,
            image = postedInfo().imageUrl,
            content = postedInfo().content,
            heartCount = postedInfo().heartCount,
            commentCount = postedInfo().commentCount,
            personCount = postedInfo().viewCount,
            isFavorite = postedInfo().heart
        ) {
            scope.launch {
                if (!postedInfo().heart) {
                    onFavorite(postedInfo().postId)
                } else {
                    onNotFavorite(postedInfo().postId)
                }
            }
        }
    }
}

@Composable
fun CommentTextField(
    modifier: Modifier,
    postId: Long,
    text: String,
    onTextChange: (String) -> Unit,
    onSend: (postId: Long) -> Unit
) {
    val placeholderStyle = TextStyle(
        color = colorResource(id = R.color.font_background_color2),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold
    )

    TextField(
        value = text,
        onValueChange = { onTextChange(it) },
        placeholder = { Text(text = "댓글을 입력하세요.", style = placeholderStyle) },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.send_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable { onSend(postId) }
            )
        },
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = colorResource(id = R.color.font_background_color4),
            focusedContainerColor = colorResource(id = R.color.font_background_color4),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}