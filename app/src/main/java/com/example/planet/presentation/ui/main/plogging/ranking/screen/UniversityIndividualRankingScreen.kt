package com.example.planet.presentation.ui.main.plogging.ranking.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.data.remote.dto.response.ranking.universityuser.UniversityUser
import com.example.planet.presentation.ui.main.plogging.ranking.component.MiddleHead
import com.example.planet.presentation.ui.main.plogging.ranking.component.SearchTextField
import com.example.planet.presentation.ui.main.plogging.ranking.component.UniversityIndividualContentRow
import com.example.planet.presentation.ui.main.plogging.ranking.component.UniversityIndividualTitleRow
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma
import com.example.planet.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun UniversityIndividualRankingScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val universityUserList: LazyPagingItems<UniversityUser> =
        mainViewModel.totalUniversityUser.collectAsLazyPagingItems()

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

            UniversityIndividualGraph(
                visible = visible,
                score = mainViewModel.higherMyUniversityUsers.value[1].score.numberComma(),
                graphHeight = mainViewModel.universityUserGraphHeightList[0],
                colors = listOf(Color(0XFFD1CFCF), Color(0XFFFFFFFF)),
                userName = mainViewModel.higherMyUniversityUsers.value[1].nickName,
            )
            UniversityIndividualGraph(
                visible = visible,
                score = mainViewModel.higherMyUniversityUsers.value[0].score.numberComma(),
                graphHeight = mainViewModel.universityUserGraphHeightList[1],
                colors = listOf(Color(0xFFFFCC31), Color(0XFFFFFFFF)),
                userName = mainViewModel.higherMyUniversityUsers.value[0].nickName,
            )
            UniversityIndividualGraph(
                visible = visible,
                score = mainViewModel.higherMyUniversityUsers.value[2].score.numberComma(),
                graphHeight = mainViewModel.universityUserGraphHeightList[2],
                colors = listOf(Color(0xFFE1B983), Color(0XFFFFFFFF)),
                userName = mainViewModel.higherMyUniversityUsers.value[2].nickName,
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
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(universityUserList.itemCount) { index ->
                universityUserList[index]?.let {
                    UniversityIndividualContentRow(
                        rank = it.rank,
                        nickname = it.nickName,
                        score = it.score.numberComma(),
                        contribution = it.contribution,
                    )
                }
            }
        }
//        mainViewModel.myUniversityTop4RankingUsers.forEachIndexed { index, universityPerson ->
//            UniversityIndividualContentRow(
//                rank = universityPerson.rank,
//                nickname = universityPerson.nickName,
//                score = universityPerson.score.numberComma(),
//                contribution = universityPerson.contribution,
//            )
//        }
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