package com.example.planet.screen.main.plogging.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.component.main.plogging.ranking.MiddleHead
import com.example.planet.component.main.plogging.ranking.TearProfile
import com.example.planet.component.main.plogging.ranking.TrophyProfile
import com.example.planet.component.main.plogging.ranking.UniversityProfile

@Composable
@Preview(showBackground = true)
fun RankingScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {

        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_planet),
            title = "플래닛 랭킹",
            description = "플래닛 누적점수를 통해 최고의 자리를 차지하세요."
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            TrophyProfile(
                image = painterResource(id = R.drawable.plogging_ranking_2st_trophy),
                imageSize = 50.dp,
                userIcon = painterResource(id = R.drawable.temporary_user_icon),
                userName = "HappyBean"
            )

            TrophyProfile(
                image = painterResource(id = R.drawable.plogging_ranking_1st_trophy),
                imageSize = 60.dp,
                userIcon = painterResource(id = R.drawable.temporary_user_icon),
                userName = "행복한 정대영"
            )

            TrophyProfile(
                image = painterResource(id = R.drawable.plogging_ranking_3st_trophy),
                imageSize = 40.dp,
                userIcon = painterResource(id = R.drawable.temporary_user_icon),
                userName = "고통받는 이승민"
            )
        }

        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_season),
            title = "Spring Season IV",
            description = "아름다운 자연과 함께 봄의 주인공이 되어 보세요!"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            TearProfile(
                image = painterResource(id = R.drawable.temporary_tear2),
                imageSize = 80.dp,
                userName = "미미미누",
                userScore = "3,201점"
            )
            TearProfile(
                image = painterResource(id = R.drawable.temporary_tear1),
                imageSize = 95.dp,
                userName = "행복한 정대영",
                userScore = "3,201점"
            )
            TearProfile(
                image = painterResource(id = R.drawable.temporary_tear2),
                imageSize = 80.dp,
                userName = "컴공 간판 강기환",
                userScore = "3,201점"
            )
        }

        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_university),
            title = "대학교 랭킹",
            description = "학교를 인증하여, 학교의 위상을 높히세요!!"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            UniversityProfile(
                image = painterResource(id = R.drawable.university1),
                imageSize = 55.dp,
                medal = painterResource(id = R.drawable.medal_2st),
                universityName = "연세대학교"
            )

            UniversityProfile(
                image = painterResource(id = R.drawable.university2),
                imageSize = 65.dp,
                medal = painterResource(id = R.drawable.medal_1st),
                universityName = "한국공학대학교"
            )

            UniversityProfile(
                image = painterResource(id = R.drawable.university3),
                imageSize = 55.dp,
                medal = painterResource(id = R.drawable.medal_3st),
                universityName = "고려대학교"
            )
        }

        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_university),
            title = "대학교 개인 랭킹",
            description = "대학교 랭킹의 나의 기여도는?"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.university2),
                    contentDescription = null,
                    modifier = Modifier.size(65.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "한국공학대학교",
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                UniversityIndividualRankingTitle()
                UniversityIndividualRankingContent(ranking = 1, name = "행복한 정대영", score = "371,357점") {
                    Divider(
                        color = colorResource(id = R.color.ranking_color1),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                }
                UniversityIndividualRankingContent(ranking = 3, name = "고통받는 이승민", score = "268,589점") {
                    Divider(
                        color = colorResource(id = R.color.ranking_color2),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                }
                UniversityIndividualRankingContent(ranking = 3, name = "컴공간판 강기환", score = "21,075점") {
                    Divider(
                        color = colorResource(id = R.color.ranking_color3),
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                    )
                    Spacer(modifier = Modifier.width(13.dp))
                }
            }
        }
    }
}

@Composable
fun UniversityIndividualRankingTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp)
    ) {
        Text(
            text = "순위",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 11.sp,
            modifier = Modifier.fillMaxWidth(0.2f)
        )
        Text(
            text = "이름",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 11.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "점수",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 11.sp,
            modifier = Modifier.weight(1f)
        )
    }
    Divider(thickness = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp), color = colorResource(id = R.color.font_background_color3))
}

@Composable
fun UniversityIndividualRankingContent(ranking: Int, name: String, score: String, medal: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        medal()
        Text(
            text = ranking.toString(),
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 11.sp,
            modifier = Modifier.fillMaxWidth(0.2f)
        )
        Text(
            text = name,
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 11.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = score,
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 11.sp,
            modifier = Modifier.weight(1f)
        )
    }
    Divider(thickness = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp), color = colorResource(id = R.color.font_background_color3))
}