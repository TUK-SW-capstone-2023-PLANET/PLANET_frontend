package com.example.planet.presentation.ui.main.plogging.screen.ranking.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.data.remote.dto.response.ranking.universityuser.UniversityUser
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.MiddleHead
import com.example.planet.presentation.ui.component.SearchTextField
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityIndividualContentRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityIndividualGraph
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityIndividualTitleRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.presentation.util.noRippleClickable
import com.example.planet.presentation.util.numberComma
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UniversityIndividualRankingScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val universityUserList: LazyPagingItems<UniversityUser> =
        mainViewModel.totalUniversityUser.collectAsLazyPagingItems()

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        launch {
            mainViewModel.getAllUniversityUser()
        }
        launch {
            delay(200)
            visible = true
        }
    }

    BackHandler { mainViewModel.showRankingScreen = ScreenNav.HomeScreen }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = colorResource(id = R.color.font_background_color1),
                modifier = Modifier.noRippleClickable {
                    mainViewModel.showRankingScreen = ScreenNav.HomeScreen
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
                        .data(mainViewModel.higherMyUniversityUsers[0].universityLogo)
                        .crossfade(true).build(),
                    contentDescription = null,
                    modifier = Modifier.size(65.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = mainViewModel.higherMyUniversityUsers[0].universityName,
                    color = colorResource(id = R.color.font_background_color1),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            UniversityIndividualGraph(
                visible = visible,
                score = mainViewModel.higherMyUniversityUsers[1].score.numberComma(),
                graphHeight = mainViewModel.universityUserGraphHeightList[0],
                colors = listOf(Color(0XFFD1CFCF), Color(0XFFFFFFFF)),
                userName = mainViewModel.higherMyUniversityUsers[1].nickName,
            )
            UniversityIndividualGraph(
                visible = visible,
                score = mainViewModel.higherMyUniversityUsers[0].score.numberComma(),
                graphHeight = mainViewModel.universityUserGraphHeightList[1],
                colors = listOf(Color(0xFFFFCC31), Color(0XFFFFFFFF)),
                userName = mainViewModel.higherMyUniversityUsers[0].nickName,
            )
            UniversityIndividualGraph(
                visible = visible,
                score = mainViewModel.higherMyUniversityUsers[2].score.numberComma(),
                graphHeight = mainViewModel.universityUserGraphHeightList[2],
                colors = listOf(Color(0xFFE1B983), Color(0XFFFFFFFF)),
                userName = mainViewModel.higherMyUniversityUsers[2].nickName,
            )
        }

        Spacer(modifier = Modifier.padding(bottom = 24.dp))

        SearchTextField(
            text = mainViewModel.searchText.value,
            onValueChange = mainViewModel.changeSearchText,
            fontSize = 12.sp,
            placeholder = "search",
            modifier = Modifier,
            verticalSpace = 9.dp
        )

        UniversityIndividualTitleRow()
        mainViewModel.myUniversityRank.collectAsStateWithLifecycle().value?.let { myRank ->
            UniversityIndividualContentRow(
                rank = myRank.rank,
                nickname = myRank.nickName,
                score = myRank.score.numberComma(),
                contribution = myRank.contribution,  /* TODO(기여도 대학교 로고로 바꿀 것)*/
                color = colorResource(id = R.color.main_color4)
            )
        }

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
    }
}