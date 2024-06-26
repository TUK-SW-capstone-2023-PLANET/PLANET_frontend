package com.example.planet.presentation.ui.main.record.screen.record.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.TAG
import com.example.planet.presentation.ui.main.record.screen.record.component.PloggingCard
import com.example.planet.presentation.ui.main.record.screen.record.component.RecordCalendar
import com.example.planet.presentation.viewmodel.RecordViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordScreen(
    recordViewModel: RecordViewModel,
    startPloggingResultActivity: (Long, String) -> Unit
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val ploggingDateStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    Log.d(TAG, "RecordScreen 리컴포지션")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)

    ) {
        RecordCalendar(
            modifier = Modifier.fillMaxWidth(),
            ploggingActivityList = recordViewModel.allPloggingActiveDays,
            setSelectedPloggingList = { recordViewModel.setSelectedList(it) },
            getPloggingActiveList = { year, month, func ->
                recordViewModel.readPloggingActiveList(year = year, month = month) { func() }
            }
        )
        HorizontalDivider(
            thickness = 1.dp, modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 20.dp)
                .fillMaxWidth(),
            color = Color.White
        )

        Text(
            text = recordViewModel.selectedDate,
            style = ploggingDateStyle,
            modifier = Modifier.padding(start = 20.dp)
        )

        repeat(recordViewModel.selectedPloggingActiveList.size) {
            PloggingCard(
                image = recordViewModel.selectedPloggingActiveList[it].imageUrl,
                address = recordViewModel.selectedPloggingActiveList[it].address,
                trashCount = recordViewModel.selectedPloggingActiveList[it].trashCount,
                distance = recordViewModel.selectedPloggingActiveList[it].distance,
                ploggingTime = recordViewModel.selectedPloggingActiveList[it].ploggingTime
            ) {
                startPloggingResultActivity(
                    recordViewModel.selectedPloggingActiveList[it].ploddingId,
                    "dark"
                )
            }
        }
    }
}