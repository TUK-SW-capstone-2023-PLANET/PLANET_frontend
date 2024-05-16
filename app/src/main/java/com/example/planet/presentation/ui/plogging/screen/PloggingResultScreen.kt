package com.example.planet.presentation.ui.plogging.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.data.remote.dto.response.plogging.PloggingResult
import com.example.planet.presentation.ui.plogging.component.DateCard
import com.example.planet.presentation.ui.plogging.component.PloggingReceipt
import com.example.planet.presentation.ui.plogging.component.PloggingResultTopAppBar
import com.example.planet.presentation.ui.plogging.component.ResultGraphBoard
import com.example.planet.presentation.ui.plogging.component.ResultMap
import com.example.planet.presentation.ui.plogging.component.ScoreBoard
import com.example.planet.presentation.util.round2
import com.example.planet.presentation.util.secondsFormatTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PloggingResultScreen(ploggingInfo: PloggingResult, onBack: (Boolean) -> Unit) {

    val sheetState = rememberStandardBottomSheetState()
    val bottomSheetState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)


    val scrollState = rememberScrollState()
    val bottomSheetTitleStyle =
        TextStyle(
            color = colorResource(id = R.color.font_background_color1),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        )

    BackHandler {
        onBack(true)
    }

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
                Text(
                    text = "플로깅 영수증",
                    style = bottomSheetTitleStyle,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                PloggingReceipt(receipt = ploggingInfo.trashInfo)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(scrollState)
        ) {
            PloggingResultTopAppBar(onDone = { onBack(it) })
            DateCard(yearMonth = ploggingInfo.uploadTime, hourMinutes = ploggingInfo.runningTime)
            ResultMap(
                cameraPosition = ploggingInfo.middleLocation,
                pathOverlay = ploggingInfo.location
            )
            ScoreBoard(
                time = ploggingInfo.ploggingTime.secondsFormatTime(),
                distance = ploggingInfo.distance.round2(),
                kcal = ploggingInfo.kcal.toString(),
                pace = ploggingInfo.pace,
                totalTrashCount = ploggingInfo.trashCount.toString(),
                score = ploggingInfo.score.toString()
            )
            HorizontalDivider(
                thickness = 3.dp,
                color = colorResource(id = R.color.font_background_color3)
            )
            ResultGraphBoard(
                trashList = ploggingInfo.trash,
                totalScore = ploggingInfo.score,
                totalCount = ploggingInfo.trashCount
            )
        }

    }
}





