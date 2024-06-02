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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planet.TAG
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordCalendar(
    modifier: Modifier,
    ploggingActivityList: List<Int>,
    setSelectedPloggingList: (Int) -> Unit,
    getPloggingActiveList: (Int, Int, () -> Unit) -> Unit
) {


    Log.d(TAG, "calendar 리컴포지션, ploggingActivityList: $ploggingActivityList")

    var currentDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var today by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(Unit) {
        getPloggingActiveList(currentDate.year, currentDate.month.value) {}
    }

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            CalendarHeader(
                yearMonth = currentDate,
                onNextMonth = {
                    val date = currentDate.plusMonths(1).withDayOfMonth(1)
                    getPloggingActiveList(date.year, date.month.value) { currentDate = date }
                }
            ) {
                val date = currentDate.minusMonths(1).withDayOfMonth(1)
                getPloggingActiveList(date.year, date.month.value) { currentDate = date }
            }
            CalendarBody(
                currentDate = currentDate,
                today = today,
                ploggingActivityList = ploggingActivityList,
                setSelectedPloggingList = setSelectedPloggingList,
            ) { year, month, func -> getPloggingActiveList(year, month){func()} }
        }
    }
}