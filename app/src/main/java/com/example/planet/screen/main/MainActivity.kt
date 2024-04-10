package com.example.planet.screen.main

import android.content.Intent
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
import androidx.navigation.compose.rememberNavController
import com.example.planet.component.navigation.NavigationGraph
import com.example.planet.screen.user.UserActivity
import com.example.planet.screen.map.MapActivity
import com.example.planet.ui.theme.MyApplicationTheme
import com.example.planet.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
//                RequestPermission()
                NavigationGraph(navController = navController, mainViewModel = mainViewModel, startMapActivity = { startMapActivity() }) {
                    startUserActivity()
                }
            }
        }
    }

    private fun startMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

    private fun startUserActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent)
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