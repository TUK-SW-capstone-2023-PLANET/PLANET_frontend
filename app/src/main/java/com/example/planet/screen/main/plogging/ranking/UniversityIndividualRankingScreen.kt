package com.example.planet.screen.main.plogging.ranking

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.component.main.plogging.UniversityIndividualContentRow
import com.example.planet.component.main.plogging.UniversityIndividualTitleRow
import com.example.planet.component.main.plogging.ranking.MiddleHead
import com.example.planet.component.main.plogging.ranking.SearchTextField
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma
import com.example.planet.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlin.math.round

@Composable
fun UniversityIndividualRankingScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
    }

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
            image = painterResource(id = R.drawable.plogging_ranking_universitylogo),
            title = "대학교 개인 랭킹",
            description = "대학교 랭킹의 나의 기여도는?"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {

//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(mainViewModel.myUniversityTop3RankingUsers[0].universityLogo)
//                        .crossfade(true).build(),
//                    contentDescription = null,
//                    modifier = Modifier.size(65.dp)
//                )
//                Spacer(modifier = Modifier.height(12.dp))
//                Text(
//                    text = mainViewModel.myUniversityTop3RankingUsers[0].universityName,
//                    color = colorResource(id = R.color.font_background_color1),
//                    fontSize = 11.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }

            UniversityIndividualGraph(
                visible = visible,
                score = mainViewModel.myUniversityTop3RankingUsers[1].score.numberComma(),
                graphHeight = mainViewModel.universityUserGraphHeightList[0],
                colors = listOf(Color(0XFFD1CFCF), Color(0XFFFFFFFF)),
                userName = mainViewModel.myUniversityTop3RankingUsers[1].nickName,
            )
            UniversityIndividualGraph(
                visible = visible,
                score = mainViewModel.myUniversityTop3RankingUsers[0].score.numberComma(),
                graphHeight = mainViewModel.universityUserGraphHeightList[1],
                colors = listOf(Color(0xFFFFCC31), Color(0XFFFFFFFF)),
                userName = mainViewModel.myUniversityTop3RankingUsers[0].nickName,
            )
            UniversityIndividualGraph(
                visible = visible,
                score = mainViewModel.myUniversityTop3RankingUsers[2].score.numberComma(),
                graphHeight = mainViewModel.universityUserGraphHeightList[2],
                colors = listOf(Color(0xFFE1B983), Color(0XFFFFFFFF)),
                userName = mainViewModel.myUniversityTop3RankingUsers[2].nickName,
            )
        }

        Spacer(modifier = Modifier.padding(bottom = 24.dp))

        SearchTextField(
            text = mainViewModel.searchText.value,
            onValueChange = mainViewModel.changeSearchText,
            fontSize = 12.sp,
            placeholder = "search"
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }

        UniversityIndividualTitleRow()
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
                        contribution = universityPerson.contribution,
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
                        contribution = universityPerson.contribution,
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
                        contribution = universityPerson.contribution,
                    )
                }

                else -> {
                    UniversityIndividualContentRow(
                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
                        rank = universityPerson.rank,
                        nickname = universityPerson.nickName,
                        score = universityPerson.score.numberComma(),
                        contribution = universityPerson.contribution,
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun Test3() {

    var visible by remember { mutableStateOf(false) }

    var GraphHeight2th: Int = round(120 / 371357 * 268589.0).toInt()
    var GraphHeight3th: Int = round(120 / 371357 * 189240.0).toInt()

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
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
                modifier = Modifier.noRippleClickable {})
        }
        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_universitylogo),
            title = "대학교 개인 랭킹",
            description = "대학교 랭킹의 나의 기여도는?"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.university2),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "한국공학대학교",
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            UniversityIndividualGraph(
                visible = visible,
                score = "921,218",
                graphHeight = GraphHeight2th.dp,
                colors = listOf(Color(0XFFD1CFCF), Color(0XFFFFFFFF)),
                userName = "고통받는 이승민",
            )
            UniversityIndividualGraph(
                visible = visible,
                score = "1,120,921",
                graphHeight = 120.dp,
                colors = listOf(Color(0xFFFFCC31), Color(0XFFFFFFFF)),
                userName = "행복한 정대영",
            )
            UniversityIndividualGraph(
                visible = visible,
                score = "218,213",
                graphHeight = GraphHeight3th.dp,
                colors = listOf(Color(0xFFE1B983), Color(0XFFFFFFFF)),
                userName = "컴공간판 강기환",
            )
        }

        SearchTextField(
            text = "mainViewModel.searchText.value",
            onValueChange = { },
            fontSize = 12.sp,
            placeholder = "search"
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }

        UniversityIndividualTitleRow()
    }
}

@Composable
fun UniversityIndividualGraph(
    visible: Boolean = false,
    score: String,
    graphHeight: Dp,
    colors: List<Color>,
    userName: String,
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn()
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = score,
                color = colorResource(id = R.color.font_background_color1),
                fontSize = 9.sp
            )
        }
        AnimatedVisibility(visible = visible,
            enter = slideInVertically { it }) {
            Canvas(
                modifier = Modifier
                    .height(graphHeight)
                    .width(40.dp)
                    .animateContentSize()
            ) {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = colors
                    )
                )
            }
        }
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn()
        ) {
            Text(
                text = userName,
                color = colorResource(id = R.color.font_background_color1),
                fontSize = 10.sp
            )
        }
    }
}