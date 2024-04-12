package com.example.planet.presentation.ui.main.plogging.ranking

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
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.planet.R
import com.example.planet.component.main.plogging.ranking.MiddleHead
import com.example.planet.component.main.plogging.ranking.SearchTextField
import com.example.planet.component.main.plogging.ranking.UniversityProfile
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.presentation.ui.main.plogging.ranking.component.UniversityContentRow
import com.example.planet.presentation.ui.main.plogging.ranking.component.UniversityTitleRow
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma

@Composable
fun UniversityRankingScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val universityList: LazyPagingItems<University> =
        mainViewModel.totalUniversity.collectAsLazyPagingItems()

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
            title = "대학교 랭킹",
            description = "학교를 인증하여, 학교의 위상을 높히세요!!",
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            UniversityProfile(
                imageUrl = mainViewModel.higherUniversity[1].imageUrl,
                imageSize = 55.dp,
                medal = painterResource(id = R.drawable.medal_2st),
                universityName = mainViewModel.higherUniversity[1].name
            )

            UniversityProfile(
                imageUrl = mainViewModel.higherUniversity[0].imageUrl,
                imageSize = 65.dp,
                medal = painterResource(id = R.drawable.medal_1st),
                universityName = mainViewModel.higherUniversity[0].name
            )

            UniversityProfile(
                imageUrl = mainViewModel.higherUniversity[2].imageUrl,
                imageSize = 55.dp,
                medal = painterResource(id = R.drawable.medal_3st),
                universityName = mainViewModel.higherUniversity[2].name
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

        UniversityTitleRow()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(universityList.itemCount) { index ->
                universityList[index]?.let {
                    UniversityContentRow(
                        rank = it.rank,
                        universityName = it.name,
                        score = it.score.numberComma(),
                        universityLogo = it.imageUrl,
                    )
                }
            }
        }
    }
}

