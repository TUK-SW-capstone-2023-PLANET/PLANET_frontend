package com.example.planet.presentation.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planet.component.map.trashList.TrashCard
import com.example.planet.data.map.Trash

@Composable
fun TrashListScreen(trashList: List<Trash>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(trashList.size) {count ->
            TrashCard(trash = trashList[count].name, count = trashList[count].count, image = trashList[count].image)
            if(count != 9) {  // 마지막 TrashCard에는 밑줄 X
                Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }

}