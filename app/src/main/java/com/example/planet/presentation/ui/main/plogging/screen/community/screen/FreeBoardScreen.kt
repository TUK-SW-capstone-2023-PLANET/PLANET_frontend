package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.planet.R
import com.example.planet.presentation.ui.main.plogging.screen.community.component.CommunityTopAppBar
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PopularPostingCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostingCard

@Composable
fun FreeBoardScreen(onBack: () -> Unit, onSearch: () -> Unit) {
    val popularColor1 = Color(0xFFFFACA3)
    val popularIconColor1 = Color(0xFFFF0000)
    val popularColor2 = Color(0xFFFFD3D3)
    val popularIconColor2 = Color(0xFFFF5D5D)

    Column(modifier = Modifier.fillMaxSize()) {
        CommunityTopAppBar(
            modifier = Modifier.padding(bottom = 18.dp),
            title = "자유 게시판",
            onBack = { onBack() }) { onSearch() }
        PopularPostingCard(
            image = painterResource(id = R.drawable.fire_icon),
            color = popularColor1,
            text = "플로깅 10년차의 플로깅 꿀팁",
            icon = Icons.Outlined.PersonOutline,
            iconColor = popularIconColor1,
            count = 10,
            modifier = Modifier.padding(start = 19.dp, end = 19.dp, bottom = 10.dp)
        )
        PopularPostingCard(
            image = painterResource(id = R.drawable.heart_icon),
            color = popularColor2,
            text = "나랑 같이 플로깅 할래?",
            icon = Icons.Outlined.FavoriteBorder,
            iconColor = popularIconColor2,
            count = 10,
            modifier = Modifier.padding(horizontal = 19.dp)
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = colorResource(id = R.color.font_background_color3),
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(10) {
                PostingCard(
                    title = "다들 뭐하고 있어?",
                    content = "나 심심해",
                    data = "16:37",
                    name = "행복하지 않은 정대영",
                    heartCount = 12,
                    commentCount = 21,
                    personCount = 100
                )
            }
        }
    }
}

