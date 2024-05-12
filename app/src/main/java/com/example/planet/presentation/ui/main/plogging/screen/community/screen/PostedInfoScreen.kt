package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planet.presentation.ui.main.plogging.screen.community.component.PostedTopAppBar

@Composable
fun PostedInfoScreen(navController: NavHostController, appBarTitle: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        PostedTopAppBar(
            modifier = Modifier.padding(bottom = 18.dp),
            title = appBarTitle,
            onBack = { navController.popBackStack() }) {  }
    }
}