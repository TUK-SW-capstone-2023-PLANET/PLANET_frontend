package com.example.planet.presentation.ui.main.plogging.screen.ranking.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.planet.R
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.presentation.ui.component.SearchTextField
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.MiddleHead
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.PlanetContentRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.PlanetTitleRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.TrophyProfile
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityIndividualContentRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityIndividualTitleRow
import com.example.planet.presentation.util.noRippleClickable
import com.example.planet.presentation.util.numberComma
import com.example.planet.presentation.viewmodel.MainViewModel

@Composable
fun PlanetRankingScreen(mainViewModel: MainViewModel, onBack: () -> Unit) {

    val planetUserList: LazyPagingItems<PlanetRankingUser> =
        mainViewModel.totalPlanetRankingUser.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        mainViewModel.getAllPlanetUserRanking()
    }
    DisposableEffect(Unit) {
        onDispose { mainViewModel.changeSearchText("") }
    }


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
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = colorResource(id = R.color.font_background_color1),
                modifier = Modifier.noRippleClickable { onBack() }
            )
        }
        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_planet),
            title = "플래닛 랭킹",
            description = "플래닛 누적점수를 통해 최고의 자리를 차지하세요.",
        )

        if (mainViewModel.higherPlanetUsers.isEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                CircularProgressIndicator()
            }
        } else {
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
        }

        SearchTextField(
            text = { mainViewModel.searchText.value },
            onValueChange = mainViewModel.changeSearchText,
            fontSize = 12.sp,
            placeholder = "search",
            modifier = Modifier,
            verticalSpace = 9.dp
        ) {
            mainViewModel.searchPlanetUser(mainViewModel.searchText.value)
        }

        PlanetTitleRow()

        if (mainViewModel.searchText.value.isEmpty()) {
            mainViewModel.planetRankResult = emptyList()
            mainViewModel.myPlanetRank.value?.let { myRank ->
                PlanetContentRow(
                    rank = myRank.rank,
                    nickname = myRank.nickName,
                    score = myRank.score.numberComma(),
                    universityLogo = myRank.universityLogo,
                    color = colorResource(id = R.color.main_color4)
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(planetUserList.itemCount) { index ->
                        planetUserList[index]?.let {
                            PlanetContentRow(
                                rank = it.rank,
                                nickname = it.nickName,
                                score = it.score.numberComma(),
                                universityLogo = it.universityLogo,
                            )
                        }
                    }
                }
            }
        } else {
            mainViewModel.planetRankResult.forEach {
                PlanetContentRow(
                    rank = it.rank,
                    nickname = it.nickName,
                    score = it.score.numberComma(),
                    universityLogo = it.universityLogo,
                )
            }
        }


    }
}
