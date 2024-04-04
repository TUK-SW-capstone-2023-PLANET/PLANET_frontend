package com.example.planet.screen.main.plogging.ranking

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.component.main.plogging.ranking.MiddleHead
import com.example.planet.component.main.plogging.ranking.SearchTextField
import com.example.planet.component.main.plogging.ranking.UniversityProfile
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma
import com.example.planet.util.round
import com.example.planet.viewmodel.MainViewModel

@Composable
fun UniversityRankingScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    BackHandler {
        mainViewModel.mainTopSwitchOnShow()
        navController.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = colorResource(id = R.color.font_background_color1),
                modifier = Modifier.noRippleClickable {
                    mainViewModel.mainTopSwitchOnShow()
                    navController.popBackStack()
                })
        }
        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_university),
            title = "대학교 랭킹",
            description = "학교를 인증하여, 학교의 위상을 높히세요!!",
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

        SearchTextField(
            text = mainViewModel.searchText.value,
            onValueChange = mainViewModel.changeSearchText,
            fontSize = 12.sp,
            placeholder = "search"
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }

        UniversityTitleRow()
        mainViewModel.seasonPerson.forEachIndexed { index, universityPerson ->
            when (index) {
                0 -> {
                    UniversityContentRow(
                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
                        rank = universityPerson.rank,
                        universityName = universityPerson.userName,
                        score = universityPerson.score.numberComma(),
                        universityLogo = universityPerson.universityLogo,
                        color = colorResource(id = R.color.main_color4)
                    )
                }

                1 -> {
                    UniversityContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.ranking_color1),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = universityPerson.rank,
                        universityName = universityPerson.userName,
                        score = universityPerson.score.numberComma(),
                        universityLogo = universityPerson.universityLogo
                    )
                }

                2 -> {
                    UniversityContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.ranking_color2),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = universityPerson.rank,
                        universityName = universityPerson.userName,
                        score = universityPerson.score.numberComma(),
                        universityLogo = universityPerson.universityLogo
                    )
                }

                3 -> {
                    UniversityContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.ranking_color3),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = universityPerson.rank,
                        universityName = universityPerson.userName,
                        score = universityPerson.score.numberComma(),
                        universityLogo = universityPerson.universityLogo
                    )
                }

                else -> {
                    UniversityContentRow(
                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
                        rank = universityPerson.rank,
                        universityName = universityPerson.userName,
                        score = universityPerson.score.numberComma(),
                        universityLogo = universityPerson.universityLogo
                    )
                }
            }

        }

    }

}

@Composable
fun UniversityTitleRow() {
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 2.dp, bottom = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.3f)) {
            Text(
                text = "순위",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "학교",
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Text(
            text = "이름",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "점수",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
}

@Composable
fun UniversityContentRow(
    medal: @Composable () -> Unit,
    rank: Int,
    universityLogo: String,
    universityName: String,
    score: String,
    color: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(color = color)
            .padding(vertical = 2.dp)
    ) {
        medal()
        Row(
            modifier = Modifier
                .fillMaxWidth(0.3f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rank.toString(),
                color = colorResource(id = R.color.font_background_color2),
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(universityLogo)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.9f)
            )
        }
        Text(
            text = universityName,
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$score 점",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
}