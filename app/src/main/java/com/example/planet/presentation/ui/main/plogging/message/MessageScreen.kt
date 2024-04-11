package com.example.planet.presentation.ui.main.plogging.message

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planet.presentation.ui.main.plogging.ranking.UniversityContentRow
import com.example.planet.presentation.ui.main.plogging.ranking.UniversityTitleRow

@Composable
@Preview(showBackground = true)
fun MessageScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        UniversityTitleRow()
        UniversityContentRow(
            medal = { Spacer(modifier = Modifier.width(24.dp)) },
            rank = 1,
            universityLogo = "",
            universityName = "한국공학대학교",
            score = "2"
        )
    }
}