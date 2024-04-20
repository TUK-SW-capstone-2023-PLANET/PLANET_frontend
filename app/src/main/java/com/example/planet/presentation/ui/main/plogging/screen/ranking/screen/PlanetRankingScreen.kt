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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.planet.R
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.MiddleHead
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.SearchTextField
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.TrophyProfile
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityIndividualContentRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityIndividualTitleRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma

@Composable
fun PlanetRankingScreen(mainViewModel: MainViewModel = hiltViewModel()) {

    val planetUserList: LazyPagingItems<PlanetRankingUser> =
        mainViewModel.totalPlanetRankingUser.collectAsLazyPagingItems()


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
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = colorResource(id = R.color.font_background_color1),
                modifier = Modifier.noRippleClickable { mainViewModel.showRankingScreen = ScreenNav.HomeScreen }
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

        SearchTextField(
            text = mainViewModel.searchText.value,
            onValueChange = mainViewModel.changeSearchText,
            fontSize = 12.sp,
            placeholder = "search"
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }

        UniversityIndividualTitleRow()
        mainViewModel.myPlanetRank.value?.let { myRank ->
            UniversityIndividualContentRow(
                rank = myRank.rank,
                nickname = myRank.nickName,
                score = myRank.score.numberComma(),
                contribution = 1.7,  /* TODO(기여도 대학교 로고로 바꿀 것)*/
                color = colorResource(id = R.color.main_color4)
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(planetUserList.itemCount) { index ->
                planetUserList[index]?.let {
                    UniversityIndividualContentRow(
                        rank = it.rank,
                        nickname = it.nickName,
                        score = it.score.numberComma(),
                        contribution = 1.7,  /* TODO(기여도 대학교 로고로 바꿀 것)*/
                    )
                }
            }
        }
    }
}
