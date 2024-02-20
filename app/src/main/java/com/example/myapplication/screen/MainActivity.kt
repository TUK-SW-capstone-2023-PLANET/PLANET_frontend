package com.example.myapplication.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.network.GeocoderApi
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.util.RequestPermission
import com.example.myapplication.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var geocoderApi: GeocoderApi
//    private val mapViewModel by viewModels<MapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                RequestPermission()
            }
        }
    }
}


@Composable
fun ScrollToTopDerivedAndRememberedCase(lazyListState: LazyListState = rememberLazyListState()) {
    val isEnabledDerivedStateCase by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } }
//    val isEnabledRememberCase = remember(lazyListState.firstVisibleItemIndex) { lazyListState.firstVisibleItemIndex > 0}
    val isEnabledRememberCase = remember { mutableStateOf(lazyListState.firstVisibleItemIndex > 0) }

    Log.d(
        "daeYoung",
        "isEnabledDerivedStateCase: $isEnabledDerivedStateCase\nisEnabledRememberCase: ${isEnabledRememberCase.value}"
    )
//    Log.d("daeYoung", "isEnabledDerivedStateCase: $isEnabledDerivedStateCase")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(state = lazyListState, modifier = Modifier.weight(1f)) {
            items(50) {
                Text(text = "Text $it")
            }
        }
        Button(onClick = { /*TODO*/ }, enabled = isEnabledDerivedStateCase) {
            Text(text = "Derived State Button")
        }

        Button(onClick = { /*TODO*/ }, enabled = isEnabledRememberCase.value) {
            Text(text = "Remembered Button")
        }
    }
}