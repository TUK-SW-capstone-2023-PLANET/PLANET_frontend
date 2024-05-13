package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostingTopAppBar
import com.example.planet.presentation.viewmodel.CommunityViewModel

@Composable
fun PostingScreen(
    viewModel: CommunityViewModel,
    navController: NavHostController,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PostingTopAppBar(
            modifier = Modifier,
            title = "글쓰기",
            onBack = { navController.popBackStack() }) {
            viewModel.postedDialogState = true
        }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 18.dp, start = 20.dp, end = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.postingImageList.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.empty_image),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
            }
            PostingTitleTextFiled(
                value = viewModel.postingTitleInput,
                onValueChange = { viewModel.postingTitleInput = it })
            PostingContentTextFiled(
                value = viewModel.postingContentInput,
                onValueChange = { viewModel.postingContentInput = it })
        }
    }
}

@Composable
fun PostingTitleTextFiled(value: String, onValueChange: (String) -> Unit) {

    val placeholderTextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.font_background_color2)
    )

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = colorResource(id = R.color.font_background_color3),
            focusedIndicatorColor = colorResource(id = R.color.font_background_color3),
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        placeholder = { Text(text = "제목", style = placeholderTextStyle) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold,)
    )
}

@Composable
fun PostingContentTextFiled(value: String, onValueChange: (String) -> Unit) {

    val placeholderTextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.font_background_color2)
    )

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        placeholder = { Text(text = "내용을 입력하세요.", style = placeholderTextStyle) },
        modifier = Modifier.fillMaxWidth(),
    )
}