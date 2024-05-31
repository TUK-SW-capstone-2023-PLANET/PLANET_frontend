package com.example.planet.presentation.ui.main.record.screen.record.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        HorizontalDayOfWeek()
        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            for (i in 1..firstDayOfWeek) { // 일요일부터 시작하니까 .. 사용, 월요일부터 시작하면 until 사용
                item { Box(modifier = Modifier.weight(1f)) }
            }
            items(days) { day ->
                // 이번 달의 날짜를 day로 치환하여 CalendarDay로 넘긴다. ex) 2024-05-01
                val date = currentDate.withDayOfMonth(day)
                CalendarDay(
                    day = date,
                    isToday = (date == today),
                    isSelected = (date == selectedDate),
                    isPlogging = (ploggingActivityList.contains(day)),
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                ) { onSelectedDate(date) }
            }
        }
    }
}