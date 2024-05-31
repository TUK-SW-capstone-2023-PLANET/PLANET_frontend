package com.example.planet.presentation.ui.main.record.screen.record.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordCalendar(
    modifier: Modifier,
    currentDate: LocalDate = LocalDate.now(),
    today: LocalDate,
    selectedDate: LocalDate?,
    ploggingActivityList: List<Int>,
    setMonth: (LocalDate) -> Unit,
    onSelectedDate: (LocalDate) -> Unit
) {
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM")

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(3.dp)
    ) {
        Column {
            CalendarHeader(yearMonth = currentDate.format(dateTimeFormatter),
                onNextMonth = { setMonth(currentDate.plusMonths(1).withDayOfMonth(1)) }) {
                setMonth(currentDate.minusMonths(1).withDayOfMonth(1))
            }
            CalendarBody(currentDate = currentDate, selectedDate = selectedDate, today = today, ploggingActivityList = ploggingActivityList) {
                onSelectedDate(it)
            }
        }
    }
}