package com.example.planet.presentation.ui.main.record.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatisticsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val randomList = listOf(
            482, 693, 245, 980, 104, 657, 387, 432, 789, 56,
            234, 874, 120, 561, 302, 918, 276, 453, 730, 667,
            350, 815, 299, 503, 142, 975, 421, 684, 238, 590
        )
        LineGraph(
            rowCount = randomList.size,
            lastRowValue = 100,
            lastColumnValue = randomList.max(),
            list = randomList
        )
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
    for (i in 0..rowDottedLineCount) {
        val text = (lastRowValue * (i / rowDottedLineCount.toFloat())).toInt().toString()
        rowDottedLineTextList.add(remember(text) { textMeasurer.measure(text, style) })
    }


    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f)
    ) {
        drawLine(
            color = Color.White,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.White,
            start = Offset(0f, size.height),
            end = Offset(0f, 0f),
            strokeWidth = 2f
        )
        repeat(rowDottedLineCount + 1) {
            if (it == 0) {
                drawText(
                    textLayoutResult = rowDottedLineTextList[it],
                    topLeft = Offset(size.width * (it / rowDottedLineCount.toFloat()), size.height)
                )
            } else if (it == rowDottedLineCount) {
                drawText(
                    textLayoutResult = rowDottedLineTextList[it],
                    topLeft = Offset(
                        size.width * (it / rowDottedLineCount.toFloat()) - rowDottedLineTextList[it].size.width,
                        size.height
                    )
                )
            } else {
                drawText(
                    textLayoutResult = rowDottedLineTextList[it],
                    topLeft = Offset(
                        size.width * (it / rowDottedLineCount.toFloat()) - rowDottedLineTextList[it].size.width / 2,
                        size.height
                    )
                )
            }
        }

        for (i in 0 until list.size - 1) {
            if (i == 0) {
                drawLine(
                    color = Color.Blue,
                    start = Offset(0f, size.height - size.height*list[i]/lastColumnValue),
                    end = Offset(size.width/rowCount, size.height - size.height*list[1]/lastColumnValue),
                    strokeWidth = 2f
                )
            } else {
//                drawLine(
//                    color = Color.Blue,
//                    start = Offset(size.width*(index/rowCount), size.height),
//                    end = Offset(size.width*(index+1/rowCount), 0f),
//                    strokeWidth = 2f
//                )
                drawLine(
                    color = Color.Blue,
                    start = Offset(size.width*(i/rowCount.toFloat()), size.height - size.height*list[i]/lastColumnValue),
                    end = Offset(size.width*((i+1)/rowCount.toFloat()), size.height - size.height*list[i+1]/lastColumnValue),
                    strokeWidth = 2f
                )
            }
        }
    }

}

@Composable
fun DrawScope.CanvasText(text: String, offset: Offset) {
    val textMeasurer = rememberTextMeasurer()

    val style = TextStyle(
        fontSize = 15.sp,
        color = Color.White,
        background = Color.Red.copy(alpha = 0.2f)
    )

    val textLayoutResult = remember(text) {
        textMeasurer.measure(text, style)
    }
    drawText(textLayoutResult = textLayoutResult)
}