package com.example.planet.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    private const val prefFileName = "userPref"

    @Singleton
    @Provides
    fun preferenceImpl(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
}