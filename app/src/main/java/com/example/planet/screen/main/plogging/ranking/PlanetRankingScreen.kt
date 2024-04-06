package com.example.planet.screen.main.plogging.ranking

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.planet.R
import com.example.planet.component.main.plogging.UniversityIndividualContentRow
import com.example.planet.component.main.plogging.UniversityIndividualTitleRow
import com.example.planet.component.main.plogging.ranking.MiddleHead
import com.example.planet.component.main.plogging.ranking.SearchTextField
import com.example.planet.component.main.plogging.ranking.TrophyProfile
import com.example.planet.data.dto.ranking.ExpandedUniversityUser
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma
import com.example.planet.viewmodel.MainViewModel

@Composable
fun PlanetRankingScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    BackHandler {
        mainViewModel.mainTopSwitchOnShow()
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        Log.d("daeYoung","mainViewModel.universityPerson: ${mainViewModel.myUniversityUserList}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = colorResource(id = R.color.font_background_color1),
                modifier = Modifier.noRippleClickable {
                    mainViewModel.mainTopSwitchOnShow()
                    navController.popBackStack()
                }
            )
        }
        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_planet),
            title = "플래닛 랭킹",
            description = "플래닛 누적점수를 통해 최고의 자리를 차지하세요.",
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

        SearchTextField(
            text = mainViewModel.searchText.value,
            onValueChange = mainViewModel.changeSearchText,
            fontSize = 12.sp,
            placeholder = "search"
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }

        UniversityIndividualTitleRow()
        /* TODO(api 연동해서 전체 플로깅 대상으로 된 리스트로 할 것)*/
        mainViewModel.myUniversityUserList.forEachIndexed { index, universityPerson ->
            when (index) {
                0 -> {
                    UniversityIndividualContentRow(
                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
                        rank = universityPerson.rank,
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution,
                        color = colorResource(id = R.color.main_color4)
                    )
                }
                1 -> {
                    UniversityIndividualContentRow(
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
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution
                    )
                }

                2 -> {
                    UniversityIndividualContentRow(
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
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution
                    )
                }

                3 -> {
                    UniversityIndividualContentRow(
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
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution
                    )
                }

                else -> {
                    UniversityIndividualContentRow(
                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
                        rank = universityPerson.rank,
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Test1() {
    val list = listOf(
        ExpandedUniversityUser(
            imageUrl = "https://mblogthumb-phinf.pstatic.net/MjAyMDExMDFfMTgy/MDAxNjA0MjI4ODc1NDMw.Ex906Mv9nnPEZGCh4SREknadZvzMO8LyDzGOHMKPdwAg.ZAmE6pU5lhEdeOUsPdxg8-gOuZrq_ipJ5VhqaViubI4g.JPEG.gambasg/%EC%9C%A0%ED%8A%9C%EB%B8%8C_%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84_%ED%95%98%EB%8A%98%EC%83%89.jpg?type=w800",
            universityName = "한국공학대학교",
            universityLogo = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/logo/image+58.png",
            nickName = "행복한 정대영",
            score = 262,
            rank = 2,
            contribution = 29.638009049773757
        ),
        ExpandedUniversityUser(
            imageUrl = "https://mblogthumb-phinf.pstatic.net/MjAyMDExMDFfMTgy/MDAxNjA0MjI4ODc1NDMw.Ex906Mv9nnPEZGCh4SREknadZvzMO8LyDzGOHMKPdwAg.ZAmE6pU5lhEdeOUsPdxg8-gOuZrq_ipJ5VhqaViubI4g.JPEG.gambasg/%EC%9C%A0%ED%8A%9C%EB%B8%8C_%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84_%ED%95%98%EB%8A%98%EC%83%89.jpg?type=w800",
            universityName = "한국공학대학교",
            universityLogo = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/logo/image+58.png",
            nickName = "행복한 정대영",
            score = 262,
            rank = 2,
            contribution = 29.638009049773757
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = colorResource(id = R.color.font_background_color1),
                modifier = Modifier.noRippleClickable {}
            )
        }
        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_planet),
            title = "플래닛 랭킹",
            description = "플래닛 누적점수를 통해 최고의 자리를 차지하세요.",
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

        SearchTextField(
            text = "mainViewModel.searchText.value",
            onValueChange = {},
            fontSize = 12.sp,
            placeholder = "search"
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }

        UniversityIndividualTitleRow()
        /* TODO(api 연동해서 전체 플로깅 대상으로 된 리스트로 할 것)*/
        list.forEachIndexed { index, universityPerson ->
            when (index) {
                0 -> {
                    UniversityIndividualContentRow(
                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
                        rank = universityPerson.rank,
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution,
                        color = colorResource(id = R.color.main_color4)
                    )
                }
                1 -> {
                    UniversityIndividualContentRow(
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
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution
                    )
                }

                2 -> {
                    UniversityIndividualContentRow(
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
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution
                    )
                }

                3 -> {
                    UniversityIndividualContentRow(
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
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution
                    )
                }

                else -> {
                    UniversityIndividualContentRow(
                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
                        rank = universityPerson.rank,
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution
                    )
                }
            }
        }
    }
}