package com.example.planet.presentation.ui.main.plogging.screen.ranking.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.planet.R
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.MiddleHead
import com.example.planet.presentation.ui.component.SearchTextField
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.SeasonContentRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.SeasonTitleRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.TearProfile
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.presentation.util.noRippleClickable
import com.example.planet.presentation.util.numberComma

@Composable
fun SeasonRankingScreen(mainViewModel: MainViewModel, onBack: () -> Unit) {
    val seasonUserList: LazyPagingItems<SeasonUser> =
        mainViewModel.totalSeasonUser.collectAsLazyPagingItems()


    LaunchedEffect(Unit) {
        mainViewModel.getAllSeasonUser()
    }
    if (seasonUserList.itemSnapshotList.items.isEmpty()) {
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
                    modifier = Modifier.noRippleClickable { onBack() })
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
                placeholder = "search",
                modifier = Modifier,
                verticalSpace = 9.dp
            )

            SeasonTitleRow()
            mainViewModel.mySeasonRank.collectAsStateWithLifecycle().value?.let { myRank ->
                SeasonContentRow(
                    rank = myRank.rank,
                    nickname = myRank.userName,
                    tier = myRank.tierImageUrl,
                    score = myRank.score.numberComma(),
                    universityLogo = myRank.universityLogo,
                    color = colorResource(id = R.color.main_color4)
                )
            }


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(seasonUserList.itemCount) { index ->
                    seasonUserList[index]?.let {
                        SeasonContentRow(
                            rank = it.rank,
                            nickname = it.userName,
                            tier = it.tierImageUrl,
                            score = it.score.numberComma(),
                            universityLogo = it.universityLogo,
                        )
                    }
                }
            }
        }
    }
}

