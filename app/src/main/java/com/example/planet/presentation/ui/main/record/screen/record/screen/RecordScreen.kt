package com.example.planet.presentation.ui.main.record.screen.record.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.planet.presentation.ui.main.record.screen.record.component.RecordCalendar
import com.example.planet.presentation.viewmodel.RecordViewModel
import com.example.planet.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordScreen(
    recordViewModel: RecordViewModel,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)

    ) {
        RecordCalendar(
            modifier = Modifier.fillMaxWidth(),
            currentDate = recordViewModel.currentDate,
            today = recordViewModel.today,
            selectedDate = recordViewModel.selectedDate,
            ploggingActivityList = emptyList(),
            setMonth = { recordViewModel.currentDate = it },
            onSelectedDate = { recordViewModel.selectedDate = it }
        )
        HorizontalDivider(
            thickness = 1.dp, modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 20.dp)
                .fillMaxWidth(),
            color = colorResource(id = R.color.font_background_color1)
        )
    }



}