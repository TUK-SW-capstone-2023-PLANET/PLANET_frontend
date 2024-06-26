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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.MiddleHead
import com.example.planet.presentation.ui.component.SearchTextField
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityProfile
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityContentRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.component.UniversityTitleRow
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.presentation.util.noRippleClickable
import com.example.planet.presentation.util.numberComma

@Composable
fun UniversityRankingScreen(mainViewModel: MainViewModel, onBack: () -> Unit) {
    val universityList: LazyPagingItems<University> =
        mainViewModel.totalUniversity.collectAsLazyPagingItems()


    LaunchedEffect(Unit) {
        mainViewModel.getAllUniversities()
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
            Icon(imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                tint = colorResource(id = R.color.font_background_color1),
                modifier = Modifier.noRippleClickable { onBack() })
        }
        MiddleHead(
            image = painterResource(id = R.drawable.plogging_ranking_universitylogo),
            title = "대학교 랭킹",
            description = "학교를 인증하여, 학교의 위상을 높히세요!!",
        )

        if (mainViewModel.higherUniversity.isEmpty()) {
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
        }



        SearchTextField(
            text = { mainViewModel.searchText.value },
            onValueChange = mainViewModel.changeSearchText,
            fontSize = 12.sp,
            placeholder = "search",
            modifier = Modifier,
            verticalSpace = 9.dp
        ) {
            mainViewModel.searchUniversity(mainViewModel.searchText.value)
        }

        UniversityTitleRow()

        if (mainViewModel.searchText.value.isEmpty()) {
            mainViewModel.universityRankResult = emptyList()
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
        } else {
            mainViewModel.universityRankResult.forEach {
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

