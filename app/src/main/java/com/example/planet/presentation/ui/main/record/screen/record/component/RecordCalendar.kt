package com.example.planet.presentation.ui.main.record.screen.record.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planet.TAG
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordCalendar(
    modifier: Modifier,
    ploggingActivityList: ImmutableList<Int>,
//    setMonth: (LocalDate) -> Unit,
//    onSelectedDate: (LocalDate) -> Unit,
//    readPloggingActiveList: (Int, Int) -> Unit
) {
    Log.d(TAG, "calendar 리컴포지션")

    var currentDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var today by remember { mutableStateOf(LocalDate.now()) }
    var selectedDate: LocalDate? by remember { mutableStateOf(null) }

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(3.dp)
    ) {
        Column {
            CalendarHeader(
                yearMonth = currentDate,
                onNextMonth = { currentDate = currentDate.plusMonths(1).withDayOfMonth(1) }
            ) { currentDate = currentDate.minusMonths(1).withDayOfMonth(1) }
            CalendarBody(
                currentDate = currentDate,
                selectedDate = selectedDate,
                today = today,
                ploggingActivityList = ploggingActivityList
            ) /*{ onSelectedDate(it) } */
        }
    }
}