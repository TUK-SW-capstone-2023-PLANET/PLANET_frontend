package com.example.planet.presentation.ui.main.plogging.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.component.common.TripleArrowIcon
import com.example.planet.component.main.SubTitle
import com.example.planet.component.main.SubTitleDescription
import com.example.planet.component.main.plogging.UniversityGraph
import com.example.planet.presentation.ui.main.plogging.ranking.component.UniversityIndividualContentRow
import com.example.planet.presentation.ui.main.plogging.ranking.component.UniversityIndividualTitleRow
import com.example.planet.component.navigation.ScreenNav
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.data.remote.dto.response.ranking.universityuser.ExpandedUniversityUser
import com.example.planet.util.numberComma
import com.example.planet.util.round
import kotlinx.coroutines.delay

@Composable
fun UniversityScreen(
    universityUserList: List<ExpandedUniversityUser>,
    universityList: List<University>,
    graphHeightList: List<Dp>,
    navController: NavController
) {

    var visible by remember { mutableStateOf(false) }
    var scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.Bottom,
            ) {
                SubTitle(title = "대학교 순위", modifier = Modifier.padding(end = 8.dp))
                SubTitleDescription("학교를 인증하여, 학교의 위상을 높히세요!!")
            }
            TripleArrowIcon { navController.navigate(ScreenNav.UniversityRankingScreen.screenRoute) }
        }
        Column(
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                UniversityGraph(
                    visible = visible,
                    universityLogo = universityList[1].imageUrl,
                    score = universityList[1].score.numberComma(),
                    graphHeight = graphHeightList[0],
                    colors = listOf(Color(0XFFD1CFCF), Color(0XFFFFFFFF)),
                    universityName = universityList[1].name,
                    medal = painterResource(id = R.drawable.medal_2st)
                )
                UniversityGraph(
                    visible = visible,
                    universityLogo = universityList[0].imageUrl,
                    score = universityList[0].score.numberComma(),
                    graphHeight = graphHeightList[1],
                    colors = listOf(Color(0xFFFFCC31), Color(0XFFFFFFFF)),
                    universityName = universityList[0].name,
                    medal = painterResource(id = R.drawable.medal_1st)
                )
                UniversityGraph(
                    visible = visible,
                    universityLogo = universityList[2].imageUrl,
                    score = universityList[2].score.numberComma(),
                    graphHeight = graphHeightList[2],
                    colors = listOf(Color(0xFFE1B983), Color(0XFFFFFFFF)),
                    universityName = universityList[2].name,
                    medal = painterResource(id = R.drawable.medal_3st)
                )
            }

        }
        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = colorResource(id = R.color.font_background_color3)
        )
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.wrapContentSize(), verticalAlignment = Alignment.Bottom
                ) {
                    SubTitle(title = "대학교와 함께하기", modifier = Modifier.padding(end = 8.dp))
                    Text(
                        text = "내 학교와 같이 뛰면 즐거움과 성취감 두 배!",
                        color = colorResource(id = R.color.font_background_color2),
                        fontSize = 9.sp
                    )
                }
                TripleArrowIcon { navController.navigate(ScreenNav.UniversityIndividualRankingScreen.screenRoute) }
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
                            .data(universityUserList[0].imageUrl).crossfade(true).build(),
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
                                .data(universityUserList[0].universityLogo).crossfade(true)
                                .build(), contentDescription = null, modifier = Modifier.size(25.dp)
                        )
                        Text(
                            text = universityUserList[0].universityName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.font_background_color1),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                    Text(
                        text = universityUserList[0].nickName,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.font_background_color1)
                    )
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.font_background_color1)
                            )
                        ) {
                            append(universityUserList[0].score.numberComma())
                            append("점")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.font_background_color3)
                            )
                        ) {
                            append(" (")
                            append(universityUserList[0].contribution.round())
                            append("%)")
                        }


                    })
                    Text(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.font_background_color1)
                            )
                        ) {
                            append(universityUserList[0].rank.toString())
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
                universityUserList.forEachIndexed { index, universityUser ->
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