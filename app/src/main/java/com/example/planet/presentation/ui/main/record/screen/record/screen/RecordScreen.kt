package com.example.planet.presentation.ui.main.record.screen.record.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planet.presentation.ui.main.record.screen.record.component.RecordCalendar
import com.example.planet.presentation.viewmodel.RecordViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordScreen(
    recordViewModel: RecordViewModel,
) {
    RecordCalendar(
        currentDate = recordViewModel.currentDate,
        today = recordViewModel.today,
        selectedDate = recordViewModel.selectedDate,
        ploggingActivityList = emptyList(),
        setMonth = { recordViewModel.currentDate = it },
        onSelectedDate = { recordViewModel.selectedDate = it }
    )
    HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp).fillMaxWidth())


}