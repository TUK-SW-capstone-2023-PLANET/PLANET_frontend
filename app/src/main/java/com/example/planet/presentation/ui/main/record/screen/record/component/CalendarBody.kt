package com.example.planet.presentation.ui.main.record.screen.record.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planet.TAG
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarBody(
    currentDate: LocalDate,
    today: LocalDate,
    selectedDate: LocalDate?,
    ploggingActivityList: List<Int>,
    onSelectedDate: (LocalDate) -> Unit
) {
    val firstDayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek.value // 첫 주에 시작하는 요일 ex) 5(금요일)
    val lastDay = currentDate.lengthOfMonth()        // 마지막 일자, ex) 31
    val days = IntRange(1, lastDay).toList()    // ex) 1, 2, 3, 4, ... , 31
    var dayIndex = 0

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        HorizontalDayOfWeek()
        Column(modifier = Modifier.fillMaxWidth()) {
            val rows = if (lastDay % 7 == 0) lastDay / 7 else lastDay / 7 + 1

            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(7) {
                    if (it < firstDayOfWeek) {
                        Box(modifier = Modifier.weight(1f))
                    } else {
                        Log.d(TAG, "dayIndex: $dayIndex, day: ${days[dayIndex]}")

                        val date = currentDate.withDayOfMonth(days[dayIndex])
                        CalendarDay(
                            day = date,
                            isToday = (date == today),
                            isSelected = (date == selectedDate),
                            isPlogging = (ploggingActivityList.contains(days[dayIndex++])),
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        ) { onSelectedDate(date) }
                    }
                }
            }
            while (dayIndex+1 <= lastDay+(lastDay%7)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    run loop@ {
                        repeat(7) {
                            if (dayIndex > days.lastIndex) {
                                Box(modifier = Modifier.weight(1f))
                                dayIndex++
                            } else {
                                Log.d(TAG, "dayIndex: $dayIndex, day: ${days[dayIndex]}")

                                val date = currentDate.withDayOfMonth(days[dayIndex])
                                CalendarDay(
                                    day = date,
                                    isToday = (date == today),
                                    isSelected = (date == selectedDate),
                                    isPlogging = (ploggingActivityList.contains(days[dayIndex++])),
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                ) { onSelectedDate(date) }
                            }

                        }
                    }
                }
            }
        }
//        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
//            for (i in 1..firstDayOfWeek) { // 일요일부터 시작하니까 .. 사용, 월요일부터 시작하면 until 사용
//                item { Box(modifier = Modifier.weight(1f)) }
//            }
//            items(days) { day ->
//                // 이번 달의 날짜를 day로 치환하여 CalendarDay로 넘긴다. ex) 2024-05-01
//                val date = currentDate.withDayOfMonth(day)
//                CalendarDay(
//                    day = date,
//                    isToday = (date == today),
//                    isSelected = (date == selectedDate),
//                    isPlogging = (ploggingActivityList.contains(day)),
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                ) { onSelectedDate(date) }
//            }
//        }
    }
}