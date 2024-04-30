package com.example.planet.presentation.ui.plogging.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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


@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
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
                Text(text = "플로깅 영수증", style = bottomSheetTitleStyle)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            PloggingResultTopAppBar()
            Text(text = sheetState.targetValue.name)
            Text(text = sheetState.requireOffset().toString())


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


data class TrashItem(
    val name: String,
    val count: Int,
    val score: Int,
)
