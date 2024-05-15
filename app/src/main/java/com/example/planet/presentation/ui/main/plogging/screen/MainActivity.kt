package com.example.planet.presentation.ui.main.plogging.screen

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.planet.TAG
import com.example.planet.presentation.ui.main.plogging.navigation.NavigationGraph
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.CommunityActivity
import com.example.planet.presentation.ui.main.plogging.screen.user.screen.UserActivity
import com.example.planet.presentation.ui.plogging.screen.MapActivity
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val mainViewModel by viewModels<MainViewModel>()
//    async(Dispatchers.IO) { getUniversityMyRanking() },

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate()")

        setContent {
            val navController = rememberNavController()
            mainViewModel
            MyApplicationTheme {
//                RequestPermission()
                NavigationGraph(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    startMapActivity = { startMapActivity() },
                    startUserActivity = { startUserActivity() },
                    startCommunityActivity = { board -> startCommunityActivity(board) }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity onStart()")

//        if (intent.getBooleanExtra("API_call", false) && mainViewModel.userId != 0L) {
//            lifecycleScope.launch {
//                mainViewModel.getUniversityMyRanking(mainViewModel.userId)
//                mainViewModel.getTop3Universities()
////                mainViewModel.getTop5SeasonUser(mainViewModel.userId)
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity onResume()")

    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity onPause()")

    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity onStop()")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity onCreate()")

    }



    private fun startMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

    private fun startUserActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent)
    }

    private fun startCommunityActivity(bulletinBoard: String) {
        val intent = Intent(this, CommunityActivity::class.java).apply {
            this.putExtra("board", bulletinBoard)
        }
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