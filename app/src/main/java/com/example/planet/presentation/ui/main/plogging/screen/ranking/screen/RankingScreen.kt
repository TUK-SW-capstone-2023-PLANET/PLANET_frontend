package com.example.planet.presentation.ui.main.plogging.screen.ranking.screen

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.component.common.TripleArrowIcon
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.MiddleHead
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.TearProfile
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.TrophyProfile
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityProfile
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.util.numberComma

@Composable
fun RankingScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_planet),
            title = "플래닛 랭킹",
            description = "플래닛 누적점수를 통해 최고의 자리를 차지하세요.",
            icon = {
                TripleArrowIcon { mainViewModel.showRankingScreen = ScreenNav.PlanetRankingScreen }
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            TrophyProfile(
                image = painterResource(id = R.drawable.plogging_ranking_2st_trophy),
                imageSize = 50.dp,
                userIconUrl = mainViewModel.higherPlanetUsers[1].imageUrl,
                userName = mainViewModel.higherPlanetUsers[1].nickName
            )

            TrophyProfile(
                image = painterResource(id = R.drawable.plogging_ranking_1st_trophy),
                imageSize = 60.dp,
                userIconUrl = mainViewModel.higherPlanetUsers[0].imageUrl,
                userName = mainViewModel.higherPlanetUsers[0].nickName
            )

            TrophyProfile(
                image = painterResource(id = R.drawable.plogging_ranking_3st_trophy),
                imageSize = 40.dp,
                userIconUrl = mainViewModel.higherPlanetUsers[2].imageUrl,
                userName = mainViewModel.higherPlanetUsers[2].nickName
            )
        }

        MiddleHead(
            modifier = Modifier.padding(top = 16.dp),
            image = painterResource(id = R.drawable.plogging_ranking_season),
            title = "Spring Season IV",
            description = "아름다운 자연과 함께 봄의 주인공이 되어 보세요!",
            icon = {
                TripleArrowIcon { mainViewModel.showRankingScreen = ScreenNav.SeasonRankingScreen }
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            TearProfile(
                imageUrl = mainViewModel.higherSeasonUsers[2].tierImageUrl,
                imageSize = 80.dp,
                userName = mainViewModel.higherSeasonUsers[2].userName,
                userScore = mainViewModel.higherSeasonUsers[2].score.numberComma()
            )
            TearProfile(
                imageUrl = mainViewModel.higherSeasonUsers[1].tierImageUrl,
                imageSize = 95.dp,
                userName = mainViewModel.higherSeasonUsers[1].userName,
                userScore = mainViewModel.higherSeasonUsers[1].score.numberComma()
            )
            TearProfile(
                imageUrl = mainViewModel.higherSeasonUsers[3].tierImageUrl,
                imageSize = 80.dp,
                userName = mainViewModel.higherSeasonUsers[3].userName,
                userScore = mainViewModel.higherSeasonUsers[3].score.numberComma()
            )
        }
        MiddleHead(
            modifier = Modifier.padding(top = 16.dp),
            image = painterResource(id = R.drawable.plogging_ranking_universitylogo),
            title = "대학교 랭킹",
            description = "학교를 인증하여, 학교의 위상을 높히세요!!",
            icon = {
                TripleArrowIcon {
                    mainViewModel.showRankingScreen = ScreenNav.UniversityRankingScreen
                }
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            UniversityProfile(
                imageUrl = mainViewModel.higherUniversity[1].imageUrl,
                imageSize = 55.dp,
                medal = painterResource(id = R.drawable.medal_2st),
                universityName = mainViewModel.higherUniversity[1].name
            )

            UniversityProfile(
                imageUrl = mainViewModel.higherUniversity[0].imageUrl,
                imageSize = 65.dp,
                medal = painterResource(id = R.drawable.medal_1st),
                universityName = mainViewModel.higherUniversity[0].name
            )

            UniversityProfile(
                imageUrl = mainViewModel.higherUniversity[2].imageUrl,
                imageSize = 55.dp,
                medal = painterResource(id = R.drawable.medal_3st),
                universityName = mainViewModel.higherUniversity[2].name
            )
        }

        MiddleHead(
            modifier = Modifier.padding(top = 16.dp),
            image = painterResource(id = R.drawable.plogging_ranking_universitylogo),
            title = "대학교 개인 랭킹",
            description = "대학교 랭킹의 나의 기여도는?",
            icon = {
                TripleArrowIcon {
                    mainViewModel.showRankingScreen = ScreenNav.UniversityIndividualRankingScreen
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mainViewModel.higherMyUniversityUsers.value[0].universityLogo)
                        .crossfade(true).build(),
                    contentDescription = null,
                    modifier = Modifier.size(65.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = mainViewModel.higherMyUniversityUsers.value[0].universityName,
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                UniversityIndividualRankingTitle()
                mainViewModel.higherMyUniversityUsers.value.subList(1, 4)
                    .forEachIndexed { index, user ->
                        when (index) {
                            0 -> {
                                UniversityIndividualRankingContent(
                                    ranking = user.rank,
                                    name = user.nickName,
                                    score = user.score.numberComma()
                                ) {
                                    Divider(
                                        color = colorResource(id = R.color.ranking_color1),
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(2.dp)
                                    )
                                    Spacer(modifier = Modifier.width(13.dp))
                                }
                            }

                            1 -> {
                                UniversityIndividualRankingContent(
                                    ranking = user.rank,
                                    name = user.nickName,
                                    score = user.score.numberComma()
                                ) {
                                    Divider(
                                        color = colorResource(id = R.color.ranking_color2),
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(2.dp)
                                    )
                                    Spacer(modifier = Modifier.width(13.dp))
                                }
                            }

                            2 -> {
                                UniversityIndividualRankingContent(
                                    ranking = user.rank,
                                    name = user.nickName,
                                    score = user.score.numberComma()
                                ) {
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
    Divider(
        thickness = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp), color = colorResource(id = R.color.font_background_color3)
    )
}

@Composable
fun UniversityIndividualRankingContent(
    ranking: Int,
    name: String,
    score: String,
    medal: @Composable RowScope.() -> Unit
) {
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
            text = "$score 점",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 11.sp,
            modifier = Modifier.weight(1f)
        )
    }
    Divider(
        thickness = 1.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp), color = colorResource(id = R.color.font_background_color3)
    )
}