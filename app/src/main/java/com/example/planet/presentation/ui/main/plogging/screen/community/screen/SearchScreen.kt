package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.presentation.ui.component.SearchTextField
import com.example.planet.presentation.ui.main.plogging.screen.community.component.SearchTopAppBar
import com.example.planet.presentation.viewmodel.CommunityViewModel
import com.example.planet.R
import com.example.planet.data.remote.dto.response.post.Posted
import com.example.planet.presentation.ui.component.EmptyRecentlySearch
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostingCard
import com.example.planet.presentation.util.noRippleClickable
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: CommunityViewModel,
    types: String,
    onBack: () -> Unit,
    startPostedInfoActivity: (Long, String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.readRecentlySearch()
    }

    LaunchedEffect(viewModel.searchInput) {
        if (viewModel.searchInput.isEmpty()) {
            viewModel.searchResult = emptyList()
        }
    }

    val type = if (types == "자유 게시판") {
        "free"
    } else {
        viewModel.universityName
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTopAppBar(title = types) {
            onBack()
        }
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            SearchTextField(
                text = { viewModel.searchInput },
                onValueChange = { viewModel.searchInput = it },
                placeholder = "글 제목",
                fontSize = 12.sp,
            ) {
                viewModel.readSearchPosted(type = type, search =  viewModel.searchInput)
            }
        }

        if (viewModel.recentlySearch.isEmpty() && viewModel.searchResult.isEmpty()) {
            EmptyRecentlySearch()
        } else if (viewModel.searchResult.isEmpty()) {
            RecentlySearch(recentlySearch = viewModel.recentlySearch, onSearch = {
                viewModel.readSearchPosted(type = type, search = it)
                viewModel.searchInput = it
            }) {

            }
        } else {
            SearchingArea(searchResult = viewModel.searchResult) {
                startPostedInfoActivity(it, type)
            }
        }
    }
}



@Composable
fun RecentlySearch(recentlySearch: List<String>, onSearch: suspend (String) -> Unit, onDelete: () -> Unit) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)) {
        repeat(recentlySearch.size / 2) {
            Row(modifier = Modifier.fillMaxWidth()) {
                RecentlySearchCard(modifier = Modifier.weight(1f), text = recentlySearch[it * 2], onSearch = {onSearch(it)}) {}
                Spacer(modifier = Modifier.width(20.dp))
                RecentlySearchCard(modifier = Modifier.weight(1f), text = recentlySearch[it * 2 + 1], onSearch = {onSearch(it)}) {}
            }
            Spacer(modifier = Modifier.height(7.dp))
        }
        if (recentlySearch.size % 2 == 1) {
            Row(modifier = Modifier.fillMaxWidth()) {
                RecentlySearchCard(modifier = Modifier.weight(1f), text = recentlySearch[recentlySearch.lastIndex], onSearch = {onSearch(it)}) {}
                Spacer(modifier = Modifier.width(20.dp))
                Box(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun RecentlySearchCard(modifier: Modifier, text: String, onSearch: suspend (String) -> Unit, onDelete: () -> Unit) {
    val scope = rememberCoroutineScope()

    val testStyle = TextStyle(
        color = colorResource(id = R.color.font_background_color2),
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium
    )
    Card(
        modifier = modifier
            .aspectRatio(7f)
            .clickable { scope.launch { onSearch(text) } },
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            contentColor = colorResource(id = R.color.font_background_color2),
            containerColor = colorResource(id = R.color.font_background_color4)
        )
    ) {
        Row(
            Modifier.padding(start = 9.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "delete",
                tint = colorResource(id = R.color.font_background_color1),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .fillMaxHeight()
                    .noRippleClickable { onDelete() }
            )
            Text(text = text, style = testStyle)
        }
    }
}

@Composable
fun SearchingArea(searchResult: List<Posted>, onClick: (Long) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            HorizontalDivider(
                thickness = 1.dp,
                color = colorResource(id = R.color.font_background_color3),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
        }
        items(searchResult.size) {
            PostingCard(
                title = searchResult[it].title,
                content = searchResult[it].content,
                data = searchResult[it].uploadTime,
                name = searchResult[it].nickName,
                heartCount = searchResult[it].heartCount,
                commentCount = searchResult[it].commentCount,
                viewCount = searchResult[it].viewCount,
                imageCount = searchResult[it].imageCount
            ) {
                onClick(searchResult[it].postId)
            }
        }
    }
}