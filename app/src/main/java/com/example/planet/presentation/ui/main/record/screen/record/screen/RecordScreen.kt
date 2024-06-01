package com.example.planet.presentation.ui.main.record.screen.record.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.planet.TAG
import com.example.planet.presentation.ui.main.record.screen.record.component.RecordCalendar
import com.example.planet.presentation.viewmodel.RecordViewModel
import kotlinx.collections.immutable.toImmutableList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordScreen(
    recordViewModel: RecordViewModel,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Log.d(TAG, "RecordScreen 리컴포지션")

//    LaunchedEffect(Unit) {
//        recordViewModel.readPloggingActiveList(year = recordViewModel.currentDate.year, month = recordViewModel.currentDate.month.value)
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)

    ) {
        RecordCalendar(
            modifier = Modifier.fillMaxWidth(),
            ploggingActivityList = recordViewModel.allPloggingActiveDays.toImmutableList(),
//            setMonth = {
//                recordViewModel.currentDate = it
//                scope.launch {
//                    recordViewModel.readPloggingActiveList(year = recordViewModel.currentDate.year, month = recordViewModel.currentDate.month.value)
//                }
//            },
//            onSelectedDate = {
//                if (recordViewModel.selectedDate == null) recordViewModel.selectedDate =
//                    it else recordViewModel.selectedDate = null
//            },
//            readPloggingActiveList = { year, month ->
//                scope.launch {
//                    recordViewModel.readPloggingActiveList(year = recordViewModel.currentDate.year, month = recordViewModel.currentDate.month.value)
//                }
//            }
        )
//        HorizontalDivider(
//            thickness = 1.dp, modifier = Modifier
//                .padding(vertical = 16.dp, horizontal = 20.dp)
//                .fillMaxWidth(),
//            color = colorResource(id = R.color.font_background_color1)
//        )
//        repeat(recordViewModel.selectedPloggingActiveList.size) {
//            PloggingCard(
//                image = "https://tuk-planet.s3.ap-northeast-2.amazonaws.com/images/c4fbb3bd-4c90-44cf-9973-a671db85cdab-filename.jpg",
//                address = "단원구",
//                trashCount = 12,
//                distance = "1.2km",
//                ploggingTime = "타임오바"
//            )
//        }
    }
}