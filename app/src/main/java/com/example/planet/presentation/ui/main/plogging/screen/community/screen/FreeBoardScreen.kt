package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.presentation.ui.main.plogging.screen.community.component.BulletinBoardTopAppBar
import com.example.planet.presentation.ui.main.plogging.screen.community.component.HeartPostingCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostingCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.VisitPostingCard
import com.example.planet.presentation.ui.main.plogging.screen.community.navigation.CommunityNavItem

@Composable
fun FreeBoardScreen(navController: NavHostController, onBack: () -> Unit, onSearch: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        BulletinBoardTopAppBar(
            modifier = Modifier.padding(bottom = 18.dp),
            title = "자유 게시판",
            onBack = { onBack() }) { onSearch() }
        VisitPostingCard(
            text = "플로깅 10년차의 플로깅 꿀팁",
            count = 10,
            modifier = Modifier.padding(start = 19.dp, end = 19.dp, bottom = 10.dp)
        )

        HeartPostingCard(
            text = "나랑 같이 플로깅 할래?",
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
                ) {
                    navController.navigate("${CommunityNavItem.PostedInfoScreen.screenRoute}/자유 게시판")
                }
            }
        }
    }
}

