package com.example.planet.presentation.ui.main.plogging.screen.home.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.component.common.TripleArrowIcon
import com.example.planet.presentation.ui.main.plogging.component.SubTitle
import com.example.planet.presentation.ui.main.plogging.component.SubTitleDescription
import com.example.planet.presentation.ui.main.plogging.screen.home.component.UniversityGraph
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.data.remote.dto.response.ranking.universityuser.ExpandedUniversityUser
import com.example.planet.presentation.ui.main.plogging.component.Header
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityIndividualContentRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityIndividualTitleRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav
import com.example.planet.presentation.util.numberComma
import com.example.planet.presentation.util.round
import com.example.planet.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UniversityScreen(
    mainViewModel: MainViewModel,
    universityUserList: () -> List<ExpandedUniversityUser>,
    universityList: () -> List<University>,
    graphHeightList: () -> List<Dp>,
    startRankingActivity: (String) -> Unit
) {

    var visible by remember { mutableStateOf(true) }
    var scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) { mainViewModel.getTop4UniversityUser() }
        launch(Dispatchers.IO) { mainViewModel.getTop3Universities() }
    }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }
    if (universityList().isEmpty() || universityUserList().isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
            ) {
                Header(
                    title = "대학교 순위",
                    description = "학교를 인증하여, 학교의 위상을 높히세요!!",
                    modifier = Modifier.fillMaxHeight(0.1f)
                ) {
                    startRankingActivity("university")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    UniversityGraph(
                        visible = { visible },
                        universityLogo = universityList()[1].imageUrl,
                        score = universityList()[1].score.numberComma(),
                        graphHeight = graphHeightList()[0],
                        colors = listOf(Color(0XFFD1CFCF), Color(0XFFFFFFFF)),
                        universityName = universityList()[1].name,
                        medal = painterResource(id = R.drawable.medal_2st)
                    )
                    UniversityGraph(
                        visible = { visible },
                        universityLogo = universityList()[0].imageUrl,
                        score = universityList()[0].score.numberComma(),
                        graphHeight = graphHeightList()[1],
                        colors = listOf(Color(0xFFFFCC31), Color(0XFFFFFFFF)),
                        universityName = universityList()[0].name,
                        medal = painterResource(id = R.drawable.medal_1st)
                    )
                    UniversityGraph(
                        visible = { visible },
                        universityLogo = universityList()[2].imageUrl,
                        score = universityList()[2].score.numberComma(),
                        graphHeight = graphHeightList()[2],
                        colors = listOf(Color(0xFFE1B983), Color(0XFFFFFFFF)),
                        universityName = universityList()[2].name,
                        medal = painterResource(id = R.drawable.medal_3st)
                    )
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = colorResource(id = R.color.font_background_color3)
            )

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Header(
                    title = "대학교와 함께하기",
                    description = "내 학교와 같이 뛰면 즐거움과 성취감 두 배!",
                    modifier = Modifier.fillMaxHeight(0.1f)
                ) {
                    startRankingActivity("universityIndividual")
                }

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .aspectRatio(1f), shape = CircleShape
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(universityUserList()[0].imageUrl).crossfade(true).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(108.dp)
                            .padding(start = 32.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row(modifier = Modifier, verticalAlignment = Alignment.Bottom) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(universityUserList()[0].universityLogo).crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                            Text(
                                text = universityUserList()[0].universityName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                        Text(
                            text = universityUserList()[0].nickName,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                append(universityUserList()[0].score.numberComma())
                                append("점")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.font_background_color3)
                                )
                            ) {
                                append(" (")
                                append(universityUserList()[0].contribution.round())
                                append("%)")
                            }


                        })
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                append(universityUserList()[0].rank.toString())
                                append("등")
                            }
                        })
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    UniversityIndividualTitleRow()

                    universityUserList()[0].apply {
                        Log.d("daeYoung", "UniversityScreen() 재호출")
                        UniversityIndividualContentRow(
                            rank = this.rank,
                            nickname = this.nickName,
                            score = this.score.numberComma(),
                            contribution = this.contribution,  /* TODO(기여도 대학교 로고로 바꿀 것)*/
                            color = colorResource(id = R.color.main_color4)
                        )
                    }

                    universityUserList().subList(1, universityUserList().size)
                        .forEach { universityUser ->
                            UniversityIndividualContentRow(
                                rank = universityUser.rank,
                                nickname = universityUser.nickName,
                                score = universityUser.score.numberComma(),
                                contribution = universityUser.contribution
                            )
                        }
                }
            }
        }
    }
}