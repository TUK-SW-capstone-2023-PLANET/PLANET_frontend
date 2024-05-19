package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.planet.R
import com.example.planet.presentation.ui.main.plogging.screen.community.component.BulletinBoardChoiceTitle
import com.example.planet.presentation.ui.main.plogging.screen.community.component.FreeBoardCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.HotPostingCard
import com.example.planet.presentation.ui.main.plogging.screen.community.component.UniversityBoardCard
import com.example.planet.presentation.ui.main.plogging.screen.community.navigation.CommunityNavItem
import com.example.planet.presentation.viewmodel.MainViewModel

@Composable
fun CommunityScreen(
    mainViewModel: MainViewModel,
    startCommunityActivity: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    val universityLogo = mainViewModel.universityInfo.collectAsStateWithLifecycle().value?.universityLogo

    LaunchedEffect(Unit) {
        mainViewModel.getUniversityName()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .weight(0.45f)
                .background(color = colorResource(id = R.color.font_background_color4))
                .padding(start = 30.dp, end = 30.dp, top = 24.dp, bottom = 30.dp)
        ) {
            BulletinBoardChoiceTitle(text = "원하는 게시판을 선택해주세요")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                FreeBoardCard(
                    title = "자유 게시판",
                    description = "플로깅 회원들 모두와\n" + "소통이 가능한 게시판",
                    image = painterResource(id = R.drawable.free_bulletinboard),
                    modifier = Modifier.weight(1f)
                ) { startCommunityActivity(CommunityNavItem.FreeBoardScreen.screenRoute) }
                Spacer(modifier = Modifier.width(20.dp))
                UniversityBoardCard(
                    title = "대학교 게시판",
                    description = "자대 학생들끼리\n" + "소통이 가능한 게시판",
                    image = universityLogo,
                    modifier = Modifier.weight(1f)
                ) { startCommunityActivity(CommunityNavItem.UniversityBoardScreen.screenRoute) }
            }

//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(0.8f)
//            ) {
//                PopularPosting(
//                    text = "플로깅 10년차의 플로깅 꿀팁!",
//                    icon = Icons.Outlined.Person,
//                    count = 1000,
//                    modifier = Modifier
//                )
//                Spacer(modifier = Modifier.height(9.dp))
//                PopularPosting(
//                    text = "플로깅 10년차의 플로깅 꿀팁!",
//                    icon = Icons.Outlined.Person,
//                    count = 1000,
//                    modifier = Modifier
//                )
//            }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 30.dp, end = 30.dp, top = 19.dp)
        ) {
            val titleStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(text = "인기 게시글", style = titleStyle, modifier = Modifier.padding(bottom = 23.dp))
            val dummyDate = listOf(
                PostingInfo1(
                    painterResource(id = R.drawable.temporary_user_icon),
                    "HappyBean",
                    "2024.04.29 01:28",
                    "플로깅 10년차의 플로깅 꿀팁!",
                    "10년차 플로거로 대표해서 글을 작성한다.\n 플로깅을 100% 즐기는 방식은 내 말만 따라하면 될 것이다."
                ),
                PostingInfo1(
                    painterResource(id = R.drawable.temporary_user_icon),
                    "HappyBean",
                    "2024.04.29 01:28",
                    "플로깅 10년차의 플로깅 꿀팁!",
                    "10년차 플로거로 대표해서 글을 작성한다.\n 플로깅을 100% 즐기는 방식은 내 말만 따라하면 될 것이다."
                ),
                PostingInfo1(
                    painterResource(id = R.drawable.temporary_user_icon),
                    "HappyBean",
                    "2024.04.29 01:28",
                    "플로깅 10년차의 플로깅 꿀팁!",
                    "10년차 플로거로 대표해서 글을 작성한다.\n 플로깅을 100% 즐기는 방식은 내 말만 따라하면 될 것이다."
                )
            )
            dummyDate.forEach {
                HotPostingCard(
                    image = it.image,
                    name = it.name,
                    date = it.date,
                    title = it.title,
                    content = it.content
                )
            }

        }
    }
}

data class PostingInfo1(
    val image: Painter,
    val name: String,
    val date: String,
    val title: String,
    val content: String
)
