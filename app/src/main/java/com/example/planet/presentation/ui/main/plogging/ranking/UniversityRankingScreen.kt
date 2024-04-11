package com.example.planet.presentation.ui.main.plogging.ranking

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planet.R
import com.example.planet.component.main.plogging.ranking.MiddleHead
import com.example.planet.component.main.plogging.ranking.SearchTextField
import com.example.planet.component.main.plogging.ranking.UniversityProfile
import com.example.planet.data.remote.dto.ranking.University
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.util.noRippleClickable
import com.example.planet.util.numberComma

@Composable
fun UniversityRankingScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val planetUserList: LazyPagingItems<University> =
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
            items(planetUserList.itemCount) { index ->
                planetUserList[index]?.let {
                    UniversityContentRow(
                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
                        rank = it.rank,
                        universityName = it.name,
                        score = it.score.numberComma(),
                        universityLogo = it.imageUrl,
                        color = colorResource(id = R.color.main_color4)
                    )
                }
            }
        }
//        mainViewModel.totalUniversity.forEachIndexed { index, university ->
//            when (index) {
//                0 -> {
//                    UniversityContentRow(
//                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
//                        rank = university.rank,
//                        universityName = university.name,
//                        score = university.score.numberComma(),
//                        universityLogo = university.imageUrl,
//                        color = colorResource(id = R.color.main_color4)
//                    )
//                }
//
//                1 -> {
//                    UniversityContentRow(
//                        medal = {
//                            Divider(
//                                color = colorResource(id = R.color.ranking_color1),
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .width(4.dp)
//                            )
//                            Spacer(modifier = Modifier.width(20.dp))
//                        },
//                        rank = university.rank,
//                        universityName = university.name,
//                        score = university.score.numberComma(),
//                        universityLogo = university.imageUrl
//                    )
//                }
//
//                2 -> {
//                    UniversityContentRow(
//                        medal = {
//                            Divider(
//                                color = colorResource(id = R.color.ranking_color2),
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .width(4.dp)
//                            )
//                            Spacer(modifier = Modifier.width(20.dp))
//                        },
//                        rank = university.rank,
//                        universityName = university.name,
//                        score = university.score.numberComma(),
//                        universityLogo = university.imageUrl
//                    )
//                }
//
//                3 -> {
//                    UniversityContentRow(
//                        medal = {
//                            Divider(
//                                color = colorResource(id = R.color.ranking_color3),
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .width(4.dp)
//                            )
//                            Spacer(modifier = Modifier.width(20.dp))
//                        },
//                        rank = university.rank,
//                        universityName = university.name,
//                        score = university.score.numberComma(),
//                        universityLogo = university.imageUrl
//                    )
//                }
//
//                else -> {
//                    UniversityContentRow(
//                        medal = { Spacer(modifier = Modifier.width(24.dp)) },
//                        rank = university.rank,
//                        universityName = university.name,
//                        score = university.score.numberComma(),
//                        universityLogo = university.imageUrl
//                    )
//                }
//            }
//
//        }

    }

}

@Composable
fun UniversityTitleRow() {
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 2.dp, bottom = 2.dp)
    ) {

        Text(
            text = "순위",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = "학교",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.1f)
        )
        Text(
            text = "이름",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = "점수",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.4f)
        )
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
}

@Composable
fun UniversityContentRow(
    medal: @Composable () -> Unit,
    rank: Int,
    universityLogo: String,
    universityName: String,
    score: String,
    color: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(color = color)
            .padding(vertical = 2.dp)
    ) {
        medal()

        Text(
            text = rank.toString(),
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.08f)
        )
        Box(modifier = Modifier.weight(0.12f)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(universityLogo)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(1f).align(Alignment.CenterStart)
            )
        }
        Text(
            text = universityName,
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = "$score 점",
            color = colorResource(id = R.color.font_background_color2),
            fontSize = 12.sp,
            modifier = Modifier.weight(0.4f)
        )
    }
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.font_background_color3)
    )
}