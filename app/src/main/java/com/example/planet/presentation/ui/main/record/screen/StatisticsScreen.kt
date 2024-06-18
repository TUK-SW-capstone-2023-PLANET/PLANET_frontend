package com.example.planet.presentation.ui.main.record.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.planet.R
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.statistics.StatisticsPloggingInfo
import com.example.planet.presentation.viewmodel.RecordViewModel
import kotlinx.coroutines.launch
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticsScreen(recordViewModel: RecordViewModel) {
    var switch by remember {
        mutableStateOf(true)
    }
    val coroutineScope = rememberCoroutineScope()

    var monthPloggingData =
        recordViewModel.statisticsPloggingLogResult.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        recordViewModel.readWeekPloggingLog(recordViewModel.userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val titleStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        val switchOffColor = colorResource(id = R.color.font_background_color3)
        val switchOnColor = Color.White

        Text(
            text = "플로깅 점수 기록",
            style = titleStyle,
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)
        )

        if (monthPloggingData is ApiState.Success<*>) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(top = 16.dp, bottom = 36.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .border(
                                width = 1.dp,
                                color = colorResource(id = R.color.font_background_color3),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(id = R.color.font_background_color3)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .drawBehind { drawRect(if (switch) switchOnColor else switchOffColor) }
                            .clickable {
                                coroutineScope.launch {
                                    if (!switch) {
                                        recordViewModel.readWeekPloggingLog(recordViewModel.userId)
                                        if (monthPloggingData is ApiState.Success<*>) {
                                            switch = true
                                        }
                                    }
                                }
                            }) {
                            Text(
                                text = "1주일",
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }
                        Box(modifier = Modifier
                            .weight(1f)
                            .drawBehind { drawRect(if (!switch) switchOnColor else switchOffColor) }
                            .clickable {
                                coroutineScope.launch {
                                    if (switch) {
                                        recordViewModel.readMonthPloggingLog(
                                            recordViewModel.userId,
                                            YearMonth.now().year,
                                            YearMonth.now().monthValue
                                        )
                                        if (monthPloggingData is ApiState.Success<*>) {
                                            switch = false
                                        }
                                    }
                                }
                            }) {
                            Text(
                                text = "1개월",
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Text(
                    text = "평균",
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = colorResource(id = R.color.font_background_color2)
                    ),
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        ) {
                            append((monthPloggingData.value as StatisticsPloggingInfo).average.toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(id = R.color.font_background_color2)
                            )
                        ) {
                            append("점")
                        }
                    },
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                val dataList =
                    (monthPloggingData.value as StatisticsPloggingInfo).dataList
                if (switch) {
                    WeekLineGraph(
                        rowCount = dataList.size,
                        rowDottedList = dataList.map { it.day },
                        lastColumnValue = dataList.maxOf { it.score },
                        list = dataList.map { it.score }
                    )
                } else {
                    LineGraph(
                        rowCount = dataList.size,
                        lastRowValue = dataList[dataList.lastIndex].day.toInt(),
                        lastColumnValue = dataList.maxOf { it.score },
                        list = dataList.map { it.score }
                    )
                }

            }
        } else if (monthPloggingData is ApiState.Loading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun LineGraph(rowCount: Int, lastRowValue: Int, lastColumnValue: Int, list: List<Int>) {
    val rowDottedLineCount = 4
    val columnDottedLineCount = 3
    val textMeasurer = rememberTextMeasurer()

    val style = TextStyle(
        fontSize = 15.sp,
        color = Color.White,
    )

    val rowDottedLineTextList = mutableListOf<TextLayoutResult>()
    val columnDottedLineTextList = mutableListOf<TextLayoutResult>()

    for (i in 0..rowDottedLineCount) {
        val text = (lastRowValue * (i / rowDottedLineCount.toFloat())).toInt().toString()
        rowDottedLineTextList.add(remember(text) { textMeasurer.measure(text, style) })
    }
    for (i in 0..columnDottedLineCount) {
        val text = (lastColumnValue * (i / columnDottedLineCount.toFloat())).toInt().toString()
        columnDottedLineTextList.add(remember(text) { textMeasurer.measure(text, style) })
    }
    val lastRowPadding = columnDottedLineTextList.maxOf { it.size.width }
    val lineColor = colorResource(id = R.color.font_background_color3)
    val dataLineColor = colorResource(id = R.color.main_color2)
    val textColor = colorResource(id = R.color.font_background_color2)

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f)
    ) {
        val rowSize = size.width - (lastRowPadding + 10f)
        val test = size.width - lastRowPadding
        drawLine(
            color = lineColor,
            start = Offset(0f, size.height),
            end = Offset(rowSize, size.height),
            strokeWidth = 2f
        )
        drawLine(
            color = lineColor,
            start = Offset(0f, size.height),
            end = Offset(0f, 0f),
            strokeWidth = 2f
        )
        drawLine(
            color = lineColor,
            start = Offset(rowSize, size.height),
            end = Offset(rowSize, 0f),
            strokeWidth = 2f
        )
        // column 점선 데이터
        for (i in 1..columnDottedLineCount) {
            drawText(
                textLayoutResult = columnDottedLineTextList[i],
                topLeft = Offset(
                    size.width - lastRowPadding,
                    size.height * (1 - i / columnDottedLineCount.toFloat())
                ),
                color = textColor
            )
        }


        // row 점선 데이터
        repeat(rowDottedLineCount + 1) {
            if (it == 0) {
                drawText(
                    textLayoutResult = rowDottedLineTextList[it],
                    topLeft = Offset(rowSize * (it / rowDottedLineCount.toFloat()), size.height),
                    color = textColor
                )
            } else if (it == rowDottedLineCount) {
                drawText(
                    textLayoutResult = rowDottedLineTextList[it],
                    topLeft = Offset(
                        rowSize * (it / rowDottedLineCount.toFloat()) - rowDottedLineTextList[it].size.width,
                        size.height
                    ),
                    color = textColor
                )
            } else {
                drawText(
                    textLayoutResult = rowDottedLineTextList[it],
                    topLeft = Offset(
                        rowSize * (it / rowDottedLineCount.toFloat()) - rowDottedLineTextList[it].size.width / 2,
                        size.height
                    ),
                    color = textColor
                )
            }
        }

        // 데이터의 그래프
        for (i in 0 until list.size - 1) {
            if (i == 0) {
                drawLine(
                    color = dataLineColor,
                    start = Offset(0f, size.height * (1 - list[i] / lastColumnValue.toFloat())),
                    end = Offset(
                        rowSize / (rowCount - 1),
                        size.height * (1 - list[1] / lastColumnValue.toFloat())
                    ),
                    strokeWidth = 5f
                )
            } else {
                drawLine(
                    color = dataLineColor,
                    start = Offset(
                        rowSize * (i / (rowCount - 1).toFloat()),
                        size.height * (1 - list[i] / lastColumnValue.toFloat())
                    ),
                    end = Offset(
                        rowSize * ((i + 1) / (rowCount - 1).toFloat()),
                        size.height * (1 - list[i + 1] / lastColumnValue.toFloat())
                    ),
                    strokeWidth = 5f
                )
            }
        }
    }

}

@Composable
fun WeekLineGraph(
    rowCount: Int,
    rowDottedList: List<String>,
    lastColumnValue: Int,
    list: List<Int>
) {
    val rowDottedLineCount = 7
    val columnDottedLineCount = 3
    val textMeasurer = rememberTextMeasurer()

    val style = TextStyle(
        fontSize = 15.sp,
        color = Color.White,
    )

    val rowDottedLineTextList = mutableListOf<TextLayoutResult>()
    val columnDottedLineTextList = mutableListOf<TextLayoutResult>()

    rowDottedList.forEach {
        rowDottedLineTextList.add(remember(it) { textMeasurer.measure(it, style) })
    }

    for (i in 0..columnDottedLineCount) {
        val text = (lastColumnValue * (i / columnDottedLineCount.toFloat())).toInt().toString()
        columnDottedLineTextList.add(remember(text) { textMeasurer.measure(text, style) })
    }
    val lastRowPadding = columnDottedLineTextList.maxOf { it.size.width }
    val lineColor = colorResource(id = R.color.font_background_color3)
    val dataLineColor = colorResource(id = R.color.main_color2)
    val textColor = colorResource(id = R.color.font_background_color2)

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f)
    ) {
        val rowSize = size.width - (lastRowPadding + 10f)
        val test = size.width - lastRowPadding
        drawLine(
            color = lineColor,
            start = Offset(0f, size.height),
            end = Offset(rowSize, size.height),
            strokeWidth = 2f
        )
        drawLine(
            color = lineColor,
            start = Offset(0f, size.height),
            end = Offset(0f, 0f),
            strokeWidth = 2f
        )
        drawLine(
            color = lineColor,
            start = Offset(rowSize, size.height),
            end = Offset(rowSize, 0f),
            strokeWidth = 2f
        )
        // column 점선 데이터
        for (i in 1..columnDottedLineCount) {
            drawText(
                textLayoutResult = columnDottedLineTextList[i],
                topLeft = Offset(
                    size.width - lastRowPadding,
                    size.height * (1 - i / columnDottedLineCount.toFloat())
                ),
                color = textColor
            )
        }


        // row 점선 데이터
        repeat(rowDottedLineCount - 1) {
            drawText(
                textLayoutResult = rowDottedLineTextList[it],
                topLeft = Offset(
                    rowSize * (it / (rowDottedLineCount - 1).toFloat()),
                    size.height
                ),
                color = textColor
            )
        }

        // 데이터의 그래프
        for (i in 0 until list.size - 1) {
            if (i == 0) {
                drawLine(
                    color = dataLineColor,
                    start = Offset(0f, size.height * (1 - list[i] / lastColumnValue.toFloat())),
                    end = Offset(
                        rowSize / (rowCount - 1),
                        size.height * (1 - list[1] / lastColumnValue.toFloat())
                    ),
                    strokeWidth = 5f
                )
            } else {
                drawLine(
                    color = dataLineColor,
                    start = Offset(
                        rowSize * (i / (rowCount - 1).toFloat()),
                        size.height * (1 - list[i] / lastColumnValue.toFloat())
                    ),
                    end = Offset(
                        rowSize * ((i + 1) / (rowCount - 1).toFloat()),
                        size.height * (1 - list[i + 1] / lastColumnValue.toFloat())
                    ),
                    strokeWidth = 5f
                )
            }
        }
    }

}