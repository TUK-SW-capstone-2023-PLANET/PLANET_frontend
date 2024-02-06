package com.example.myapplication.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Column {
                    Text(text = "asd", style = MaterialTheme.typography.headlineLarge)
                    Text(text = "asd", style = MaterialTheme.typography.titleLarge)
                    Text(text = "asd", style = MaterialTheme.typography.titleMedium)
                    Text(text = "asd", style = MaterialTheme.typography.bodyLarge)
                }
//                Button(onClick = { /*TODO*/ }) {
//                    Text(text = "asd", style = MaterialTheme.typography.headlineLarge)
//                }
            }
        }
    }
}
