package com.example.myapplication

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

val TAG = "daeYoung"

@HiltAndroidApp
class MainApplication : Application() {
}