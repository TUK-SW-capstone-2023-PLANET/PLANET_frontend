package com.example.planet.presentation.ui.plogging.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.ui.plogging.component.DateCard
import com.example.planet.presentation.ui.plogging.component.PloggingResultTopAppBar
import com.example.planet.presentation.ui.plogging.component.ResultGraphBoard
import com.example.planet.presentation.ui.plogging.component.ResultMap
import com.example.planet.presentation.ui.plogging.component.ScoreBoard
import com.naver.maps.map.compose.ExperimentalNaverMapApi


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PloggingResultScreen() {

    val sheetState = rememberStandardBottomSheetState()
    val bottomSheetState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)


    val scrollState = rememberScrollState()
    val bottomSheetTitleStyle =
        TextStyle(
            color = colorResource(id = R.color.font_background_color1),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        )
    val trashList = listOf<TrashItem>(
        TrashItem("종이", 10, 100),
        TrashItem("종이", 10, 20),
        TrashItem("종이", 10, 40),
        TrashItem("종이", 10, 10),
        TrashItem("종이", 10, 70),
        TrashItem("박스", 10, 15),
        TrashItem("종이", 10, 10),
        TrashItem("종이", 10, 0),
        TrashItem("종이", 10, 10),
        TrashItem("종이", 10, 10),
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetShape = RoundedCornerShape(10.dp),
        sheetPeekHeight = 80.dp,
//        sheetPeekHeight = 0.dp,
        sheetTonalElevation = 5.dp,
        sheetShadowElevation = 1.dp,
        sheetContainerColor = Color.White,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "플로깅 영수증", style = bottomSheetTitleStyle, modifier = Modifier.padding(bottom = 24.dp))
                TrashHistory()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(scrollState)
        ) {
            PloggingResultTopAppBar()
            DateCard()
            ResultMap()
            ScoreBoard()
            HorizontalDivider(
                thickness = 3.dp,
                color = colorResource(id = R.color.font_background_color3)
            )
            ResultGraphBoard(trashList = trashList)
        }

    }
}

@Composable
fun TrashHistory() {
    val timeTextStyle = TextStyle(
        color = colorResource(id = R.color.font_background_color1),
        fontSize = 8.sp,
        fontWeight = FontWeight.Medium
    )
    val test = mapOf("asd" to 2)

    val trashesHistoryList = listOf(
        TrashesHistory(
            time = mapOf(
                "오전 8시 23분" to
                        listOf(
                            TrashInfo(
                                imageUrl = "",
                                name = "일반쓰레기",
                                count = 4,
                                address = "경기도 시흥시 산기대학로 237",
                                score = 200,
                                totalScore = 3200
                            ),
                            TrashInfo(
                                imageUrl = "",
                                name = "캔",
                                count = 4,
                                address = "경기도 시흥시 산기대학로 237",
                                score = 300,
                                totalScore = 3500
                            ),
                        )
            )
        ),
        TrashesHistory(
            time = mapOf(
                "오전 8시 25분" to
                        listOf(
                            TrashInfo(
                                imageUrl = "",
                                name = "일반쓰레기",
                                count = 4,
                                address = "경기도 시흥시 산기대학로 237",
                                score = 700,
                                totalScore = 3800
                            )
                        )
            )
        ),
    )
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(trashesHistoryList.size) {
            val time = trashesHistoryList[it].time.keys.toList()[0]
            val trashes = trashesHistoryList[it].time.values.toList()[0]
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.font_background_color3)
                )
                Text(
                    text = time,
                    style = timeTextStyle,
                    modifier = Modifier.padding(start = 24.dp, bottom = 3.dp, top = 7.dp)
                )
                trashes.forEach { trashInfo ->
                    TrashHistoryCard(trashInfo)
                }
            }
        }
    }
}

@Composable
fun TrashHistoryCard(trashInfo: TrashInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Icon(imageVector = Icons.Default.HideImage, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "${trashInfo.name} ${trashInfo.count}개")
                Text(text = trashInfo.address)
            }
        }
        Column {
            Text(text = "+ ${trashInfo.score}점")
            Text(text = "${trashInfo.totalScore}점")
        }
    }
}

data class TrashesHistory(
    val time: Map<String, List<TrashInfo>>
)

data class TrashInfo(
    val imageUrl: String,
    val name: String,
    val count: Int,
    val address: String,
    val score: Int,
    val totalScore: Int
)

data class TrashItem(
    val name: String,
    val count: Int,
    val score: Int,
)
