package com.example.planet.presentation.ui.main.plogging.ranking

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.planet.R
import com.example.planet.component.main.plogging.SeasonContentRow
import com.example.planet.component.main.plogging.SeasonTitleRow
import com.example.planet.component.main.plogging.ranking.MiddleHead
import com.example.planet.component.main.plogging.ranking.SearchTextField
import com.example.planet.component.main.plogging.ranking.TearProfile
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma
import com.example.planet.presentation.viewmodel.MainViewModel

@Composable
fun SeasonRankingScreen(
    navController: NavController, mainViewModel: MainViewModel = hiltViewModel()
) {
    BackHandler {
        mainViewModel.mainTopSwitchOnShow()
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        Log.d("daeyoung", "mainViewModel.seasonPerson: ${mainViewModel.higherSeasonUsers}")
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
            image = painterResource(id = R.drawable.plogging_ranking_season),
            title = "Spring Season IV",
            description = "아름다운 자연과 함께 봄의 주인공이 되어 보세요!",
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

        SearchTextField(
            text = mainViewModel.searchText.value,
            onValueChange = mainViewModel.changeSearchText,
            fontSize = 12.sp,
            placeholder = "search"
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }

        SeasonTitleRow()
        mainViewModel.totalSeasonUser.forEachIndexed { index, user ->
            when (index) {
                0 -> {
                    SeasonContentRow(
                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo,
                        color = colorResource(id = R.color.main_color4)
                    )
                }

                1 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color1),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                2 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color2),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                3 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color2),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                4 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color2).copy(alpha = 0.8f),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                5 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = colorResource(id = R.color.main_color2).copy(alpha = 0.6f),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                6 -> {
                    SeasonContentRow(
                        medal = {
                            Divider(
                                color = Color(0xFFBED7EE).copy(alpha = 0.4f),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(4.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }

                else -> {
                    SeasonContentRow(
                        medal = {
//                            Spacer(modifier = Modifier.width(24.dp))
                            Spacer(modifier = Modifier.width(24.dp))
                        },
                        rank = user.rank,
                        tier = user.tierImageUrl,
                        nickname = user.userName,
                        score = user.score.numberComma(),
                        universityLogo = user.universityLogo
                    )
                }
            }
        }
    }
}

